package com.project.grocery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.project.grocery.responce.VdcResponce;
import com.project.grocery.service.VdcService;

import io.swagger.annotations.ApiOperation;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 24, 2018
 * 
 */
@RestController
@RequestMapping(value="/api/va/vdc")
public class VdcController {
	
	private static final Logger LOG = LoggerFactory.getLogger(VdcController.class);
	@Autowired
	VdcService vdcService;
	@ApiOperation(value="uplode vdc",notes="Api to uplode vdc")
	@RequestMapping(value = "/uplodeVdc", method = RequestMethod.POST)
	public ResponseEntity<Object> processState(@RequestPart MultipartFile vdc) {
		LOG.debug("Request Accepted to uplode Vdc");
		vdcService.uplodeVdc(vdc);
		return new ResponseEntity<Object>("Vdc uploded", HttpStatus.OK);
	}
	@ApiOperation(value="List all vdc by district",notes="Api to list vdc")
	@RequestMapping(value="/{district}",method=RequestMethod.GET)
	public ResponseEntity<Object> listAllDistrict(@PathVariable("district") String district){
		LOG.debug("Request Accepted to List a Vdc");
		 List<VdcResponce> vdcResponce=vdcService.getVdc(district);
		Map<Object,Object> responce=new HashMap<>();
		responce.put("vdc",vdcResponce);
		return new ResponseEntity<Object>(responce,HttpStatus.OK);
		
	}
}
