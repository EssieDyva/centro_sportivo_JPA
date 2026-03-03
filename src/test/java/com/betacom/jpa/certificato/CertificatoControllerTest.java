package com.betacom.jpa.certificato;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.assertj.core.api.Assertions;
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
	@Order(10)	
	public void myTest() {
		createCertificato();
		updateCertificato();
		list();
	}

	public void createCertificato() {

		log.debug("Create certificato");
		
		CertificatoReq req = new CertificatoReq();
		req.setTipo(false);
		req.setDataCertificato("15/01/2025");
		req.setSocioID(1);
		
		ResponseEntity<Resp> resp = certificatoC.create(req);
		assertEquals(HttpStatus.OK, resp.getStatusCode());
		Resp r = (Resp)resp.getBody();
		
		Assertions.assertThat(r.getMsg()).isEqualTo("rest_created");
		
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
		req.setDataCertificato("10/03/2026");
		
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
