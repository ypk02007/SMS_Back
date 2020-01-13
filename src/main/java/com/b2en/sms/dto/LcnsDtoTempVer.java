package com.b2en.sms.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.b2en.sms.customvalidator.StartEndValid;

import lombok.Data;

@Data
@StartEndValid.List(value = {
	@StartEndValid(start="lcnsStartDt", end="lcnsEndDt", message="라이센스 시작일과 라이센스 종료일의 선후관계가 맞지 않습니다.") }
)
public class LcnsDtoTempVer {
	
	@Min(value = 1, message="prdtId는 {value}보다 크거나 같아야 합니다.")
	private int prdtId;
	
	@NotBlank(message="prdtNm이 빈칸입니다.")
	private String prdtNm;
	
	@NotBlank(message="lcnsIssuDt가 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$", message="날짜는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String lcnsIssuDt;
	
	@NotBlank(message="lcnsTpCd가 빈칸입니다.")
	private String lcnsTpCd;
	
	@NotBlank(message="lcnsStartDt가 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$", message="날짜는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String lcnsStartDt;
	
	@NotBlank(message="lcnsEndDt가 빈칸입니다.")
	@Pattern(regexp="^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$", message="날짜는 yyyy-MM-dd의 형식으로, 올바른 값이 입력되어야 합니다")
	private String lcnsEndDt;
}
