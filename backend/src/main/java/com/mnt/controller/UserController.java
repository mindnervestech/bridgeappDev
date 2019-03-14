package com.mnt.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mnt.service.UserService;
import com.mnt.vm.RoleVM;
import com.mnt.vm.UserVM;

@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value="/saveUser",method = RequestMethod.POST)
	@ResponseBody
    public void saveUser(UserVM userVM) {
		userService.saveUser(userVM);
    }
	
	@RequestMapping(value="/updateUser/{id}",method = RequestMethod.POST)
	@ResponseBody
    public void updateUser(@PathVariable("id") Long id, UserVM userVM) {
		userService.updateUser(id, userVM);
    }
	
	@RequestMapping(value="/userLogin",method = RequestMethod.POST)
	@ResponseBody
    public UserVM userLogin(@RequestParam(value="email") String email, @RequestParam(value="password") String password) {
		return userService.loginUser(email, password);
    }
	
	@RequestMapping(value="/getUserRoles",method = RequestMethod.GET)
	@ResponseBody
    public List<RoleVM> getUserRoles() {
		return userService.getAllRoles();
    }
	
	@RequestMapping(value="/getUserById/{id}",method = RequestMethod.GET)
	@ResponseBody
    public UserVM getUserById(@PathVariable("id") Long id) {
		return userService.getUserById(id);
    }
	
	@RequestMapping(value="/deleteUserById/{id}",method = RequestMethod.GET)
	@ResponseBody
    public void deleteUserById(@PathVariable("id") Long id) {
		userService.deleteUserById(id);
    }
	
	@RequestMapping(value="/deleteGroupById/{id}",method = RequestMethod.GET)
	@ResponseBody
    public void deleteGroupById(@PathVariable("id") Long id) {
		userService.deleteGroupById(id);
    }
	
	@RequestMapping(value="/getAllUsers",method = RequestMethod.GET)
	@ResponseBody
    public List<UserVM> getAllUsers() {
		return userService.getAllUsers();
    }
	
	@RequestMapping(value="/forgotPassword/sentOTP",method = RequestMethod.POST)
	@ResponseBody
    public boolean sentOTP(@RequestParam(value="email") String email) {
		return userService.sentOTP(email);
    }
	
	@RequestMapping(value="/forgotPassword/validateOTP",method = RequestMethod.POST)
	@ResponseBody
	public boolean validateOTP(@RequestParam(value="email") String email, @RequestParam(value="otp") String otp) {
		return userService.validateOTP(email, otp);
	}
	
	@RequestMapping(value="/forgotPassword/changePassword",method = RequestMethod.POST)
	@ResponseBody
	public boolean changePassword(@RequestParam(value="email") String email, @RequestParam(value="newPassword") String newPassword) {
		return userService.changeForgottenPassword(email, newPassword);
	}	
	@RequestMapping(value="changePasswordFirstTime",method = RequestMethod.POST)
	@ResponseBody
	public boolean changePasswordFirstTime(@RequestParam(value="email") String email, @RequestParam(value="newPassword") String newPassword) {
		return userService.changePasswordFirstTime(email, newPassword);
	}
}
