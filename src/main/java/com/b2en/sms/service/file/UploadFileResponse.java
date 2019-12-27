package com.b2en.sms.service.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UploadFileResponse {
	
	private String fileName;
	
    private String fileDownloadUri;
    
    private String fileType;
    
    private long size;
}