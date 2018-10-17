package com.emergency.src.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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
			User user = EmergencyUtil.convertToEntity(userDetails, User.class);
			user.setCreated(new Timestamp(System.currentTimeMillis()));
			user.setLastUpdated(new Timestamp(System.currentTimeMillis()));
			ContactPersonDetails[] cpDetails = userDetails.getCpDetails();
			if (cpDetails != null && cpDetails.length > 0) {
				Set<ContactPersonDetails> cps = new HashSet<ContactPersonDetails>(Arrays.asList(cpDetails));
				for (ContactPersonDetails cpd : cps) {
					ContactPerson cp = cpDaoImpl.get(ContactPerson.class, "cellno", cpd.getCellno());
					if (cp == null) {
						cp = EmergencyUtil.convertToEntity(cpd, ContactPerson.class);
						cp.setLastUpdated(new Timestamp(System.currentTimeMillis()));
						user.getContactPersons().add(cp);
					} else
						user.getContactPersons().add(cp);
				}
			}
			daoImpl.add(user);
		}
		return userDetails;
	}

	@Transactional(readOnly = true)
	public List<UserDetails> getAllUsers() {
		List<UserDetails> udList = new ArrayList<>();
		List<User> all = daoImpl.getAll();
		for (User user : all) {
			UserDetails ud = EmergencyUtil.convertToDTO(user, UserDetails.class);
			if (!CollectionUtils.isEmpty(user.getContactPersons())) {
				ContactPersonDetails[] cpda = new ContactPersonDetails[user.getContactPersons().size()];
				Stream<ContactPerson> stream = user.getContactPersons().stream();
				ContactPerson[] cpa = stream.toArray(ContactPerson[]::new);
				for (int i = 0; i < cpa.length; i++) {
					ContactPerson cp = cpa[i];
					cpda[i] = EmergencyUtil.convertToDTO(cp, ContactPersonDetails.class);
				}
				ud.setCpDetails(cpda);
			}

			udList.add(ud);
		}
		return udList;
	}

	@Transactional(readOnly = true)
	public UserDetails getUser(Map<String, String> queryMap) {
		User user = null;
		user = daoImpl.get(User.class, queryMap);
		if (user != null && user.getContactPersons() != null) {
			ContactPersonDetails[] cpda = new ContactPersonDetails[user.getContactPersons().size()];
			Stream<ContactPerson> stream = user.getContactPersons().stream();
			ContactPerson[] cpa = stream.toArray(ContactPerson[]::new);
			for (int i = 0; i < cpa.length; i++) {
				ContactPerson cp = cpa[i];
				cpda[i] = EmergencyUtil.convertToDTO(cp, ContactPersonDetails.class);
			}
			UserDetails ud = EmergencyUtil.convertToDTO(user, UserDetails.class);
			ud.setCpDetails(cpda);
			return ud;
		}
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public UserDetails updateUser(UserDetails userDetails) {
		User user = null;
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("cellno", userDetails.getCellno());
		user = daoImpl.get(User.class, queryMap);
		if (user != null) {
			user.setFirstname(userDetails.getFirstname());
			user.setLastname(userDetails.getLastname());
			user.setEmail(userDetails.getEmail());
			user.setLastUpdated(new Timestamp(System.currentTimeMillis()));
			daoImpl.update(user);
		}
		return userDetails;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public boolean removeUser(Map<String, String> queryMap) {
		User user = null;
		user = daoImpl.get(User.class, queryMap);
		if (user != null) {
			daoImpl.remove(user);
			return true;
		}
		return false;
	}

}
