package com.betacom.jpa;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

import com.betacom.jpa.abbonamento.AbbonamentoControllerTest;
import com.betacom.jpa.attivita.AttivitaControllerTest;
import com.betacom.jpa.certificato.CertificatoControllerTest;
import com.betacom.jpa.socio.SocioControlerTest;
import com.betacom.jpa.socio.SocioFindByAttivitaTest;
import com.betacom.jpa.socio.SocioServicesTest;

@Suite
@SelectClasses({
	SocioServicesTest.class,
	SocioControlerTest.class,
	CertificatoControllerTest.class,
	AbbonamentoControllerTest.class,
	AttivitaControllerTest.class,
	SocioFindByAttivitaTest.class
})


@SpringBootTest
class ProjectJpaApplicationTests {

	@Test
	void contextLoads() {
	}

}
