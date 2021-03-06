package com.b2en.sms.model.pk;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Data;

@Data
@Embeddable
public class ContDetailHistPK implements Serializable {

	// 계약상세이력 PK
	private static final long serialVersionUID = 1019006356562898173L;
	
	// 계약상세순번
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "detail_seq")
	private int detailSeq;
	
	// 계약상세 PK
	private ContDetailPK contDetailPK;

}
