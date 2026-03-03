package com.betacom.jpa.attivita;

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

import com.betacom.jpa.controllers.AbbonamentoController;
import com.betacom.jpa.controllers.AttivitaController;
import com.betacom.jpa.dto.inputs.AbbonamentoReq;
import com.betacom.jpa.dto.inputs.AttivitaReq;
import com.betacom.jpa.dto.outputs.AttivitaDTO;
import com.betacom.jpa.response.Resp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AttivitaControllerTest {

    @Autowired
    private AttivitaController attivitaC;

    @Autowired
    private AbbonamentoController abbonamentoC;

    @SuppressWarnings("unchecked")
	@Test
	@Order(30)	
	public void myTest() {
		createAttivita();
        createAttivitaError();
        list();
        updateAttivita();
        createAttivitaAbbonamento();
        deleteAttivitaAbbonamento();
        deleteAttivita();
	}

    @SuppressWarnings("unchecked")
    public void list() {
        log.debug("Test list Attivita");
		
		ResponseEntity<Object> resp = attivitaC.list();
		assertEquals(HttpStatus.OK, resp.getStatusCode());
		Object body = resp.getBody();
		
		List<AttivitaDTO> lA = (List<AttivitaDTO>) body;
		
		Assertions.assertThat(lA.size()).isGreaterThanOrEqualTo(0);
		lA.forEach(c -> log.debug(c.toString()));
    }

    public void createAttivita() {
        log.debug("Create attivita");

        AttivitaReq req = new AttivitaReq();
        req.setDescription("Yoga");

        ResponseEntity<Resp> resp = attivitaC.create(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp)resp.getBody();

        Assertions.assertThat(r.getMsg()).isEqualTo("rest_created");
    }

    public void createAttivitaError() {
        log.debug("Create attivita error");

        AttivitaReq req = new AttivitaReq();
        req.setDescription("Yoga");

        ResponseEntity<Resp> resp = attivitaC.create(req);
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
        Resp r = (Resp)resp.getBody();

        Assertions.assertThat(r.getMsg()).isEqualTo("Attivita presente in db:Yoga");
    }

    public void updateAttivita() {
        log.debug("***** Update attivita *****");

        AttivitaReq req = new AttivitaReq();
        req.setId(1);
        req.setDescription("Karate");

        ResponseEntity<Resp> resp = attivitaC.update(req);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
		Resp r = (Resp)resp.getBody();
		log.debug(r.getMsg());
	
        Assertions.assertThat(r.getMsg()).isEqualTo("rest_updated");
    }

    public void deleteAttivita() {
        log.debug("******* delete attivita  *******");
		
		
		ResponseEntity<Resp> resp = attivitaC.delete(1);
	
		assertEquals(HttpStatus.OK, resp.getStatusCode());
		Resp r = (Resp)resp.getBody();
		log.debug(r.getMsg());
		Assertions.assertThat(r.getMsg()).isEqualTo("rest_deleted");
		
    }

    public void createAttivitaAbbonamento() {
        log.debug("Create attivitaAbbonamento");

        AbbonamentoReq abbReq = new AbbonamentoReq();
        abbReq.setSocioID(1);
        abbReq.setDataInscizione("02/03/2025");
        abbonamentoC.create(abbReq);

        AttivitaReq attReq = new AttivitaReq();
        attReq.setDescription("Corsa");
        ResponseEntity<Resp> resp = attivitaC.create(attReq);
        assertEquals(HttpStatus.OK, resp.getStatusCode());

        AttivitaReq linkReq = new AttivitaReq();
        linkReq.setId(2);
        linkReq.setAbbonamentID(2);
        resp = attivitaC.createAttivitaAbbonamento(linkReq);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp)resp.getBody();

        Assertions.assertThat(r.getMsg()).isEqualTo("rest_created");
    }

    public void deleteAttivitaAbbonamento() {
        log.debug("******* delete attivitaAbbonamento *******");
		
		ResponseEntity<Resp> resp = attivitaC.deleteAttivitaAbbonamento(2,2);
	
		assertEquals(HttpStatus.OK, resp.getStatusCode());
		Resp r = (Resp)resp.getBody();
		log.debug(r.getMsg());
		Assertions.assertThat(r.getMsg()).isEqualTo("rest_deleted");
    }
}
