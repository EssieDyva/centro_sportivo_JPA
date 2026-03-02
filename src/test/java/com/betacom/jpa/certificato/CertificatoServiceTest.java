package com.betacom.jpa.certificato;

import java.util.List;

import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.betacom.jpa.dto.inputs.CertificatoReq;
import com.betacom.jpa.dto.outputs.SocioDTO;
import com.betacom.jpa.services.interfaces.ICertificatoServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CertificatoServiceTest {
	
	@Autowired
	private ICertificatoServices certificatoS;
	
	@Test
	@Order(1)
	public void createCertificatoTest() {
		log.debug("create certificato");
		try {
			CertificatoReq req = new CertificatoReq();
			req.setTipo(false);
			req.setDataCertificato("2026-01-15");
			req.setSocioID(1);
		
			certificatoS.create(req);
			
			List<SocioDTO> lC = certificatoS.listSocio();
			Assertions.assertThat(lC.size()).isGreaterThan(0);
			
			req = new CertificatoReq();
			req.setTipo(true);
			req.setDataCertificato("2026-02-20");
			req.setSocioID(2);
		
			certificatoS.create(req);
			
			lC = certificatoS.listSocio();
			Assertions.assertThat(lC.size()).isGreaterThan(1);
			
			lC.forEach(c -> log.debug(c.toString()));
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
		}
		
	}
	
	@Test
	@Order(2)
	public void createCertificatoErrorTest() {
		log.debug("create certificato in error");
		
		CertificatoReq req = new CertificatoReq();
		req.setTipo(false);
		req.setDataCertificato("2026-01-15");
		req.setSocioID(1);
	
		assertThrows(Exception.class, () -> {
			certificatoS.create(req);
		});
		
	}

	@Test
	@Order(3)
	public void updateCertificatoTest() {
		log.debug("update certificato");
		try {
			CertificatoReq req = new CertificatoReq();
			req.setId(1);
			req.setTipo(true);
			req.setDataCertificato("2026-03-10");
		
			certificatoS.update(req);
			
			List<SocioDTO> lC = certificatoS.listSocio();
			Assertions.assertThat(lC.size()).isGreaterThan(0);
			
			lC.forEach(c -> log.debug(c.toString()));
			
		} catch (Exception e) {
			
			log.error(e.getMessage());
		}
		
	}
}
