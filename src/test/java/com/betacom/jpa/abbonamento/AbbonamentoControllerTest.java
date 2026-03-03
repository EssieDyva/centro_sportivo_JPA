package com.betacom.jpa.abbonamento;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.betacom.jpa.dto.inputs.AbbonamentoReq;
import com.betacom.jpa.dto.outputs.AbbonamentoDTO;
import com.betacom.jpa.response.Resp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AbbonamentoControllerTest {

    @Autowired
    private AbbonamentoController abbonamentoC;

    @SuppressWarnings("unchecked")
	@Test
	@Order(20)	
	public void myTest() {
        createAbbonamento();
        getAbbonamento();
        deleteAbbonamento();
    }

    public void createAbbonamento() {
        log.debug("Create abbonamento");
        AbbonamentoReq req = new AbbonamentoReq();
        req.setSocioID(1);
        req.setDataInscizione("01/02/2025");
        
        ResponseEntity<Resp> resp = abbonamentoC.create(req);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp)resp.getBody();

        Assertions.assertThat(r.getMsg()).isEqualTo("rest_created");
    }

    public void deleteAbbonamento() {
        log.debug("***** delete abbonamento *****");

        ResponseEntity<Resp> resp = abbonamentoC.delete(1);

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Resp r = (Resp)resp.getBody();
        log.debug(r.getMsg());
        Assertions.assertThat(r.getMsg()).isEqualTo("rest_deleted");
    }

    public void getAbbonamento() {
        log.debug("Test getAbbonamento");
        ResponseEntity<?> resp = abbonamentoC.findById(1);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        AbbonamentoDTO abb = (AbbonamentoDTO)resp.getBody();
        Assertions.assertThat(abb.getId()).isEqualTo(1);
    }
}
