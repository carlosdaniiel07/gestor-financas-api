package com.carlos.gestorfinancas.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.carlos.gestorfinancas.entities.Anexo;
import com.carlos.gestorfinancas.entities.CartaoCredito;
import com.carlos.gestorfinancas.entities.Conta;
import com.carlos.gestorfinancas.entities.Fatura;
import com.carlos.gestorfinancas.entities.Movimento;
import com.carlos.gestorfinancas.entities.enums.StatusFatura;
import com.carlos.gestorfinancas.entities.enums.StatusMovimento;
import com.carlos.gestorfinancas.repositories.MovimentoRepository;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;
import com.carlos.gestorfinancas.services.exceptions.OperacaoInvalidaException;
import com.carlos.gestorfinancas.utils.DateUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 13/07/2019
 */
@Service
public class MovimentoService {
	@Autowired
	private MovimentoRepository repository;
	
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private AnexoService anexoService;
	
	@Autowired
	private FaturaService faturaService;
	
	private final int dadosPorPagina = 30;
	private final String modulo = "MOVTO";
	private final List<String> modulosPermitidos = new ArrayList<String>(
		Arrays.asList(modulo, NubankService.ORIGEM, TicketService.ORIGEM)
	);
	
	@Deprecated
	public List<Movimento> getAll() {
		return repository.findAll();
	}
	
	public List<Movimento> getAll(int pagina) {
		return repository.getAll(PageRequest.of(pagina, dadosPorPagina));
	}
	
	public List<Movimento> getAllByStatus(StatusMovimento status) {
		return repository.findByStatus(status);
	}
	
	public List<Movimento> getAllByPeriodo(String minDate, String maxDate){
		Date min = DateUtils.buildCalendarWithLocalDate(LocalDate.parse(minDate)).getTime();
		Date max = DateUtils.buildCalendarWithLocalDate(LocalDate.parse(maxDate)).getTime();
		
		return repository.findByDataContabilizacaoBetween(min, max);
	}
	
	public List<Movimento> getByConta(Long contaId){
		return repository.findByContaId(contaId);
	}
	
	public List<Movimento> getByConta(Long contaId, int pagina){
		return repository.findByContaId(contaId, PageRequest.of(pagina, dadosPorPagina));
	}
	
	public Movimento getById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Esse movimento não foi encontrado."));
	}
	
	/**
	 * Gera um movimento bancário
	 * @param movimento => O movimento a ser gerado
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public Movimento insere(Movimento movimento) {
		Movimento movimentoGerado = null;
		
		movimento.setDataInclusao(DateUtils.getDataAtual());
		movimento.setOrigem(movimento.getOrigem() == null ? modulo : movimento.getOrigem());
		
		if(movimento.hasCartaoCredito()) {
			movimento.setStatus(StatusMovimento.EFETIVADO);
			
			Fatura fatura = movimento.getFatura();
			CartaoCredito cartao = fatura.getCartao();
			
			if(fatura.getStatus() == StatusFatura.NAO_FECHADA || fatura.getStatus() == StatusFatura.PAGO_PARCIAL) {
				if(movimento.getValorTotal() < cartao.getLimite()) {
					if(cartao.getLimiteRestante() >= movimento.getValorTotal()) {
						movimento.setConta(null);
						
						// Salva o movimento no banco de dados
						movimentoGerado = repository.save(movimento);
						
						// Ajusta o valor da fatura. Obs: por questões de performance, o valor da fatura não é ajustado automaticamente quando o movimento estiver sendo
						// incluído pela Integração Nubank. Uma chamada no fim da rotina de integração é feita p/ ajustar o saldo da fatura.
						if (!movimento.getOrigem().equalsIgnoreCase(NubankService.ORIGEM)) {
							faturaService.ajustaSaldo(fatura);
						}
					} else {
						throw new OperacaoInvalidaException("O cartão de crédito não tem saldo disponível.");
					}
				} else {
					throw new OperacaoInvalidaException(String.format("O valor do movimento está acima do limite do cartão de crédito (%f)", cartao.getLimite()));
				}
			} else {
				throw new OperacaoInvalidaException("A fatura já está fechada. É necessário abrir-lá novamente.");
			}
		} else {
			if(movimento.isEfetivado() && movimento.isFuturo(DateUtils.getDataAtual())) {
				movimento.setStatus(StatusMovimento.AGENDADO);
			}
			
			// Salva o movimento no banco de dados
			movimentoGerado = repository.save(movimento);
			
			// Ajusta o saldo da conta, se necessário..
			if(movimento.isEfetivado()) {
				contaService.ajustaSaldo(movimento.getConta());
			}
		}
		
		return movimentoGerado;
	}
	
	/**
	 * Atualiza uma coleção de movimentos
	 * @param movimentos
	 */
	public void atualiza(Collection<Movimento> movimentos, boolean isTask) {
		movimentos.forEach((Movimento m) -> atualiza(m, isTask));
	}
	
	/**
	 * Atualiza os dados de um dado movimento
	 * @param movimento
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void atualiza(Movimento movimento, boolean isTask) {
		Movimento oldMovimento = getById(movimento.getId());
		
		Fatura oldFatura = oldMovimento.getFatura();
		Conta oldConta = oldMovimento.getConta();
		StatusMovimento oldStatus = oldMovimento.getStatus();
		
		if(modulosPermitidos.contains(movimento.getOrigem()) || isTask) {
			if(movimento.hasCartaoCredito()) {
				movimento.setStatus(StatusMovimento.EFETIVADO);
				
				Fatura fatura = movimento.getFatura();
				CartaoCredito cartao = fatura.getCartao();
				
				if(fatura.getStatus() == StatusFatura.NAO_FECHADA || fatura.getStatus() == StatusFatura.PAGO_PARCIAL) {
					if(movimento.getValorTotal() < cartao.getLimite()) {
						if(cartao.getLimiteRestante() >= movimento.getValorTotal()) {
							movimento.setConta(null);
							
							// Salva o movimento no banco de dados
							repository.save(movimento);
						} else {
							throw new OperacaoInvalidaException("O cartão de crédito não tem saldo disponível.");
						}
					} else {
						throw new OperacaoInvalidaException(String.format("O valor do movimento está acima do limite do cartão de crédito (%f)", cartao.getLimite()));
					}
				} else {
					throw new OperacaoInvalidaException("A fatura já está fechada. É necessário abrir-lá novamente.");
				}
				
			} else {
				if(movimento.isEfetivado() && movimento.isFuturo(DateUtils.getDataAtual())) {
					movimento.setStatus(StatusMovimento.AGENDADO);
				}
				
				// Salva o movimento no banco de dados
				repository.save(movimento);
				
				// Ajusta saldo da conta, se necessário..
				if(movimento.isEfetivado()) {
					contaService.ajustaSaldo(movimento.getConta());
				}
			}
			
			// Ajusta saldo da conta bancária 'antiga'
			if(oldStatus == StatusMovimento.EFETIVADO) {
				if(oldConta != null && movimento.hasConta()) {
					if(oldConta != movimento.getConta() || movimento.hasCartaoCredito() || movimento.getStatus() != oldStatus) {
						contaService.ajustaSaldo(oldConta);
					}
				}
			}
			
			if(oldFatura != null || movimento.getFatura() != null) {
				faturaService.ajustaSaldo(oldFatura);
				faturaService.ajustaSaldo(movimento.getFatura());
			}
		} else {
			throw new OperacaoInvalidaException("Este movimento foi gerado por outro módulo, portanto, não pode ser alterado");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void efetiva(Long id) {
		Movimento obj = getById(id);
		
		if(!obj.isEfetivado()) {
			obj.setStatus(StatusMovimento.EFETIVADO);
			
			repository.save(obj);
			
			// Ajusta saldo da conta
			contaService.ajustaSaldo(obj.getConta());
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(Long id) {
		Movimento obj = getById(id);
		
		if(modulosPermitidos.contains(obj.getOrigem())) {
			// Remove os anexos (arquivos) do movimento
			anexoService.remove(obj.getAnexos());
			
			repository.delete(obj);
						
			// Ajusta saldo da conta, se necessário..
			if(obj.isEfetivado()) {
				contaService.ajustaSaldo(obj.getConta());
			}
			
			// Ajusta o saldo da fatura do cartão de crédito, se necessário..
			if(obj.hasCartaoCredito()) {
				faturaService.ajustaSaldo(obj.getFatura());
			}
		} else {
			throw new OperacaoInvalidaException("Este movimento foi gerado por outra rotina, portanto não pode ser alterado.");
		}
	}
	
	/**
	 * Insere um anexo
	 */
	public Anexo insereAnexo(Long movimentoId, MultipartFile file) {
		return anexoService.insere(getById(movimentoId), file);
	}
	
	/**
	 * Retorna a soma de todos os movimentos de crédito de uma conta específica
	 * @param conta
	 * @return
	 */
	public double getTotalCreditoByConta(Conta conta) {
		return repository.getTotalCreditoByConta(conta.getId()).orElse(0D);
	}

	/**
	 * Retorna a soma de todos os movimentos de débito de uma conta específica (não considera movimentos vinculados a cartão de crédito)
	 * @param conta
	 * @return
	 */
	public double getTotalDebitoByConta(Conta conta) {
		return repository.getTotalDebitoByConta(conta.getId()).orElse(0D);
	}
	
	/**
	 * Retorna a soma de todos os movimentos de débito de uma fatura específica
	 * @param fatura
	 * @return
	 */
	public double getTotalDebitoByFatura(Fatura fatura) {
		return repository.getTotalDebitoByFatura(fatura.getId()).orElse(0D);
	}
}