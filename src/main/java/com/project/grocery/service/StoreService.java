package com.project.grocery.service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.grocery.dto.StoreAddressDto;
import com.project.grocery.dto.StoreDto;
import com.project.grocery.exception.AlreadyExitException;
import com.project.grocery.exception.NotFoundException;
import com.project.grocery.exception.RequiredException;
import com.project.grocery.model.Login;
import com.project.grocery.model.Store;
import com.project.grocery.model.StoreAddress;
import com.project.grocery.model.User;
import com.project.grocery.repository.LoginRepository;
import com.project.grocery.repository.StoreAddressRepository;
import com.project.grocery.repository.StoreRepository;
import com.project.grocery.repository.UserRepository;
import com.project.grocery.request.StoreAddressCreatation;
import com.project.grocery.request.StoreAddressEdit;
import com.project.grocery.request.StoreCreatationRequest;
import com.project.grocery.request.StoreEditRequest;
import com.project.grocery.responce.StoreAddressResponce;
import com.project.grocery.responce.StoreResponceDto;
import com.project.grocery.util.LoginStatus;
import com.project.grocery.util.LoginType;
import com.project.grocery.util.Md5Hashing;
import com.project.grocery.util.Status;

/**
 * @author:Samir Gautam
 * @Version:1.0
 * @Date:May 12, 2018
 * 
 */
@Service
public class StoreService {

	private static final Logger LOG = LoggerFactory.getLogger(StoreService.class);

	@Autowired
	LoginRepository loginRepository;

	@Autowired
	LoginService loginService;

	@Autowired
	StoreRepository storeRepository;

	@Autowired
	StoreAddressRepository storeAddressRepository;

	@Autowired
	UserRepository userRepository;

	/**
	 * @param userId
	 * @param storeCreatationRequest
	 */
	@Transactional
	public Store saveStore(Long userId, StoreCreatationRequest storeCreatationRequest) {
		LOG.debug("Message For Store Creatation");

		Login l = loginRepository.findLoginByUsername(storeCreatationRequest.getUsername());
		if (l != null) {
			throw new AlreadyExitException("Username Already Exits");
		}

		Store s = storeRepository.findByPhoneNoAndStatusNot(storeCreatationRequest.getPhoneNo(), Status.DELETE);
		if (s != null) {
			throw new AlreadyExitException("Phone no Already Exit");
		}

		Store store = new Store();
		store.setStoreName(storeCreatationRequest.getStoreName());
		store.setPanNo(storeCreatationRequest.getPanNo());
		store.setPhoneNo(storeCreatationRequest.getPhoneNo());
		store.setStatus(Status.ACTIVE);
		store.setCreatedDate(new Date());
		store.setCreatedBy(userId);
		store.setUsername(storeCreatationRequest.getUsername());
		store.setEmail(storeCreatationRequest.getEmail());
		LOG.debug("Adding Store....");
		Store ss = storeRepository.save(store);
		LOG.debug("Store Added");
		if (ss != null) {
			List<StoreAddressCreatation> address = storeCreatationRequest.getStoreAddress();
			if (address != null) {
				LOG.debug("Address Adding");
				for (StoreAddressCreatation add : address) {
					StoreAddress addresses = new StoreAddress();
					addresses.setDistrict(add.getDistrict());
					addresses.setZone(add.getZone());
					addresses.setVdc(add.getVdc());
					addresses.setWardNo(add.getWardNo());
					addresses.setHomeNo(add.getHomeNo());
					addresses.setWardName(add.getWardName());
					addresses.setStore(ss);

					storeAddressRepository.save(addresses);
					LOG.debug("Address Added");
				}
			}

			try {
				Login login = new Login();
				login.setPassword(Md5Hashing.getPw(storeCreatationRequest.getPassword()));
				login.setEmail(storeCreatationRequest.getEmail());
				login.setLoginStatus(LoginStatus.LOGOUT);
				login.setUsername(storeCreatationRequest.getUsername());
				login.setStore(ss);
				login.setCreatedDate(new Date());
				login.setStatus(Status.ACTIVE);
				login.setLoginType(LoginType.STOER);
				loginService.saveLogin(login);
				LOG.debug("Login Added");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

		}
		return store;
	}

	/**
	 * @param id
	 */
	@Transactional
	public void deleteCustomer(Long userId, Long id) {
		LOG.debug("Deleteing Store..");

		Store store = storeRepository.findStoreByIdAndStatusNot(id, Status.DELETE);

		if (store == null) {
			throw new NotFoundException("Store Not found");

		}

		User user = userRepository.findUserByIdAndStatusNot(userId, Status.DELETE);

		if (user == null) {
			throw new NotFoundException("User Not found");

		}
		
		Login l=loginRepository.findLoginByEmailAndStatusNot(store.getEmail(),Status.DELETE);
		if(l==null) {
			throw new NotFoundException("Store Not found !!");
		}
		l.setStatus(Status.DELETE);
		store.setStatus(Status.DELETE);
		LOG.debug("Store Deleted..");
		storeRepository.save(store);

	}

	/**
	 * @param storeEditRequest
	 */
	@Transactional
	public Store editStore(StoreEditRequest storeEditRequest) {

		LOG.debug("Request Accepted to Edit store..");
		if (storeEditRequest.getId() == null) {
			throw new RequiredException("User id is needed");
		}

		Store store = storeRepository.findStoreByIdAndStatusNot(storeEditRequest.getId(), Status.DELETE);
		if (store == null) {
			throw new NotFoundException("User not foud");
		}

		if (storeEditRequest.getEmail() != null) {
			emailDuplication(storeEditRequest.getEmail(), store);
		}

		if (storeEditRequest.getStoreName() != null) {
			store.setStoreName(storeEditRequest.getStoreName());
		}

		if (storeEditRequest.getEmail() != null) {
			store.setEmail(storeEditRequest.getEmail());
		}

		if (storeEditRequest.getPanNo() != null) {
			store.setPanNo(storeEditRequest.getPanNo());
		}

		if (storeEditRequest.getUsername() != null) {
			store.setUsername(storeEditRequest.getUsername());
		}

		if (storeEditRequest.getPhoneNo() != null) {
			store.setPhoneNo(storeEditRequest.getPhoneNo());
		}

		if (storeEditRequest.getStoreAddressEdit() != null) {
			List<StoreAddressEdit> addressEditRequests = storeEditRequest.getStoreAddressEdit();
			for (StoreAddressEdit address : addressEditRequests) {

				StoreAddress add = null;
				if (address.getId() == null) {
					add = new StoreAddress();
				}

				else {
					add = storeAddressRepository.findStoreAddressById(address.getId());
				}

				if (null != address.getDistrict()) {
					add.setDistrict(address.getDistrict());
				}
				if (null != address.getZone()) {
					add.setZone(address.getZone());
				}
				if (null != address.getVdc()) {
					add.setVdc(address.getVdc());
				}
				if (null != address.getWardNo()) {
					add.setWardNo(address.getWardNo());
				}

				if (null != address.getWardName()) {
					add.setWardName(address.getWardName());
				}

				if (null != address.getHomeNo()) {
					add.setHomeNo(address.getHomeNo());
				}

				add.setStore(store);
				storeAddressRepository.save(add);
				LOG.debug("Added address.");

			}
		}

		return store;

	}

	private void emailDuplication(String email, Store store) {
		LOG.debug("Check for Email dublication");

		Store s = storeRepository.findByEmailAndStatusNot(email, Status.DELETE);
		if (s != null && store.getId().equals(s.getId())) {

			throw new AlreadyExitException("Email Already Exit");

		}
	}

	/**
	 * @return
	 */
	public List<StoreDto> listAllStores() {
		LOG.debug("Request Accepted to list all stores");
		List<Store> store = storeRepository.findAllStoreByStatusNot(Status.DELETE);
		List<StoreDto> storeDto = new ArrayList<>();

		store.stream().forEach(u -> {
			StoreDto dto = new StoreDto();
			dto.setStoreName(u.getStoreName());
			dto.setEmail(u.getEmail());
			dto.setPhoneNo(u.getPhoneNo());
			dto.setPanNo(u.getPanNo());
			dto.setUsername(u.getUsername());
			List<StoreAddressDto> storeAddress = new ArrayList<>();
			List<StoreAddress> addresses = u.getStoreAddress();
			if (addresses != null) {
				addresses.stream().forEach(a -> {
					StoreAddressDto storeAddressDto = new StoreAddressDto();
					storeAddressDto.setId(a.getId());
					storeAddressDto.setDistrict(a.getDistrict());
					storeAddressDto.setZone(a.getZone());
					storeAddressDto.setVdc(a.getVdc());
					storeAddressDto.setWardNo(a.getWardNo());
					storeAddressDto.setWardName(a.getWardName());
					storeAddressDto.setHomeNo(a.getHomeNo());
					storeAddress.add(storeAddressDto);
				});
			}
			dto.setAddress(storeAddress);
			storeDto.add(dto);
		});
		LOG.debug("All Store List is obtain");

		return storeDto;
	}

	/**
	 * @param storeId
	 * @return
	 */
	public StoreResponceDto getStore(Long storeId) {
		LOG.debug("Request to get Store");
		Store store = storeRepository.findByIdAndStatusNot(storeId, Status.DELETE);
		if (store == null) {
			throw new NotFoundException("Store not found");
		}
		StoreResponceDto storeResponceDto = new StoreResponceDto();
		storeResponceDto.setId(store.getId());
		storeResponceDto.setStoreName(store.getStoreName());
		storeResponceDto.setEmail(store.getEmail());
		storeResponceDto.setPanNo(store.getPanNo());
		storeResponceDto.setUsername(store.getUsername());
		storeResponceDto.setPhoneNo(store.getPhoneNo());
		List<StoreAddressResponce> addressResponces = new ArrayList<>();
		List<StoreAddress> addresses = store.getStoreAddress();
		if (addresses != null) {
			addresses.stream().forEach(u -> {
				StoreAddressResponce dd = new StoreAddressResponce();
				dd.setId(u.getId());
				dd.setDistrict(u.getDistrict());
				dd.setZone(u.getZone());
				dd.setVdc(u.getVdc());
				dd.setWardName(u.getWardName());
				dd.setWardNo(u.getWardNo());
				dd.setHomeNo(u.getHomeNo());
				addressResponces.add(dd);
			});
		}
		storeResponceDto.setAddress(addressResponces);
		LOG.debug("Store Obatin");
		return storeResponceDto;
	}


//	/**
//	 * @param zone
//	 * @param district
//	 * @param vdc
//	 * @param wardNo
//	 * @return
//	 */
//	public List<StoreDto> getStoreAddress(String zone, String district, String vdc, Long wardNo) {
//
//		List<StoreAddress> address = storeAddressRepository.findStoreByZoneAndDistrictAndVdcAndWardNo(zone, district,
//				vdc, wardNo);
//		if (address == null) {
//			throw new NotFoundException("No Store Found");
//
//		}
//		StoreAddress storeAddress=new StoreAddress();
//		
//		List<Store> store=storeRepository.findByStoreAddress(address);
//		System.out.println(store);
//
//		List<StoreDto> storeDto = new ArrayList<>();
//		return storeDto;
//
//	}
}
