package com.betacom.jpa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.jpa.dto.inputs.AttivitaReq;
import com.betacom.jpa.response.Resp;
import com.betacom.jpa.services.interfaces.IAttivitaServices;
import com.betacom.jpa.services.interfaces.IMessagioServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("rest/atttivita")
public class AttivitaController {
	
	private final IMessagioServices    msgS;
	private final IAttivitaServices    attS;
	
	@PostMapping("/create")
	public ResponseEntity<Resp> create(@RequestBody(required = true)  AttivitaReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			attS.create(req);
			r.setMsg(msgS.get("rest_created"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}
	
	@PutMapping("/update")
	public ResponseEntity<Resp> update(@RequestBody(required = true)  AttivitaReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			attS.update(req);
			r.setMsg(msgS.get("rest_updated"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Resp> delete(@PathVariable(required = true)  Integer id){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			attS.delete(id);
			r.setMsg(msgS.get("rest_deleted"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}

	@PostMapping("/createAttivitaAbbonamento")
	public ResponseEntity<Resp> createAttivitaAbbonamento(@RequestBody(required = true)  AttivitaReq req){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			attS.createAttivitaAbbonamento(req);
			r.setMsg(msgS.get("rest_created"));
		} catch (Exception e) {
			r.setMsg(e.getMessage());
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);		
	}

	@DeleteMapping("/deleteAttivitaAbbonamento/{idAbbo}/{idAttivita}")
	public ResponseEntity<Resp> deleteAttivitaAbbonamento(
				@PathVariable(required = true)  Integer idAbbo,
				@PathVariable(required = true)  Integer idAttivita){
		Resp r = new Resp();
		HttpStatus status = HttpStatus.OK;
		try {
			attS.deleteAttivitaAbbonamento(idAbbo, idAttivita);
			r.setMsg(msgS.get("rest_deleted"));
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
			r= attS.list();
		} catch (Exception e) {
			r=e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
		
	}
}
