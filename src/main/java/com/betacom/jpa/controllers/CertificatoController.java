package com.betacom.jpa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.jpa.dto.inputs.CertificatoReq;
import com.betacom.jpa.response.Resp;
import com.betacom.jpa.services.interfaces.ICertificatoServices;
import com.betacom.jpa.services.interfaces.IMessagioServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("rest/certificato")
public class CertificatoController {

	private final ICertificatoServices certS;
	private final IMessagioServices    msgS;
	
	@PostMapping("/create")
	public ResponseEntity<Resp> create(@RequestBody(required = true)  CertificatoReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			certS.create(req);
			r.setMsg(msgS.get("rest_created"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}
	
	@PutMapping("/update")
	public ResponseEntity<Resp> update(@RequestBody(required = true)  CertificatoReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			certS.update(req);
			r.setMsg(msgS.get("rest_updated"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}
	

	@GetMapping("/list")
	public ResponseEntity<Object> list(){
		Object r;
		HttpStatus status = HttpStatus.OK;
		try {
			r= certS.listSocio();
		} catch (Exception e) {
			r=e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
		
	}

}
