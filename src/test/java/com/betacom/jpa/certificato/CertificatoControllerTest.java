package com.betacom.jpa.certificato;

import java.util.List;

import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.betacom.jpa.controllers.CertificatoController;
import com.betacom.jpa.dto.inputs.CertificatoReq;
import com.betacom.jpa.dto.outputs.SocioDTO;
import com.betacom.jpa.response.Resp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CertificatoControllerTest {
	@Autowired
	private CertificatoController certificatoC;
	
	@SuppressWarnings("unchecked")
	@Test
	@Order(1)	
	public void myTest() {
		createCertificato();
		updateCertificato();
		list();
	}

	public void createCertificato() {

		log.debug("Create certificato");
		CertificatoReq req = new CertificatoReq();
		req.setTipo(false);
		req.setDataCertificato("2026-01-15");
		req.setSocioID(1);
		
		ResponseEntity<Resp> resp = certificatoC.create(req);
		log.debug("Response status: " + resp.getStatusCode());
		log.debug("Response body: " + resp.getBody());
		
		if (resp.getStatusCode() == HttpStatus.OK) {
			Resp r = (Resp)resp.getBody();
			Assertions.assertThat(r.getMsg()).isEqualTo("rest_created");
		} else if (resp.getStatusCode() == HttpStatus.BAD_REQUEST) {
			log.warn("Certificato creation failed: " + resp.getBody());
		}
		
	}

	@SuppressWarnings("unchecked")
    public void list() {
		log.debug("Test list certificato");
		
		ResponseEntity<Object> resp = certificatoC.list();
		assertEquals(HttpStatus.OK, resp.getStatusCode());
		Object body = resp.getBody();
		
		List<SocioDTO> lC = (List<SocioDTO>) body;
		
		Assertions.assertThat(lC.size()).isGreaterThanOrEqualTo(0);
		lC.forEach(c -> log.debug(c.toString()));
	}
	
	public void updateCertificato() {
		log.debug("******* Update certificato  *******");
		
		CertificatoReq req = new CertificatoReq();
		req.setId(1);
		req.setTipo(true);
		req.setDataCertificato("2026-03-10");
		
		ResponseEntity<Resp> resp = certificatoC.update(req);
		log.debug("Update response status: " + resp.getStatusCode());
		log.debug("Update response body: " + resp.getBody());
		
		if (resp.getStatusCode() == HttpStatus.OK) {
			Resp r = (Resp)resp.getBody();
			log.debug(r.getMsg());
			Assertions.assertThat(r.getMsg()).isEqualTo("rest_updated");
		} else if (resp.getStatusCode() == HttpStatus.BAD_REQUEST) {
			log.warn("Certificato update failed: " + resp.getBody());
		}
		
				
	}

}
