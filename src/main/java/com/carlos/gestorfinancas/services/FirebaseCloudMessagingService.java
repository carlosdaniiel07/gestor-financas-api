package com.carlos.gestorfinancas.services;

import java.io.IOException;
import java.util.Collection;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.dtos.fcm.SendNotificationDTO;
import com.carlos.gestorfinancas.entities.Notificacao;
import com.carlos.gestorfinancas.entities.enums.TipoNotificacao;
import com.carlos.gestorfinancas.repositories.NotificacaoRepository;
import com.carlos.gestorfinancas.services.exceptions.NotificationException;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;
import com.carlos.gestorfinancas.utils.DateUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FirebaseCloudMessagingService implements NotificacaoService {

	@Autowired
	private NotificacaoRepository repository;
	
	private static final String API_ENDPOINT = "https://fcm.googleapis.com/fcm";
	
	@Override
	public Collection<Notificacao> getAll() {
		return repository.findAll();
	}

	@Override
	public Notificacao getById(Long notificacaoId) {
		return repository.findById(notificacaoId).orElseThrow(() -> new ObjetoNaoEncontradoException("Notificação não encontrada"));
	}

	@Override
	public void send(String titulo, String conteudo) {
		// Realiza requisição na API para envio da push notification
		ObjectMapper jsonMapper = new ObjectMapper();
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost postRequest = new HttpPost(API_ENDPOINT + "/send");
		String requestContent = null;
		String responseContent = null;
		
		postRequest.addHeader("Content-Type", "application/json");
		postRequest.addHeader("Authorization", String.format("key=%s", System.getenv("FCM_API_KEY")));
		
		try {
			requestContent = jsonMapper.writeValueAsString(new SendNotificationDTO(titulo, conteudo, "default", "/topics/all"));
			postRequest.setEntity(new StringEntity(requestContent));
			HttpResponse response = httpClient.execute(postRequest);
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				responseContent = EntityUtils.toString(response.getEntity());
				
				// Grava notificação no banco de dados
				this.insere(titulo, conteudo);
			} else {
				throw new NotificationException("Falha ao consumir API do Firebase FCM: " + EntityUtils.toString(response.getEntity()));
			}
		} catch(IOException ex) {
			throw new NotificationException(ex.getMessage());
		} finally {
			postRequest.releaseConnection();
		}
	}
	
	@Override
	public Notificacao markAsReceived(Long notificacaoId) {
		Notificacao notificacao = getById(notificacaoId);
		
		if (!notificacao.isRecebido()) {
			notificacao.setRecebido(true);
			notificacao.setDataRecebimento(DateUtils.getDataAtual());
			
			return repository.save(notificacao);
		}
		
		return notificacao;
	}
	
	/**
	 * Grava a notificação no banco de dados
	 * @param titulo
	 * @param conteudo
	 */
	private void insere(String titulo, String conteudo) {
		Notificacao notificacao = new Notificacao(null, TipoNotificacao.PUSH, titulo, conteudo, DateUtils.getDataAtual(), "Firebase Cloud Messaging", false, null);
		repository.save(notificacao);
	}
}
