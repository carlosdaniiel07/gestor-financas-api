package com.carlos.gestorfinancas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.carlos.gestorfinancas.entities.Conta;
import com.carlos.gestorfinancas.repositories.ContaRepository;
import com.carlos.gestorfinancas.services.exceptions.ObjetoNaoEncontradoException;
import com.carlos.gestorfinancas.services.exceptions.OperacaoInvalidaException;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 13/07/2019
 */
@Service
public class ContaService {
	@Autowired
	private ContaRepository repository;
	
	@Autowired
	private MovimentoService movimentoService;
	
	@Autowired
	private EmailService emailService;

	private final int dadosPorPagina = 30;
	
	public List<Conta> getAll() {
		return repository.findByAtivo(true);
	}
	
	public List<Conta> getAll(int pagina) {
		return repository.findByAtivo(true, PageRequest.of(pagina, dadosPorPagina));
	}
	
	public Conta getById(Long id) {
		return repository.findByIdAndAtivo(id, true).orElseThrow(() -> new ObjetoNaoEncontradoException("Esta conta não foi encontrada."));
	}
	
	public Conta insere(Conta conta) {
		conta.setAtivo(true);
		conta.setSaldo(conta.getSaldoInicial());
		
		return repository.save(conta);
	}
	
	public void atualiza(Conta conta) {
		List<Conta> contas = repository.findByNomeAndAtivo(conta.getNome(), true);
		contas.remove(conta);
		
		if(contas.isEmpty()) {
			conta.setAtivo(true);
			repository.save(conta);
		} else {
			throw new OperacaoInvalidaException(String.format("Já existe uma conta com o nome %s", conta.getNome()));
		}
	}
	
	public void remove(Long id) {
		Conta obj = getById(id);
		
		if(obj.isAtivo()) {
			obj.setAtivo(false);
			repository.save(obj);
		}
	}
	
	/**
	 * Ajusta o saldo da conta
	 * @param contaId
	 */
	public void ajustaSaldo(Conta conta) {
		double totalCredito = movimentoService.getTotalCreditoByConta(conta);
		double totalDebito = movimentoService.getTotalDebitoByConta(conta);
		double novoSaldo = conta.getSaldoInicial() + totalCredito - totalDebito; 
		
		conta.setSaldo(novoSaldo);
		repository.save(conta);
		
		// Envia um e-mail de alerta caso o saldo da conta esteja negativo
		if(novoSaldo < 0) {
			String assunto = String.format("O saldo da conta %s requer a sua atenção!", conta.getNome());
			String conteudo = String.format("Prezado,<br/><br/>O saldo atual da conta <b>%s</b> está atualmente negativo: R$ <b>%.2f</b>.", conta.getNome(), novoSaldo);
			
			emailService.enviaEmail(assunto, "carlosdaniiel0711@gmail.com", conteudo);
		}
	}
}
