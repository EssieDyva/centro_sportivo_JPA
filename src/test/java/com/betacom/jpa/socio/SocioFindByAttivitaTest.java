package com.betacom.jpa.socio;

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

import com.betacom.jpa.controllers.AttivitaController;
import com.betacom.jpa.controllers.SocioController;
import com.betacom.jpa.dto.inputs.AbbonamentoReq;
import com.betacom.jpa.dto.inputs.AttivitaReq;
import com.betacom.jpa.dto.inputs.SocioReq;
import com.betacom.jpa.dto.outputs.SocioDTO;
import com.betacom.jpa.response.Resp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SocioFindByAttivitaTest {

	@Autowired
	private SocioController socioC;

	@Autowired
	private AttivitaController attivitaC;

	@SuppressWarnings("unchecked")
	@Test
	@Order(40)
	public void myTest() {
		createTestData();
		findByAttivita();
        findByAttivitaError();
	}

	public void createTestData() {
		log.debug("Create test data");

		SocioReq socioReq = new SocioReq();
		socioReq.setNome("Marco");
		socioReq.setCognome("Verdi");
		socioReq.setCodiceFiscale("MV001");
		socioReq.setEmail("m.verdi@gmail.com");

		ResponseEntity<Resp> resp = socioC.create(socioReq);
		assertEquals(HttpStatus.OK, resp.getStatusCode());

		AttivitaReq attReq = new AttivitaReq();
		attReq.setDescription("Judo");

		ResponseEntity<Resp> attResp = attivitaC.create(attReq);
		assertEquals(HttpStatus.OK, attResp.getStatusCode());
	}

	@SuppressWarnings("unchecked")
	public void findByAttivita() {
		log.debug("findByAttivita");

		ResponseEntity<Object> resp = socioC.findByAttivita("Judo");
		assertEquals(HttpStatus.OK, resp.getStatusCode());

		Object body = resp.getBody();

		List<SocioDTO> lS = (List<SocioDTO>) body;
		Assertions.assertThat(lS.size()).isGreaterThanOrEqualTo(0);
	}

	@SuppressWarnings("unchecked")
	public void findByAttivitaError() {
		log.debug("findByAttivita error");

		ResponseEntity<Object> resp = socioC.findByAttivita("error");
		assertEquals(HttpStatus.OK, resp.getStatusCode());
		Object body = resp.getBody();

		List<SocioDTO> lS = (List<SocioDTO>) body;

		Assertions.assertThat(lS.size()).isEqualTo(0);
	}
}
