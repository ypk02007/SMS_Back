package com.b2en.sms.controller.file;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.b2en.sms.entity.file.Scan;
import com.b2en.sms.repo.file.ScanRepository;
import com.b2en.sms.service.MyFileNotFoundException;
import com.b2en.sms.service.ScanResponse;
import com.b2en.sms.service.ScanStorageService;

@RestController
@RequestMapping("/api/scan/")
public class ScanController {
	
	@Autowired
    private ScanStorageService scanStorageService;
	
	@Autowired
    private ScanRepository scanRepository;
    
    @PostMapping("/upload")
    public ScanResponse uploadFile(@RequestParam("file") MultipartFile file) {
    
    	String fileName = file.getOriginalFilename();
    	
        Scan scan = new Scan();
        scan.setFileName(fileName);
        scan.setFileType(file.getContentType());
        scan = scanRepository.save(scan);
        String scanId = scan.getId();
        
       scanStorageService.storeFile(file, scanId);
        
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/scan/download/")
                .path(scanId)
                .toUriString();

        return new ScanResponse(fileName, "sss", url, url);
    }
    
    @RequestMapping(value="/upload", method = RequestMethod.OPTIONS)
    public ScanResponse uploadFile2(@RequestParam("file") MultipartFile file) {
    
    	String fileName = file.getOriginalFilename();
    	
        Scan scan = new Scan();
        scan.setFileName(fileName);
        scan.setFileType(file.getContentType());
        scan = scanRepository.save(scan);
        String scanId = scan.getId();
        
       scanStorageService.storeFile(file, scanId);
        
        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/scan/download/")
                .path(scanId)
                .toUriString();

        return new ScanResponse(fileName, "sss", url, url);
    }
    
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId, HttpServletRequest request) {
    	Scan scan = scanRepository.findById(fileId).orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    	
        // Load file as Resource
        Resource resource = scanStorageService.loadFileAsResource(scan.getFileName());

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    
}