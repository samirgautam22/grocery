package com.project.grocery.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.grocery.exception.AlreadyExitException;
import com.project.grocery.model.State;
import com.project.grocery.repository.StateRepositoty;
import com.project.grocery.responce.StateResponce;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 24, 2018
 * 
 */
@Service
public class StateService {
	
	@Autowired
	StateRepositoty stateRepositoty;
	private static final Logger LOG = LoggerFactory.getLogger(StateService.class);
	
	/**
	 * @param state
	 */
	@SuppressWarnings("resource")
	public void uplodeState(MultipartFile state) {
		LOG.debug("Request Accepted to uplode state");
		State st=stateRepositoty.findByState("State 1");
		if(st!=null) {
			throw new AlreadyExitException("The file has been uploded");
			
		}
		InputStream file;
		try {
			file = state.getInputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(file); 
			XSSFSheet sheet = workbook.getSheetAt(0); 
	        Row row;
	        for(int i=1; i<=sheet.getLastRowNum(); i++){ 
	            row = (Row) sheet.getRow(i);  
	            State stat = new State();
	           String sat=row.getCell(0).toString();   
	           System.out.println(sat);
	           stat.setState(sat);
	           stateRepositoty.save(stat);
	                
	        }
	        file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public List<StateResponce> listAllState() {
		List<State> state=stateRepositoty.findAll();
		List<StateResponce> stateResponces=new ArrayList<>();
		state.stream().forEach(u->{
			StateResponce responce=new StateResponce();
			responce.setId(u.getId());
			responce.setState(u.getState());
			stateResponces.add(responce);
		});
		return stateResponces;
	}

	

}
