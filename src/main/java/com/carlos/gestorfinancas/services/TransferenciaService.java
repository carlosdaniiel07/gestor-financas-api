package com.carlos.gestorfinancas.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.entities.Movimento;
import com.carlos.gestorfinancas.entities.Transferencia;
import com.carlos.gestorfinancas.entities.enums.StatusMovimento;
import com.carlos.gestorfinancas.entities.enums.StatusTransferencia;
import com.carlos.gestorfinancas.repositories.TransferenciaRepository;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;
import com.carlos.gestorfinancas.services.exceptions.OperacaoInvalidaException;
import com.carlos.gestorfinancas.utils.DateUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 13/07/2019
 */
@Service
public class TransferenciaService {
	@Autowired
	private TransferenciaRepository repository;

	@Autowired
	private MovimentoService movimentoService;
	
	private final int dadosPorPagina = 30;
	private final String modulo = "TRANSF";
	
	public List<Transferencia> getAll() {
		return repository.findAll();
	}
	
	public List<Transferencia> getAll(int pagina) {
		return repository.findAll(PageRequest.of(pagina, dadosPorPagina)).getContent();
	}
	
	public Transferencia getById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Essa transferência não foi encontrada."));
	}
	
	public Transferencia insere(Transferencia transferencia) {
		Transferencia transferenciaGerada = null;
		Date dataAtual = DateUtils.getDataAtual();
		
		if(!transferencia.getContaDestino().equals(transferencia.getContaOrigem())) {
			transferencia.setDataInclusao(dataAtual);
			
			if(transferencia.isEfetivado() && transferencia.isFuturo(dataAtual)) {
				transferencia.setStatus(StatusTransferencia.AGENDADO);
			}
			
			// Salva a transferência no banco de dados
			transferenciaGerada = repository.save(transferencia);
			
			// Gera os movimentos bancários, se necessário..
			if(transferencia.isEfetivado()) {
				Movimento movtoDebito = this.geraMovimentoDebito(transferencia, false);
				Movimento movtoCredito = this.geraMovimentoCredito(transferencia, false);
				
				movimentoService.insere(movtoDebito);
				movimentoService.insere(movtoCredito);
			}
		} else {
			throw new OperacaoInvalidaException("A conta de destino não pode ser igual a conta de origem.");
		}
		
		return transferenciaGerada;
	}
	
	public void atualiza(Transferencia transferencia) {
		if(transferencia.getStatus() != StatusTransferencia.AGENDADO) {
			throw new OperacaoInvalidaException("Não é possível atualizar uma transferência já efetivada.");
		} else {
			repository.save(transferencia);
		}
	}
	
	public void efetiva(Long id) {
		Transferencia obj = getById(id);
		
		if(obj.getStatus() == StatusTransferencia.AGENDADO) {
			obj.setStatus(StatusTransferencia.EFETIVADO);
			
			// Atualiza o status da transferência no banco de dados
			repository.save(obj);
			
			// Gera os movimentos bancários..
			Movimento movtoDebito = this.geraMovimentoDebito(obj, false);
			Movimento movtoCredito = this.geraMovimentoCredito(obj, false);
			
			movimentoService.insere(movtoDebito);
			movimentoService.insere(movtoCredito);
		} else {
			throw new OperacaoInvalidaException("Esta transferência já foi efetivada anteriormente.");
		}
	}
	
	public void estorna(Long id) {
		Transferencia transferencia = getById(id);
		
		if(transferencia.getStatus() == StatusTransferencia.EFETIVADO) {
			transferencia.setStatus(StatusTransferencia.ESTORNADO);
			
			// Atualiza status da transferência
			repository.save(transferencia);
			
			// Gera os movimentos bancários de estorno
			movimentoService.insere(this.geraMovimentoCredito(transferencia, true));
			movimentoService.insere(this.geraMovimentoDebito(transferencia, true));
		} else {
			throw new OperacaoInvalidaException("Não é possível estornar uma transferência que ainda não foi efetivada.");
		}
	}
	
	/**
	 * Gera um objeto do tipo Movimento, com base em uma transferência bancária
	 * @param transferencia
	 * @param estorno
	 * @return
	 */
	private Movimento geraMovimentoDebito(Transferencia transferencia, boolean estorno) {
		return new Movimento(
				null, 
				(estorno) ? "Estorno " + transferencia.getDescricao() : transferencia.getDescricao(), 
				'D', 
				DateUtils.getDataAtual(), 
				transferencia.getDataContabilizacao(), 
				(estorno) ? transferencia.getValor() : transferencia.getValor() + transferencia.getTaxa(), 
				0, 
				0, 
				StatusMovimento.EFETIVADO,
				modulo, 
				transferencia.getObservacao(), 
				(estorno) ? transferencia.getContaDestino() : transferencia.getContaOrigem(), 
				null, 
				null, 
				null, 
				null
		);
	}
	
	/**
	 * Gera um objeto do tipo Movimento, com base em uma transferência bancária
	 * @param transferencia
	 * @param estorno
	 * @return
	 */
	private Movimento geraMovimentoCredito(Transferencia transferencia, boolean estorno) {
		return new Movimento(
				null, 
				(estorno) ? "Estorno " + transferencia.getDescricao() : transferencia.getDescricao(), 
				'C', 
				DateUtils.getDataAtual(),
				transferencia.getDataContabilizacao(), 
				(estorno) ? transferencia.getValor() + transferencia.getTaxa() : transferencia.getValor(),
				0, 
				0, 
				StatusMovimento.EFETIVADO,
				modulo, 
				transferencia.getObservacao(), 
				(estorno) ? transferencia.getContaOrigem() : transferencia.getContaDestino(), 
				null, 
				null, 
				null, 
				null
		);
	}
}
