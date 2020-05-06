package com.carlos.gestorfinancas.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carlos.gestorfinancas.dtos.FaturaDTO;
import com.carlos.gestorfinancas.dtos.FaturaPagamentoDTO;
import com.carlos.gestorfinancas.entities.Fatura;
import com.carlos.gestorfinancas.entities.Movimento;
import com.carlos.gestorfinancas.services.FaturaService;
import com.carlos.gestorfinancas.services.exceptions.OperacaoInvalidaException;

/**
 * @author Carlos Daniel Martins de Almeida
 * @date 05/08/2019
 */
@RestController
@RequestMapping(value =  "/faturas")
public class FaturasResource {
	@Autowired
	private FaturaService service;
	
	@GetMapping(value = "/periodo")
	public ResponseEntity<List<Fatura>> listarPorPeriodo(
		@RequestParam(value = "minDate", required = true) String minDate,
		@RequestParam(value = "maxDate", required = true) String maxDate) {
		return ResponseEntity.ok(service.getAllByPeriodo(minDate, maxDate));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Fatura> buscar(@PathVariable Long id){
		return ResponseEntity.ok(service.getById(id));
	}
	
	@GetMapping(value = "/{id}/movimentos")
	public ResponseEntity<List<Movimento>> listarMovimentos(@PathVariable Long id){
		return ResponseEntity.ok(service.getById(id).getMovimentos());
	}
	
	@PostMapping
	public ResponseEntity<Fatura> insere(@RequestBody FaturaDTO objDTO) {
		Fatura obj = service.insere(objDTO.toFatura());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).body(obj);
	}
	
	@Deprecated
	@PutMapping(value = "/efetua-pagamento")
	public ResponseEntity<Void> paga(@RequestBody FaturaPagamentoDTO faturaDTO){
		service.efetuaPagamento(faturaDTO);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping(value = "/{id}/efetua-pagamento")
	public ResponseEntity<Void> pagaById(@PathVariable Long id, @RequestBody FaturaPagamentoDTO faturaDTO){
		if (faturaDTO.getFatura().getId().equals(id)) {
			service.efetuaPagamento(faturaDTO);
			return ResponseEntity.ok(null);
		} else {
			throw new OperacaoInvalidaException("As faturas informadas não conferem");
		}
	}
	
	@Deprecated
	@PutMapping(value = "/abre/{id}")
	public ResponseEntity<Void> abre(@PathVariable Long id){
		service.abre(id);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping(value = "/{id}/abre")
	public ResponseEntity<Void> abreById(@PathVariable Long id){
		service.abre(id);
		return ResponseEntity.ok(null);
	}
	
	@Deprecated
	@PutMapping(value = "/fecha/{id}")
	public ResponseEntity<Void> fecha(@PathVariable Long id){
		service.fecha(id);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping(value = "/{id}/fecha")
	public ResponseEntity<Void> fechaById(@PathVariable Long id){
		service.fecha(id);
		return ResponseEntity.ok(null);
	}
}
