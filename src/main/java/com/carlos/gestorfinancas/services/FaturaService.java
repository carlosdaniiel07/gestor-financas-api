package com.carlos.gestorfinancas.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.carlos.gestorfinancas.dtos.FaturaPagamentoDTO;
import com.carlos.gestorfinancas.entities.Fatura;
import com.carlos.gestorfinancas.entities.Movimento;
import com.carlos.gestorfinancas.entities.enums.StatusFatura;
import com.carlos.gestorfinancas.entities.enums.StatusMovimento;
import com.carlos.gestorfinancas.repositories.FaturaRepository;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;
import com.carlos.gestorfinancas.services.exceptions.OperacaoInvalidaException;
import com.carlos.gestorfinancas.utils.DateUtils;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 13/07/2019
 */
@Service
public class FaturaService {
	@Autowired
	private FaturaRepository repository;

	@Autowired
	private MovimentoService movimentoService;
	
	private final String modulo = "CRCRE";
	
	public List<Fatura> getAll() {
		return repository.findAll();
	}
	
	public List<Fatura> getAllByStatus(StatusFatura status) {
		return repository.findByStatus(status);
	}
	
	public List<Fatura> getAllByPeriodo(String minDate, String maxDate) {
		Date min = DateUtils.buildCalendarWithLocalDate(LocalDate.parse(minDate)).getTime();
		Date max = DateUtils.buildCalendarWithLocalDate(LocalDate.parse(maxDate)).getTime();
		
		return repository.findByVencimentoBetween(min, max);
	}
	
	public List<Fatura> getByCartaoCredito(Long cartaoCreditoId) {
		return repository.findByCartaoId(cartaoCreditoId);
	}
	
	public Fatura getLastByCartaoCredito(Long cartaoCreditoId) {
		return repository.findLastByCartaoId(cartaoCreditoId).orElseThrow(() -> new ObjetoNaoEncontradoException("Nenhum fatura encontrada para este cartão de crédito"));
	}
	
	public Fatura getById(Long id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontradoException("Esta fatura não foi encontrada."));
	}
	
	public Fatura insere(Fatura fatura) {
		if(repository.findByCartaoIdAndReferencia(fatura.getCartao().getId(), fatura.getReferencia()).isEmpty()) {
			
			fatura.setReferencia(fatura.getReferencia().toLowerCase());
			fatura.setDataPagamento(null);
			fatura.setVencimento(calculaDataVencimento(fatura));
			fatura.setFechamento(calculaDataFechamento(fatura));
			fatura.setValor(0);
			fatura.setValorPago(0);
			fatura.setStatus(StatusFatura.NAO_FECHADA);
			
			return repository.save(fatura);
		} else {
			throw new OperacaoInvalidaException(String.format("Já existe uma fatura do mês informado (%s)", fatura.getReferencia()));
		}
	}
	
	/**
	 * Ajusta o valor de uma fatura
	 * @param fatura
	 */
	public void ajustaSaldo(Fatura fatura) {
		double totalDebito = movimentoService.getTotalDebitoByFatura(fatura);
		fatura.setValor(totalDebito);
		repository.save(fatura);
	}
	
	/*
	 * Efetua o pagamento de uma fatura de cartão de crédito
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void efetuaPagamento(FaturaPagamentoDTO faturaDTO) {
		Fatura fatura = faturaDTO.getFatura();
		
		if(fatura.getStatus() == StatusFatura.PENDENTE || fatura.getStatus() == StatusFatura.PAGO_PARCIAL) {
			if(faturaDTO.getValorPago() <= fatura.getSaldoRestante()) {
				StatusFatura novoStatus = (fatura.getSaldoRestante() - faturaDTO.getValorPago() > 0) ? StatusFatura.PAGO_PARCIAL : StatusFatura.PAGO;
				double novoValorPago = fatura.getValorPago() + faturaDTO.getValorPago();
				
				fatura.setStatus(novoStatus);
				fatura.setValorPago(novoValorPago);
				fatura.setDataPagamento(faturaDTO.getDataPagamento());
				
				// Altera dados da fatura no banco de dados
				repository.save(fatura);
				
				// Gera movimento bancário
				movimentoService.insere(this.geraMovimentoDebito(faturaDTO));
			} else {
				throw new OperacaoInvalidaException(String.format("O saldo a pagar desta fatura é %.2f", fatura.getSaldoRestante()));
			}
		} else {
			throw new OperacaoInvalidaException("Não é possível pagar uma fatura que já foi paga ou que ainda não foi fechada.");
		}
	}
	
	/**
	 * Abre uma fatura do cartão de crédito (status da fatura -> Não fechada)
	 */
	public void abre(Long faturaId) {
		Fatura fatura = getById(faturaId);
		
		if(fatura.getStatus() == StatusFatura.PENDENTE) {
			fatura.setStatus(StatusFatura.NAO_FECHADA);
			repository.save(fatura);
		} else {
			throw new OperacaoInvalidaException("Não é possível abrir uma fatura que já foi paga ou que ainda esteja em aberto.");
		}
	}

	/*
	 * Fecha uma fatura do cartão de crédito (status da fatura -> Pendente)
	 */
	public void fecha(Long faturaId) {
		Fatura fatura = getById(faturaId);
		
		if(fatura.getStatus() == StatusFatura.NAO_FECHADA) {
			fatura.setStatus(StatusFatura.PENDENTE);
			repository.save(fatura);
		} else {
			throw new OperacaoInvalidaException("Está fatura já está fechada.");
		}
	}
	
	/*
	 * Fecha uma fatura do cartão de crédito (status da fatura -> Pendente)
	 */
	public void fecha(Fatura fatura) {
		if(fatura.getStatus() == StatusFatura.NAO_FECHADA) {
			fatura.setStatus(StatusFatura.PENDENTE);
			repository.save(fatura);
		} else {
			throw new OperacaoInvalidaException("Está fatura já está fechada.");
		}
	}

	/**
	 * Calcula a data de vencimento de uma dada fatura
	 * @param fatura
	 * @return
	 */
	private Date calculaDataVencimento(Fatura fatura) {
		Date dataVencimento = null;
		int diaPagamento = fatura.getCartao().getDiaPagamento();
		int ultimoDiaDoMes;
		
		try {
			Date dataReferencia = new SimpleDateFormat("dd/MMM/yyyy").parse(String.format("01/%s", fatura.getReferencia()));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dataReferencia);
			ultimoDiaDoMes = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			
			if(diaPagamento > ultimoDiaDoMes) {
				calendar.set(Calendar.DAY_OF_MONTH, ultimoDiaDoMes);
			} else {
				calendar.set(Calendar.DAY_OF_MONTH, diaPagamento);
			}
			
			dataVencimento = calendar.getTime();
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		
		return dataVencimento;
	}
	
	
	/**
	 * Calcula a data de fechamento de uma fatura
	 * @param fatura
	 * @return
	 */
	private Date calculaDataFechamento(Fatura fatura) {
		Date dataFechamento = null;
		int diaFechamento = fatura.getCartao().getDiaFechamento();
		
		try {
			Date dataReferencia = new SimpleDateFormat("dd/MMM/yyyy").parse(String.format("01/%s", fatura.getReferencia()));
			Calendar calendar = Calendar.getInstance();
			
			calendar.setTime(dataReferencia);
			calendar.add(Calendar.MONTH, -1);
			
			if (diaFechamento > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
				calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			} else {
				calendar.set(Calendar.DAY_OF_MONTH, diaFechamento);
			}
			
			dataFechamento = calendar.getTime();
		} catch(ParseException ex) {
			ex.printStackTrace();
		}
		
		return dataFechamento;
	}
	
	private Movimento geraMovimentoDebito(FaturaPagamentoDTO faturaDTO) {
		String obs = "";
		List<String> descricoes = movimentoService.getDescricaoByFaturaId(faturaDTO.getFatura().getId());
		
		for(int i = 0; i < descricoes.size(); i++) {
			boolean isLastIteration = i + 1 == descricoes.size();
			obs += descricoes.get(i) + (isLastIteration ? "" : ", ");
		}
		
		return new Movimento(
				null, 
				String.format("Pagamento fatura cartão de crédito %s. Referência: %s", faturaDTO.getFatura().getCartao().getNome(), faturaDTO.getFatura().getReferencia()),
				'D', 
				DateUtils.getDataAtual(),
				faturaDTO.getDataPagamento(),
				faturaDTO.getValorPago(),
				0, 
				0, 
				StatusMovimento.EFETIVADO,
				modulo, 
				obs, 
				faturaDTO.getConta(), 
				null, 
				null, 
				null, 
				null
		); 
	}
}
