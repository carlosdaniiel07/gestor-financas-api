package com.carlos.gestorfinancas.services;

import java.util.Collection;

import com.carlos.gestorfinancas.entities.Notificacao;

public interface NotificacaoService {
	public Collection<Notificacao> getAll();
	public Notificacao getById(Long notificacaoId);
	public void send(String titulo, String conteudo);
	public Notificacao markAsReceived(Long notificacaoId);
}
