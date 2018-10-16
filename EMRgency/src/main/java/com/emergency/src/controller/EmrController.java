package com.emergency.src.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emergency.src.dto.UserDetails;
import com.emergency.src.service.UserServiceImpl;


@Controller
public class EmrController {
	
	@Autowired
	private UserServiceImpl userService;

	@RequestMapping(value = "/registeruser", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<UserDetails> registerUser(@RequestBody UserDetails userDetails) {
		System.out.println(userDetails.getFirstname());
		System.out.println(userDetails.getLastname());
		System.out.println(userDetails.getCellNo());
		System.out.println(userDetails.getEmail());
		
		userService.add(userDetails);
		return new ResponseEntity<UserDetails>(userDetails, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value = "/getalluser", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getAllUser(){
		List<UserDetails> allUsers = userService.getAllUsers();
		return new ResponseEntity<List<UserDetails>>(allUsers, HttpStatus.OK);
	}
	
//	@RequestMapping(value = "/getuser", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody ResponseEntity<UserDetails> getUser(@RequestParam(value="cellno",required=true) String cellno){
//		UserDetails ud = userService.getUser(cellno);
//		return new ResponseEntity<UserDetails>(ud, HttpStatus.OK);
//	}
	
	@RequestMapping(value = "/getuser", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<UserDetails> getUser(@RequestParam Map<String,String> queryMap){
		UserDetails ud = userService.getUser(queryMap);
		return new ResponseEntity<UserDetails>(ud, HttpStatus.OK);
	}

}
