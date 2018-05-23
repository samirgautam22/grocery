package com.project.grocery.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.project.grocery.exception.AlreadyExitException;
import com.project.grocery.model.Items;
import com.project.grocery.model.User;
import com.project.grocery.repository.ItemsRepository;
import com.project.grocery.request.ItemsCreatationRequest;
import com.project.grocery.util.File;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 19, 2018
 * 
 */
@Service
public class ItemsService {

	private static final Logger LOG = LoggerFactory.getLogger(ItemsService.class);

	@Autowired
	ItemsRepository itemsRepository;

	/**
	 * @param userID
	 * @param request
	 * @param file
	 */
	public Items create(Long userID, ItemsCreatationRequest request,CommonsMultipartFile file) {
		LOG.debug("Items uploded by admin");
		Items items = itemsRepository.findItemsByItemName(request.getItemsName());
		if (items != null) {
			throw new AlreadyExitException("Items Alredy Exit");

		}
		String imageUrl;
		Items it = new Items();
		imageUrl=File.writeImageToFile(file);
		it.setItemsPicture(imageUrl);
		it.setItemName(request.getItemsName());
		it.setUser(new User(userID));

		if (request.getItemsPicture() != null) {
		
		}
		itemsRepository.save(it);
		return it;

	}

}
