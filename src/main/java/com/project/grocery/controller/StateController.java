package com.project.grocery.controller;




import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.project.grocery.responce.StateResponce;
import com.project.grocery.service.StateService;

import io.swagger.annotations.ApiOperation;


/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 24, 2018
 * 
 */
@RestController
@RequestMapping(value="/api/va/state")
public class StateController {
	
	private static final Logger LOG = LoggerFactory.getLogger(StateController.class);
	@Autowired
	StateService stateService;
	
	@RequestMapping(value="/uplodeState",method=RequestMethod.POST)
	@ApiOperation(value="List All Store",notes="Api to List All Store")
	public ResponseEntity<Object> processState(@RequestPart MultipartFile state)  {
		LOG.debug("Request Accepted to uplode states");
		stateService.uplodeState(state);
		return new ResponseEntity<Object>("State uploded",HttpStatus.OK);
		
	}
	@RequestMapping(value="/listStates",method=RequestMethod.GET)
	@ApiOperation(value="List All States",notes="Api to List all states")
	public ResponseEntity<Object> listAllState(){
	LOG.debug("request Accepted to List All State");
	List<StateResponce> stateResponce=stateService.listAllState();
	Map<Object, Object> responce=new HashMap<>();
	responce.put("state", stateResponce);
	return new ResponseEntity<Object>(responce,HttpStatus.OK);
	}

}
