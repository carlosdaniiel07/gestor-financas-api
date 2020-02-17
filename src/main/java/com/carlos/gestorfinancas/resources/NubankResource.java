package com.carlos.gestorfinancas.resources;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carlos.gestorfinancas.dtos.nubank.NubankAutenticacaoQRCode;
import com.carlos.gestorfinancas.dtos.nubank.NubankLoginResponseDTO;
import com.carlos.gestorfinancas.dtos.nubank.NubankQRCodeDTO;
import com.carlos.gestorfinancas.dtos.nubank.Transaction;
import com.carlos.gestorfinancas.services.NubankService;

@RestController
@RequestMapping(value = "/nubank")
public class NubankResource {

	@Autowired
	private NubankService nu;
	
	@GetMapping(value = "/generate-qr-code")
	public ResponseEntity<NubankQRCodeDTO> generateQRCode() {
		return ResponseEntity.ok(nu.getNewQRCode());
	}
	
	@PostMapping(value = "/auth")
	public ResponseEntity<NubankLoginResponseDTO> auth(@RequestBody NubankAutenticacaoQRCode obj) {
		return ResponseEntity.ok(nu.auth(obj.getQr_code_id()));
	}
	
//	@GetMapping(value = "/credit-card-transactions")
//	public ResponseEntity<Collection<Transaction>> getCreditCardTransactions(@RequestBody NubankAutenticacaoQRCode obj) throws IOException {
//		return ResponseEntity.ok(nu.getCreditCardTransactions(obj.getQr_code_id()));
//	}
	
	@PostMapping(value = "/integrar-cartao-credito")
	public ResponseEntity<Collection<Transaction>> integrarCartaoCredito(@RequestBody NubankAutenticacaoQRCode obj) {
		return ResponseEntity.ok(nu.integrarCartaoCredito(obj.getQr_code_id()));
	}
}
