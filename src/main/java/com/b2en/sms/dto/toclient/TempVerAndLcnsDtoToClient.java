package com.b2en.sms.dto.toclient;

import lombok.Data;

@Data
public class TempVerAndLcnsDtoToClient {
	
	private int tempVerId;
	
	private int custId;

	private int empId;
	
	private String macAddr;
	
	private String requestDate;
	
	private String issueReason;
	
	private LcnsDtoToClientTempVer[] lcns;
}
