package com.b2en.sms.dto;

import lombok.Data;

@Data
public class ContAndLcnsDtoForUpdate {
	
	// =============== update를 위해 필요한 id =================
	private int contId;
	
	private int[] contSeq;
	
	// =============== Cont =================
	// @Min(value = 1, message="orgId는 {value}보다 크거나 같아야 합니다.")
	private int orgId;

	// @Min(value = 1, message="empId는 {value}보다 크거나 같아야 합니다.")
	private int empId;

	// @NotBlank(message="contDt가 빈칸입니다.")
	// @Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
	// message="날짜는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String contDt;

	// @NotBlank(message="contReportNo가 빈칸입니다.")
	private String contReportNo;

	// @NotBlank(message="contTpCd가 빈칸입니다.")
	private String contTpCd;

	// @NotBlank(message="installDt가 빈칸입니다.")
	// @Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
	// message="날짜는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String installDt;

	// @NotBlank(message="checkDt가 빈칸입니다.")
	// @Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
	// message="날짜는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String checkDt;

	// @NotBlank(message="mtncStartDt가 빈칸입니다.")
	// @Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
	// message="날짜는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String mtncStartDt;

	// @NotBlank(message="mtncEndDt가 빈칸입니다.")
	// @Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
	// message="날짜는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String mtncEndDt;

	// =============== Lcns =================
	// @Valid
	private LcnsDto[] lcns;
	
}