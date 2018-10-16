package com.emergency.src.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.emergency.src.dao.UserDAOImpl;
import com.emergency.src.dto.UserDetails;
import com.emergency.src.entities.User;

@Service
public class UserServiceImpl {

	@Autowired
	private UserDAOImpl daoImpl;

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public UserDetails add(UserDetails userDetails) {
		User user = new User();
		user.setFirstname(userDetails.getFirstname());
		user.setLastname(userDetails.getLastname());
		user.setCellNo(userDetails.getCellNo());
		user.setEmail(userDetails.getEmail());
		user.setImei1(userDetails.getImei1());
		user.setImei2(userDetails.getImei2());
		user.setCreated(new Timestamp(System.currentTimeMillis()));
		user.setLastUpdated(new Timestamp(System.currentTimeMillis()));
		daoImpl.add(user);
		return userDetails;
	}

	@Transactional(readOnly = true)
	public List<UserDetails> getAllUsers() {
		List<UserDetails> udList = new ArrayList<>();
		List<User> all = daoImpl.getAll();
		for (User user : all) {
			UserDetails ud = new UserDetails();
			ud.setFirstname(user.getFirstname());
			ud.setLastname(user.getLastname());
			ud.setCellNo(user.getCellNo());
			ud.setEmail(user.getEmail());
			udList.add(ud);
		}
		return udList;
	}
	
	@Transactional(readOnly = true)
	public UserDetails getUser(Map<String,String> queryMap) {
		User user = null;
		user = daoImpl.get(User.class, queryMap);
		if (user != null) {
			UserDetails ud = new UserDetails();
			ud.setFirstname(user.getFirstname());
			ud.setLastname(user.getLastname());
			ud.setCellNo(user.getCellNo());
			ud.setEmail(user.getEmail());
			return ud;
		}
		return null;
	}
}
