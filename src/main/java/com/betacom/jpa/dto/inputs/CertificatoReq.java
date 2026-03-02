package com.betacom.jpa.dto.inputs;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CertificatoReq {
	private Integer id;
	private Boolean tipo; // false normale true agosnistico
	private String dataCertificato;
	private Integer socioID;
	
}
