package com.b2en.sms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.b2en.sms.dto.B2enDto;
import com.b2en.sms.dto.DeleteDto;
import com.b2en.sms.dto.ResponseInfo;
import com.b2en.sms.dto.autocompleteinfo.B2enAC;
import com.b2en.sms.dto.toclient.B2enDtoToClient;
import com.b2en.sms.entity.B2en;
import com.b2en.sms.repo.B2enRepository;
import com.b2en.sms.repo.CmmnDetailCdRepository;

@RestController
@RequestMapping("/api/b2en")
public class B2enController {
	
	@Autowired
	private B2enRepository repository;
	
	@Autowired
	private CmmnDetailCdRepository repositoryCDC;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping(value = "/showall", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<B2enDtoToClient>> showAll() {

		List<B2en> entityList = repository.findAllOrderByName();
		if(entityList.size()==0) {
			return new ResponseEntity<List<B2enDtoToClient>>(new ArrayList<B2enDtoToClient>(), HttpStatus.OK);
		}
		List<B2enDtoToClient> list;

		list = modelMapper.map(entityList, new TypeToken<List<B2enDtoToClient>>() {
		}.getType());

		return new ResponseEntity<List<B2enDtoToClient>>(list, HttpStatus.OK);

	}
	
	@GetMapping(value = "/aclist", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<B2enAC>> acList() {

		List<B2en> list = repository.findAllOrderByName();
		if(list == null) {
			return new ResponseEntity<List<B2enAC>>(new ArrayList<B2enAC>(), HttpStatus.OK);
		}
		List<B2enAC> acList = new ArrayList<B2enAC>();
		
		for(int i = 0; i < list.size(); i++) {
			B2enAC b2enAC = new B2enAC();
			b2enAC.setEmpId(list.get(i).getEmpId());
			b2enAC.setEmpNm(list.get(i).getEmpNm());
			acList.add(b2enAC);
		}
		
		return new ResponseEntity<List<B2enAC>>(acList, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<B2enDtoToClient> findById(@PathVariable("id") int id) {
		
		B2en b2en = repository.findById(id).orElse(null);
		if(b2en==null) {
			B2enDtoToClient nothing = null;
			return new ResponseEntity<B2enDtoToClient>(nothing, HttpStatus.OK);
		}
		
		B2enDtoToClient b2enDtoToClient = modelMapper.map(b2en, B2enDtoToClient.class);
		String empTpCdNm = repositoryCDC.findByCmmnDetailCdPKCmmnDetailCd(b2enDtoToClient.getEmpTpCd()).getCmmnDetailCdNm();
		b2enDtoToClient.setEmpTpCdNm(empTpCdNm);
		
		return new ResponseEntity<B2enDtoToClient>(b2enDtoToClient, HttpStatus.OK);
	}
	
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> create(@Valid @RequestBody B2enDto b2en, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if(result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 등록에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for(int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		B2en b2enEntity = modelMapper.map(b2en, B2en.class);
		
		repository.save(b2enEntity);
		
		res.add(new ResponseInfo("등록에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}

	@DeleteMapping(value = "")
	public ResponseEntity<List<ResponseInfo>> delete(@RequestBody DeleteDto id) {
		boolean deleteFlag = true;
		int[] idx = id.getIdx();
		for(int i = 0; i < idx.length; i++) {
			if(!repository.existsById(idx[i])) {
				deleteFlag = false;
				continue;
			}
			repository.deleteById(idx[i]);
		}
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		if(deleteFlag) {
			res.add(new ResponseInfo("삭제에 성공했습니다."));
		} else {
			res.add(new ResponseInfo("삭제 도중 문제가 발생했습니다."));
		}
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}

	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ResponseInfo>> update(@PathVariable("id") int id, @Valid @RequestBody B2enDto b2en, BindingResult result) {
		
		List<ResponseInfo> res = new ArrayList<ResponseInfo>();
		
		if(result.hasErrors()) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			List<FieldError> errors = result.getFieldErrors();
			for(int i = 0; i < errors.size(); i++) {
				res.add(new ResponseInfo(errors.get(i).getDefaultMessage()));
			}
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		B2en toUpdate = repository.findById(id).orElse(null);

		if (toUpdate == null) {
			res.add(new ResponseInfo("다음의 문제로 수정에 실패했습니다: "));
			res.add(new ResponseInfo("해당 id를 가진 row가 없습니다."));
			return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.BAD_REQUEST);
		}
		
		String empNm = b2en.getEmpNm();
		String empNo = b2en.getEmpNo();
		String email = b2en.getEmail();
		String telNo = b2en.getTelNo();

		toUpdate.setEmpNm(empNm);
		toUpdate.setEmpNo(empNo);
		toUpdate.setEmail(email);
		toUpdate.setTelNo(telNo);

		repository.save(toUpdate);
		
		res.add(new ResponseInfo("수정에 성공했습니다."));
		return new ResponseEntity<List<ResponseInfo>>(res, HttpStatus.OK);
	}
}
