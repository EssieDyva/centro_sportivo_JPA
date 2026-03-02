package com.betacom.jpa;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

import com.betacom.jpa.certificato.CertificatoControllerTest;
import com.betacom.jpa.certificato.CertificatoServiceTest;
import com.betacom.jpa.socio.SocioControlerTest;
import com.betacom.jpa.socio.SocioServicesTest;

@Suite
@SelectClasses({
	SocioServicesTest.class,
	SocioControlerTest.class,
	CertificatoServiceTest.class,
	CertificatoControllerTest.class
})


@SpringBootTest
class ProjectJpaApplicationTests {

	@Test
	void contextLoads() {
	}

}
