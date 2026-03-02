package com.betacom.jpa.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.jpa.dto.inputs.AttivitaReq;
import com.betacom.jpa.dto.outputs.AbbonamentoDTO;
import com.betacom.jpa.dto.outputs.AttivitaDTO;
import com.betacom.jpa.exceptions.AcademyException;
import com.betacom.jpa.models.Abbonamento;
import com.betacom.jpa.models.Attivita;
import com.betacom.jpa.repositories.IAbbonamentoRepository;
import com.betacom.jpa.repositories.IAttivitaRepository;
import com.betacom.jpa.services.interfaces.IAttivitaServices;
import static com.betacom.jpa.utilities.Mapper.buildAbbonamentoDTO;
import static com.betacom.jpa.utilities.Mapper.buildAttivitaDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class AttivitaImp implements IAttivitaServices{
	private final IAttivitaRepository attivR;
	private final IAbbonamentoRepository abbR;
	
	
	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void create(AttivitaReq req) throws Exception {
		log.debug("create []", req);
		if (attivR.existsByDescription(req.getDescription().trim().toUpperCase())){
			throw new AcademyException("Attivita presente in db:" + req.getDescription());	
		}
		Attivita at = new Attivita();
		at.setDescription(req.getDescription().trim().toUpperCase());
		
		attivR.save(at);
				
	}
	
	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void update(AttivitaReq req) throws Exception {
		log.debug("update {}", req);
		Attivita at = attivR.findById(req.getId())
				.orElseThrow(() -> new AcademyException("Attivita non trovata"));
		if (req.getDescription() != null) {
			if (attivR.existsByDescription(req.getDescription().trim().toUpperCase())){
				throw new AcademyException("Attivita presente in db:" + req.getDescription());	
			}
			at.setDescription(req.getDescription().trim().toUpperCase());
		}
		attivR.save(at);
	}
	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void delete(Integer id) throws Exception {
		log.debug("delete {}", id);
		Attivita at = attivR.findById(id)
				.orElseThrow(() -> new AcademyException("Attivita non trovata"));
		
		if (!at.getAbbonamentos().isEmpty())
			throw new AcademyException("Ci sono abbonamenti collegati per quest'attivia");
		
		attivR.delete(at);		
	}

	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void createAttivitaAbbonamento(AttivitaReq req) throws Exception {
		log.debug("createAttivitaAbbonamento {}", req);
		Attivita at = attivR.findById(req.getId())
				.orElseThrow(() -> new AcademyException("Attivita non trovata"));
		Abbonamento ab = abbR.findById(req.getAbbonamentID())
				.orElseThrow(() -> new AcademyException("Abbonamento non trovato"));
		
		
		if (!ab.getAttivitas().contains(at)) {
			ab.getAttivitas().add(at);  // update whit new attivita
			abbR.save(ab);			
		} else {
			throw new AcademyException("Attivita presente per l'abbonamento");			
		}
	}

	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void deleteAttivitaAbbonamento(Integer idAbbo, Integer idAttivita) throws Exception {
		log.debug("deleteAttivitaAbbonamento {} / {}", idAbbo, idAttivita);
		Abbonamento ab = abbR.findById(idAbbo)
				.orElseThrow(() -> new AcademyException("Abbonamento non trovata :" + idAbbo));
		
		ab.getAttivitas().stream()
			.filter(at -> at.getId() == idAttivita)
			.findFirst()
			.ifPresent(ab.getAttivitas()::remove);
		
		abbR.save(ab);
		
	}

	
	@Override
	public List<AttivitaDTO> list() {
		log.debug("list");
		List<Attivita> lA = attivR.findAll();
		return buildAttivitaDTO(lA);
	}

	@Override
	public List<AbbonamentoDTO> getByIdAttivita(Integer id) throws Exception {
		log.debug("getByIdAttivita {}", id);
		Attivita at = attivR.findById(id)
				.orElseThrow(() -> new AcademyException("Attivita non trovata"));
		
		
		return buildAbbonamentoDTO(at.getAbbonamentos());
	}




}
