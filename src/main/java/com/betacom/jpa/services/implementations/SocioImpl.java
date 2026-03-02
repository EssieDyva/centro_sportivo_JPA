package com.betacom.jpa.services.implementations;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.jpa.dto.inputs.SocioReq;
import com.betacom.jpa.dto.outputs.SocioDTO;
import com.betacom.jpa.exceptions.AcademyException;
import com.betacom.jpa.models.Socio;
import com.betacom.jpa.repositories.ISocioRepository;
import com.betacom.jpa.services.interfaces.IMessagioServices;
import com.betacom.jpa.services.interfaces.ISocioServices;
import static com.betacom.jpa.utilities.Mapper.buildSocioDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SocioImpl implements ISocioServices{

	private final ISocioRepository socioR;
	private final IMessagioServices msgS;
	
	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public Integer create(SocioReq req) throws AcademyException {
		log.debug("create {}", req);
		if (req.getCodiceFiscale()== null)
			throw new AcademyException("Codice fiscale non caricato");
		if (req.getCognome()== null)
			throw new AcademyException("Cognome non caricato");
		if (req.getEmail()== null)
			throw new AcademyException("Email non caricato");
		if (req.getNome()== null)
			throw new AcademyException("Nome non caricato");
		Optional<Socio> s = socioR.findByCodiceFiscale(req.getCodiceFiscale());
		if (s.isPresent())
			throw new AcademyException("codice fiscale presente in db.." + req.getCodiceFiscale());
		
		Socio soc = new Socio();
		soc.setNome(req.getNome());
		soc.setCognome(req.getCognome());
		soc.setEmail(req.getEmail());
		soc.setCodiceFiscale(req.getCodiceFiscale());
		
		return socioR.save(soc).getId();
		
	}

	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void update(SocioReq req) throws AcademyException {
		log.debug("update {}", req);
		Optional<Socio> soc = socioR.findById(req.getId());
		if (soc.isEmpty())
			throw new AcademyException("Socio non trovato in DB");
		
		Socio s = soc.get();

		if (req.getCodiceFiscale() != null)
			s.setCodiceFiscale(req.getCodiceFiscale());
		if (req.getCognome() != null)
			s.setCognome(req.getCognome());
		if (req.getEmail() != null)
			s.setEmail(req.getEmail());
		if (req.getNome() != null)
			s.setNome(req.getNome());
		
		socioR.save(s);
		
		
	}
	
	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void delete(Integer id) throws AcademyException {
		log.debug("delete {}", id);
		Socio soc = socioR.findById(id)
				.orElseThrow(() ->new AcademyException("Socio non trovato in DB"));
		
		if (!soc.getAbbonementos().isEmpty())
			throw new AcademyException(msgS.get("abbo_exist"));

		socioR.delete(soc);
	}

	@Override
	public List<SocioDTO> find(Integer id, String nome, String cognome, Integer attivita) throws AcademyException {
		log.debug("find {} / {} / {} / {}", id, nome, cognome, attivita);
		List<Socio> lS = socioR.searchByFilter(id, nome, cognome, attivita);

		return buildSocioDTO(lS);
	}
	
	@Override
	public SocioDTO findById(Integer id) throws Exception {
		log.debug("findById: {}", id);
		Socio s = socioR.findById(id)
				.orElseThrow(() -> new AcademyException("Socio non trovato in DB:" + id));
		
		return buildSocioDTO(s);
	}

	@Override
	public List<SocioDTO> findByAttivita(String attivita) throws Exception {
		log.debug("findByAttivita: {}", attivita);
		List<Socio>lS = socioR.searchByAttivita(attivita.toUpperCase());
		
		return buildSocioDTO(lS);
	}
}
