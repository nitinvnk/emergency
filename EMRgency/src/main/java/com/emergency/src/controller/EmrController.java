package com.emergency.src.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import com.emergency.src.exception.UserNotFoundException;
import com.emergency.src.service.UserServiceImpl;

@Controller
public class EmrController {

	@Autowired
	private UserServiceImpl userService;

	@RequestMapping(value = "/registeruser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<UserDetails> registerUser(@Valid @RequestBody UserDetails userDetails) {
		userService.add(userDetails);
		return new ResponseEntity<UserDetails>(userDetails, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/getalluser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> getAllUser() {
		List<UserDetails> allUsers = userService.getAllUsers();
		return new ResponseEntity<List<UserDetails>>(allUsers, HttpStatus.OK);
	}

	@RequestMapping(value = "/getuser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<UserDetails> getUser(@RequestParam Map<String, String> queryMap) {
		UserDetails ud = userService.getUser(queryMap);
		if (ud == null) {
			String cellno = queryMap.get("cellno");
			throw new UserNotFoundException("User with given cell no " + cellno + " not found");
		}
		return new ResponseEntity<UserDetails>(ud, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateuser", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<UserDetails> updateUser(@Valid @RequestBody UserDetails userDetails) {
		UserDetails ud = userService.updateUser(userDetails);
		if (ud == null) {
			throw new UserNotFoundException("User with given cell no " + userDetails.getCellno() + " not found");
		}
		return new ResponseEntity<UserDetails>(ud, HttpStatus.OK);
	}

	@RequestMapping(value = "/removeuser", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<?> removeUser(@RequestParam Map<String, String> queryMap) {
		if (userService.removeUser(queryMap)) {
			return new ResponseEntity(HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}

}
