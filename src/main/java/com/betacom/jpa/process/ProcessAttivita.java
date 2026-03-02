package com.betacom.jpa.process;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.jpa.dto.inputs.AttivitaReq;
import com.betacom.jpa.dto.outputs.AbbonamentoDTO;
import com.betacom.jpa.dto.outputs.AttivitaDTO;
import com.betacom.jpa.services.interfaces.IAttivitaServices;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessAttivita {

	private final IAttivitaServices attS;
	
	@Transactional (rollbackFor = Exception.class)
	public void  createAttivita() throws Exception{
		List<AttivitaReq> lreq = new ArrayList<AttivitaReq>();
		AttivitaReq req = new AttivitaReq();
		req.setDescription("Yoga");
		lreq.add(req);
		req = new AttivitaReq();
		req.setDescription("Karate");
		lreq.add(req);
		req = new AttivitaReq();
		req.setDescription("Ritmica");
		lreq.add(req);
		for (AttivitaReq r : lreq) {
			attS.create(r);
		}
	}
	@Transactional (rollbackFor = Exception.class)	
	public void createAttivitaAbbonamento(AttivitaReq req) throws Exception{
		attS.createAttivitaAbbonamento(req);
	}

	@Transactional (rollbackFor = Exception.class)	
	public void deleteAttivitaAbbonamento(Integer idAbb, Integer idAtti) throws Exception{
		attS.deleteAttivitaAbbonamento(idAbb, idAtti);
	}

	
	@Transactional (rollbackFor = Exception.class)
	public void deleteAttivita(Integer id) throws Exception{
		attS.delete(id);
	}

	public void getByAttivita(Integer id) throws Exception{
		List<AbbonamentoDTO> lA = attS.getByIdAttivita(id);
		lA.forEach(a -> log.debug(a.toString()));
	}
	
	public void list() {
		List<AttivitaDTO> lA = attS.list();
		lA.forEach(a -> log.debug(a.toString()));
	}
	
}
