package com.project.grocery.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.grocery.exception.AlreadyExitException;
import com.project.grocery.exception.NotFoundException;
import com.project.grocery.model.District;
import com.project.grocery.model.State;
import com.project.grocery.repository.DistrictRepository;
import com.project.grocery.repository.StateRepositoty;



/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 24, 2018
 * 
 */
@Service
public class DistrictService {

	private static final Logger LOG = LoggerFactory.getLogger(DistrictService.class);
	
	@Autowired
	DistrictRepository districtRepository;
	
	@Autowired
	StateRepositoty stateRepository;
	/**
	 * @param district
	 */
	@SuppressWarnings("resource")
	public void uplodeDistrict(MultipartFile district) {
		LOG.debug("Request Accepted to add store");
		District dist=districtRepository.findByDistrict("Ilam");
		if(dist!=null) {
			throw new AlreadyExitException("District has been already");
		}
		
		InputStream file;
		try {
			file = district.getInputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(file); 
			XSSFSheet sheet = workbook.getSheetAt(0); 
	        Row row;
	        for(int i=1; i<=sheet.getLastRowNum(); i++){ 
	            row = (Row) sheet.getRow(i);  
	            District dd = new District();
	           String sat=row.getCell(1).toString();   
	           dd.setDistrict(sat);
	           
	           
	           String stat=row.getCell(0).toString();  
	           State state=stateRepository.findByState(stat);
	           if(state==null){
	        	   throw new NotFoundException("State not found");
	           }
	           dd.setState(state);
	           districtRepository.save(dd);
	                
	        }
	        file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
