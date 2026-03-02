package com.betacom.jpa.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.jpa.dto.inputs.CertificatoReq;
import com.betacom.jpa.dto.outputs.SocioDTO;
import com.betacom.jpa.exceptions.AcademyException;
import com.betacom.jpa.models.Certificato;
import com.betacom.jpa.models.Socio;
import com.betacom.jpa.repositories.ICertificatoRepository;
import com.betacom.jpa.repositories.ISocioRepository;
import com.betacom.jpa.services.interfaces.ICertificatoServices;
import com.betacom.jpa.services.interfaces.IMessagioServices;
import static com.betacom.jpa.utilities.Mapper.buildSocioDTO;
import static com.betacom.jpa.utilities.Utils.stringToDate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CertificatoImpl implements ICertificatoServices{
	private final ICertificatoRepository repoC;
	private final ISocioRepository repoS;
	private final IMessagioServices msgS;
	
	@Transactional (rollbackFor = AcademyException.class)
	@Override
	public void create(CertificatoReq req) throws Exception {
		log.debug("create {}", req);
		Socio soc = repoS.findById(req.getSocioID())
				.orElseThrow(() -> new AcademyException("Socio non trovato :" + req.getSocioID()));
		Certificato cert = new Certificato();
		cert.setTipo((req.getTipo() == null) ? false : true);
		cert.setDataCertificato(stringToDate(req.getDataCertificato()));
		cert.setSocio(soc);
		
		repoC.save(cert);
		
	}
	@Transactional (rollbackFor = AcademyException.class)	
	@Override
	public void update(CertificatoReq req) throws Exception {
		log.debug("update {}", req);
		Certificato cert = repoC.findById(req.getId())
				.orElseThrow(() -> new AcademyException(msgS.get("cert_exist")));
		if (req.getTipo() != null)
			cert.setTipo(req.getTipo());
		if (req.getDataCertificato() != null)
			cert.setDataCertificato(stringToDate(req.getDataCertificato()));
		repoC.save(cert);
	}


	@Override
	public List<SocioDTO> listSocio() throws Exception {
		log.debug("listSocio");
		List<Certificato> lC = repoC.findAll();
		
		return lC.stream()
				.map(c -> buildSocioDTO(c.getSocio()))
				.toList();
						
	}
}
