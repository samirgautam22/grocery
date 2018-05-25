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
import com.project.grocery.exception.NotFoundException;
import com.project.grocery.model.District;
import com.project.grocery.model.Vdc;
import com.project.grocery.repository.DistrictRepository;
import com.project.grocery.repository.VdcRepository;
import com.project.grocery.responce.VdcResponce;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 24, 2018
 * 
 */
@Service
public class VdcService {
	
	private static final Logger LOG = LoggerFactory.getLogger(VdcService.class);
	
	@Autowired
	VdcRepository vdcRepository;
	
	@Autowired
	DistrictRepository districtRepository;

	/**
	 * @param vdc
	 */
	@SuppressWarnings("resource")
	public void uplodeVdc(MultipartFile vdc) {
		
		LOG.debug("Request Accepted to add store");
		Vdc vd=vdcRepository.findByVdc("Ilam Municipality");
		if(vd!=null) {
			throw new AlreadyExitException("Vdc has been already");
		}
		
		InputStream file;
		try {
			file = vdc.getInputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(file); 
			XSSFSheet sheet = workbook.getSheetAt(0); 
	        Row row;
	        for(int i=1; i<=sheet.getLastRowNum(); i++){ 
	            row = (Row) sheet.getRow(i);  
	            Vdc dd = new Vdc();
	           String sat=row.getCell(2).toString();   
	           dd.setVdc(sat);
	           
	           
	           String stat=row.getCell(1).toString();  
	           District district=districtRepository.findByDistrict(stat);
	           if(district==null){
	        	   throw new NotFoundException("State not found");
	           }
	           dd.setDistrict(district);
	           vdcRepository.save(dd);
	                
	        }
	        file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param district
	 * @return
	 */
	public List<VdcResponce> getVdc(String district) {
		
		LOG.debug("Request Accepted to List a district");
		
		District dist=districtRepository.findByDistrict(district);
		if(dist==null) {
			throw new AlreadyExitException("District not found");
		}
		
		List<Vdc> vc=vdcRepository.findAllVdcByDistrict(new District(dist.getId()));
		List<VdcResponce> vdcResponces=new ArrayList<>();
		vc.stream().forEach(u->{
			VdcResponce responce=new VdcResponce();
			responce.setVdc(u.getVdc());
			vdcResponces.add(responce);
		});
		return vdcResponces;
	}

}
