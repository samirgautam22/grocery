package com.project.grocery.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.project.grocery.service.DistrictService;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 24, 2018
 * 
 */
@RestController
@RequestMapping(value = "/api/va/district")
public class DistrictController {

	private static final Logger LOG = LoggerFactory.getLogger(DistrictController.class);
	@Autowired
	DistrictService districtService;

	@RequestMapping(value = "/uplodeDistrict", method = RequestMethod.POST)
	public ResponseEntity<Object> processState(@RequestPart MultipartFile district) {
		LOG.debug("Request Accepted to uplode District");
		districtService.uplodeDistrict(district);
		return new ResponseEntity<Object>("District uploded", HttpStatus.OK);

	}
}
