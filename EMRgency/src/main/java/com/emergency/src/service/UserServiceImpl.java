package com.emergency.src.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.emergency.src.dao.ContactPersonDAOImpl;
import com.emergency.src.dao.UserDAOImpl;
import com.emergency.src.dto.ContactPersonDetails;
import com.emergency.src.dto.UserDetails;
import com.emergency.src.entities.ContactPerson;
import com.emergency.src.entities.User;
import com.emergency.src.exception.EmergencyException;
import com.emergency.src.exception.UserNotFoundException;
import com.emergency.src.util.EmergencyUtil;

@Service
public class UserServiceImpl {

	@Autowired
	private UserDAOImpl daoImpl;

	@Autowired
	private ContactPersonDAOImpl cpDaoImpl;

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public UserDetails add(UserDetails userDetails) {
		if (userDetails != null) {
			Map<String, String> queryMap = new HashMap<String, String>();
			queryMap.put("cellno", userDetails.getCellno());
			queryMap.put("imei1", userDetails.getImei1());
			queryMap.put("imei2", userDetails.getImei1());
			if ((daoImpl.get(User.class, queryMap) != null))
				throw new EmergencyException("User with given cell no " + userDetails.getCellno() + " already Exists");

			User user = new User();
			EmergencyUtil.convertDTOToEntity(userDetails, user);
			user.setCreated(new Timestamp(System.currentTimeMillis()));
			user.setLastUpdated(new Timestamp(System.currentTimeMillis()));
			ContactPersonDetails[] cpDetails = userDetails.getCpDetails();
			if (cpDetails != null && cpDetails.length > 0) {
				Set<ContactPersonDetails> cps = new HashSet<ContactPersonDetails>(Arrays.asList(cpDetails));
				for (ContactPersonDetails cpd : cps) {
					ContactPerson cp = cpDaoImpl.get(ContactPerson.class, "cellno", cpd.getCellno());
					if (cp == null) {
						cp = new ContactPerson();
						EmergencyUtil.convertDTOToEntity(cpd, cp);
						cp.setLastUpdated(new Timestamp(System.currentTimeMillis()));
						user.getContactPersons().add(cp);
					} else
						user.getContactPersons().add(cp);
				}
			}
			daoImpl.add(user);
			EmergencyUtil.convertEntityToDTO(user, userDetails);
		}
		return userDetails;
	}

	@Transactional(readOnly = true)
	public List<UserDetails> getAllUsers() {
		List<UserDetails> udList = new ArrayList<>();
		List<User> all = daoImpl.getAll();
		for (User user : all) {
			UserDetails uDetails = new UserDetails();
			EmergencyUtil.convertEntityToDTO(user, uDetails);
			if (!CollectionUtils.isEmpty(user.getContactPersons())) {
				ContactPersonDetails[] cpda = new ContactPersonDetails[user.getContactPersons().size()];
				Stream<ContactPerson> stream = user.getContactPersons().stream();
				ContactPerson[] cpa = stream.toArray(ContactPerson[]::new);
				for (int i = 0; i < cpa.length; i++) {
					ContactPersonDetails cpd = new ContactPersonDetails();
					ContactPerson cp = cpa[i];
					EmergencyUtil.convertEntityToDTO(cp, cpd);
					cpda[i] = cpd;
				}
				uDetails.setCpDetails(cpda);
			}
			udList.add(uDetails);
		}
		return udList;
	}

	@Transactional(readOnly = true)
	public UserDetails getUser(Map<String, String> queryMap) {
		UserDetails ud = null;
		User user = daoImpl.get(User.class, queryMap);
		if (user != null) {
			ud = new UserDetails();
			EmergencyUtil.convertEntityToDTO(user, ud);
			if (!CollectionUtils.isEmpty(user.getContactPersons())) {
				ContactPersonDetails[] cpda = new ContactPersonDetails[user.getContactPersons().size()];
				Stream<ContactPerson> stream = user.getContactPersons().stream();
				ContactPerson[] cpa = stream.toArray(ContactPerson[]::new);
				for (int i = 0; i < cpa.length; i++) {
					ContactPersonDetails cpd = new ContactPersonDetails();
					ContactPerson cp = cpa[i];
					EmergencyUtil.convertEntityToDTO(cp, cpd);
					cpda[i] = cpd;
				}
				ud.setCpDetails(cpda);
			}
		} else {
			throw new UserNotFoundException("User with given cell no " + queryMap.get("cellno") + " not found");
		}
		return ud;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public UserDetails updateUser(UserDetails userDetails) {
		UserDetails uDetails = null;
		User user = null;
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("cellno", userDetails.getCellno());
		user = daoImpl.get(User.class, queryMap);
		if (user != null) {
			EmergencyUtil.convertToEntityForUpdate(userDetails, user);
			user.setLastUpdated(new Timestamp(System.currentTimeMillis()));
			ContactPersonDetails[] cpDetails = userDetails.getCpDetails();
			if (cpDetails != null && cpDetails.length > 0) {
				Stream<ContactPerson> stream = user.getContactPersons().stream();
				ContactPerson[] cpa = stream.toArray(ContactPerson[]::new);
				for (int i = 0; i < cpDetails.length; i++) {
					for (int j = 0; i < cpa.length; i++) {
						if(cpDetails[i].getName().equals(cpa[j].getName())) {
							EmergencyUtil.convertDTOToEntity(cpDetails[i], cpa[j]);
						}
						
					}
					
				}
			}
			daoImpl.update(user);
			if (!CollectionUtils.isEmpty(user.getContactPersons())) {
				ContactPersonDetails[] cpda = new ContactPersonDetails[user.getContactPersons().size()];
				Stream<ContactPerson> stream = user.getContactPersons().stream();
				ContactPerson[] cpa = stream.toArray(ContactPerson[]::new);
				for (int i = 0; i < cpa.length; i++) {
					ContactPerson cp = cpa[i];
					cpda[i] = EmergencyUtil.convertToDTO(cp, ContactPersonDetails.class);
				}
				uDetails = EmergencyUtil.convertToDTO(user, UserDetails.class);
				uDetails.setCpDetails(cpda);
			}
		} else {
			throw new UserNotFoundException("User with given cell no " + userDetails.getCellno() + " not found");
		}
		return uDetails;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public boolean removeUser(Map<String, String> queryMap) {
		User user = null;
		user = daoImpl.get(User.class, queryMap);
		if (user != null) {
			if (!CollectionUtils.isEmpty(user.getContactPersons())) {
				for (ContactPerson cp : user.getContactPersons()) {
					cpDaoImpl.remove(cp);
				}
			}
			user.getContactPersons().clear();
			daoImpl.remove(user);
			return true;
		} else {
			throw new UserNotFoundException("User with given cell no " + queryMap.get("cellno") + " not found");
		}
	}

}
