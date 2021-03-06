package com.b2en.sms.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.BatchSize;

import com.b2en.sms.model.pk.ContChngHistPK;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@BatchSize(size=10)
public class ContChngHist extends TimeEntity implements Serializable {

	// 계약이력
	private static final long serialVersionUID = 4870948412599295269L;

	// 계약이력 복합키
	@EmbeddedId
	private ContChngHistPK contChngHistPK;
	
	@MapsId("contId")
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name="cont_id")
	//@OnDelete(action=OnDeleteAction.CASCADE)
	private Cont cont;
	
	// 고객사담당자ID (FK)
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name="cust_id")
	private Cust cust;
	
	// 고객사ID (FK)
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name="org_id")
	private Org org;
	
	// 담당자ID (FK)
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name="emp_id")
	private B2en b2en;
	
	// 계약일자
	@Column(name="cont_dt")
	private Date contDt;
	
	// 총계약금액
	@Column(name="cont_tot_amt")
	private String contTotAmt;
	
	// 수주보고서번호
	@Column(name="cont_report_no")
	private String contReportNo;
	
	// 설치일자
	@Column(name="install_dt")
	private Date installDt;
	
	// 검수일자
	@Column(name="check_dt")
	private Date checkDt;
	
	// 유지보수개시일
	@Column(name="mtnc_start_dt")
	private Date mtncStartDt;
	
	// 유지보수종료일
	@Column(name="mtnc_end_dt")
	private Date mtncEndDt;
}
