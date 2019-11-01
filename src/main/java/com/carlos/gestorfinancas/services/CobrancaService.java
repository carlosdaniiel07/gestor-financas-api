package com.carlos.gestorfinancas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.carlos.gestorfinancas.dtos.CobrancaPagamentoDTO;
import com.carlos.gestorfinancas.dtos.CobrancaRemocaoDTO;
import com.carlos.gestorfinancas.entities.Cobranca;
import com.carlos.gestorfinancas.entities.Movimento;
import com.carlos.gestorfinancas.entities.OperacaoCobranca;
import com.carlos.gestorfinancas.entities.enums.StatusCobranca;
import com.carlos.gestorfinancas.entities.enums.StatusMovimento;
import com.carlos.gestorfinancas.entities.enums.TipoOperacaoCobranca;
import com.carlos.gestorfinancas.repositories.CobrancaRepository;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;
import com.carlos.gestorfinancas.services.exceptions.OperacaoInvalidaException;
import com.carlos.gestorfinancas.utils.DateUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 14/07/2019
 */
@Repository
public class CobrancaService {
	@Autowired
	private CobrancaRepository repository;
	
	@Autowired
	private MovimentoService movimentoService;
	
	@Autowired
	private OperacaoCobrancaService operacaoCobrancaService;
	
	private final int dadosPorPagina = 30;
	private final String modulo = "COBRC";
	
	public List<Cobranca> getAll() {
		return repository.findAll();
	}
	
	public List<Cobranca> getAll(int pagina) {
		return repository.findAll(PageRequest.of(pagina, dadosPorPagina)).getContent();
	}
	
	public List<Cobranca> getAllByStatus(StatusCobranca status) {
		return repository.findByStatus(status);
	}
	
	public Cobranca getById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Essa cobrança não foi encontrada."));
	}
	
	public Cobranca insere(Cobranca cobranca) {
		if(cobranca.getValorTotal() <= cobranca.getBeneficiario().getLimite()) {
			cobranca.setParcela(cobranca.getParcela() == 0 ? 1 : cobranca.getParcela());
			cobranca.setDataPagamento(null);
			cobranca.setStatus(StatusCobranca.PENDENTE);
			cobranca.setSaldo(cobranca.getValorTotal());
			cobranca.setDataAgendamento(cobranca.getDataVencimento());
		
			return repository.save(cobranca);
		} else {
			throw new OperacaoInvalidaException(String.format("O valor da cobrança é superior ao limite deste beneficiário (%f)", cobranca.getValorTotal()));
		}		
	}
	
	public void atualiza(Cobranca cobranca) {
		if(cobranca.getStatus() == StatusCobranca.AGENDADO || cobranca.getStatus() == StatusCobranca.PENDENTE) {
			repository.save(cobranca);
		} else {
			throw new OperacaoInvalidaException("Não é possível alterar uma cobrança já paga.");
		}
	}
	
	/**
	 * Atualiza o status de uma cobrança para pago
	 * @param cobranca
	 */
	public void atualizaParaPago(Cobranca cobranca) {
		StatusCobranca novoStatus = (cobranca.getSaldo() == 0) ? StatusCobranca.PAGO : StatusCobranca.PAGO_PARCIAL;
		cobranca.setStatus(novoStatus);
		
		repository.save(cobranca);
	}
	
	/**
	 * Efetua o pagamento de uma cobrança
	 * @param pagamentoDTO
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void efetuaPagamento(CobrancaPagamentoDTO pagamentoDTO) {
		Cobranca cobranca = pagamentoDTO.getCobranca();
		
		if(cobranca.getStatus() != StatusCobranca.PAGO) {
			if(pagamentoDTO.getValorPago() <= cobranca.getSaldo()) {
				if(pagamentoDTO.getValorPago() <= cobranca.getBeneficiario().getLimite()) {
					StatusCobranca novoStatus = (pagamentoDTO.getValorPago() < cobranca.getSaldo()) ? StatusCobranca.PAGO_PARCIAL : StatusCobranca.PAGO;
					
					cobranca.setSaldo(cobranca.getSaldo() - pagamentoDTO.getValorPago());
					cobranca.setDataPagamento(pagamentoDTO.getDataPagamento());
					cobranca.setDataAgendamento(cobranca.getDataPagamento());
					cobranca.setStatus(novoStatus);
					
					// Atualiza dados da cobrança no banco de dados
					repository.save(cobranca);
					
					// Gera movimento bancário
					movimentoService.insere(this.geraMovimentoDebito(pagamentoDTO, cobranca));
					
					// Gera operação de baixa na cobrança
					operacaoCobrancaService.insere(this.geraOperacaoCobranca(pagamentoDTO, cobranca));				
				} else {
					throw new OperacaoInvalidaException(String.format("O valor da cobrança é superior ao limite deste beneficiário (%.2f)", cobranca.getValorTotal()));
				}
			} else {
				throw new OperacaoInvalidaException(String.format("O valor informado é superior ao valor da cobrança (%.2f)", cobranca.getSaldo()));
			}
		} else {
			throw new OperacaoInvalidaException("Não há saldo a pagar nesta cobrança.");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(CobrancaRemocaoDTO remocaoDTO) {
		Cobranca cobranca = remocaoDTO.getCobranca();

		// Insere os movimentos bancários e remove as operações de baixa, se necessário..
		if(cobranca.getStatus() == StatusCobranca.PAGO || cobranca.getStatus() == StatusCobranca.PAGO_PARCIAL) {
			Movimento movimentoCredito = this.geraMovimentoCredito(cobranca, remocaoDTO);
			List<OperacaoCobranca> operacoesCobranca = cobranca.getOperacoes();
			
			movimentoService.insere(movimentoCredito);
			operacaoCobrancaService.remove(operacoesCobranca);
		}
		
		// Remove a cobrança do banco de dados
		repository.delete(cobranca);
	}
	
	private Movimento geraMovimentoDebito(CobrancaPagamentoDTO pagamentoDTO, Cobranca cobranca) {
		return new Movimento(
				null, 
				cobranca.getDescricao(),
				'D', 
				DateUtils.getDataAtual(),
				pagamentoDTO.getDataPagamento(), 
				pagamentoDTO.getValorPago(),
				0, 
				0, 
				StatusMovimento.EFETIVADO,
				modulo, 
				cobranca.getObservacao(), 
				pagamentoDTO.getConta(), 
				pagamentoDTO.getCategoria(), 
				pagamentoDTO.getSubcategoria(), 
				pagamentoDTO.getProjeto(), 
				pagamentoDTO.getFatura()
		); 
	}
	
	/**
	 * Gera um movimento de crédito (usado somente na remoção da cobrança)
	 * @param cobranca
	 * @return
	 */
	private Movimento geraMovimentoCredito(Cobranca cobranca, CobrancaRemocaoDTO remocaoDTO) {
		return new Movimento(
				null, 
				"Estorno " + cobranca.getDescricao(),
				'C', 
				DateUtils.getDataAtual(),
				DateUtils.getDataAtual(), 
				cobranca.getValorTotal() - cobranca.getSaldo(),
				0, 
				0, 
				StatusMovimento.EFETIVADO,
				modulo, 
				cobranca.getObservacao(), 
				remocaoDTO.getConta(),
				remocaoDTO.getCategoria(), 
				remocaoDTO.getSubcategoria(), 
				remocaoDTO.getProjeto(), 
				null
		);
	}
	
	private OperacaoCobranca geraOperacaoCobranca(CobrancaPagamentoDTO pagamentoDTO, Cobranca cobranca) {
		return new OperacaoCobranca(
				null, 
				TipoOperacaoCobranca.BAIXA, 
				String.format("BAIXA REF. VALOR PAGO S/ COBRANÇA #%d", cobranca.getId()), 
				pagamentoDTO.getDataPagamento(), 
				pagamentoDTO.getValorPago(), 
				cobranca
		);
	}
}
