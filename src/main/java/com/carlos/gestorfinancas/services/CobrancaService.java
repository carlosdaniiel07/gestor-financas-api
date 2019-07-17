package com.carlos.gestorfinancas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.carlos.gestorfinancas.entities.Cobranca;
import com.carlos.gestorfinancas.entities.Movimento;
import com.carlos.gestorfinancas.entities.OperacaoCobranca;
import com.carlos.gestorfinancas.entities.dtos.CobrancaPagamentoDTO;
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
		repository.save(cobranca);
	}
	
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
					throw new OperacaoInvalidaException(String.format("O valor da cobrança é superior ao limite deste beneficiário (%f)", cobranca.getValorTotal()));
				}
			} else {
				throw new OperacaoInvalidaException(String.format("O valor informado é superior ao valor da cobrança (%f)", cobranca.getSaldo()));
			}
		} else {
			throw new OperacaoInvalidaException("Não há saldo a pagar nesta cobrança.");
		}
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
	
	private OperacaoCobranca geraOperacaoCobranca(CobrancaPagamentoDTO pagamentoDTO, Cobranca cobranca) {
		return new OperacaoCobranca(
				null, 
				TipoOperacaoCobranca.BAIXA, 
				String.format("BAIXA REF. VALOR PAGO S/ COBRANÇA ID %d", cobranca.getId()), 
				pagamentoDTO.getDataPagamento(), 
				pagamentoDTO.getValorPago(), 
				cobranca
		);
	}
}
