package com.mnt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.*;

import com.mnt.dao.AuthUserDao;
import com.mnt.dao.GroupSetDao;
import com.mnt.dao.OTPDetailsDao;
import com.mnt.dao.PermissionMatrixDao;
import com.mnt.dao.RoleDao;
import com.mnt.domain.AuthUser;
import com.mnt.domain.GroupSet;
import com.mnt.domain.OTPDetails;
import com.mnt.domain.PermissionMatrix;
import com.mnt.domain.Role;
import com.mnt.security.TokenHandler;
import com.mnt.service.UserService;
import com.mnt.vm.PermissionsVM;
import com.mnt.vm.RoleVM;
import com.mnt.vm.UserVM;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	AuthUserDao authUserDao;
	@Autowired
	RoleDao roleDao;
	@Autowired
	GroupSetDao groupSetDao;
	@Autowired
	PermissionMatrixDao permissionMatrixDao;
	@Autowired
	OTPDetailsDao otpDetailsDao;
	
	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	AuthenticationManager authenticationProvider;
	
	
	public void saveUser(UserVM userVM) {
		AuthUser authUser = new AuthUser();
		authUser.setUsername(userVM.getUserName());
		authUser.setEmail(userVM.getEmail());
		authUser.setPassword(userVM.getPassword());
		authUser.setFirstName(userVM.getFirstName());
		authUser.setLastName(userVM.getLastName());
		authUser.setPhone(userVM.getPhone());
		authUser.setActive(false);
		/*Role role = roleDao.loadById(userVM.getRoleId());
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		authUser.setRoles(roles);*/
		List<GroupSet> groups = new ArrayList<>();
		for(Long groupId: userVM.getGroupIdList()) {
			GroupSet group = groupSetDao.loadById(groupId);
			groups.add(group);
		}
		authUser.setGroups(groups);
		sendUserNameAndPassword(userVM.getEmail(), userVM.getPassword());
		authUserDao.save(authUser);
	}
	
	public void updateUser(Long id, UserVM userVM) {
		AuthUser authUser = authUserDao.loadById(id);
		authUser.setUsername(userVM.getUserName());
		authUser.setEmail(userVM.getEmail());
		authUser.setPassword(userVM.getPassword());
		authUser.setFirstName(userVM.getFirstName());
		authUser.setLastName(userVM.getLastName());
		authUser.setPhone(userVM.getPhone());
		authUser.setActive(true);
		/*Role role = roleDao.loadById(userVM.getRoleId());
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		authUser.setRoles(roles);*/
		List<GroupSet> groups = new ArrayList<>();
		for(Long groupId: userVM.getGroupIdList()) {
			GroupSet group = groupSetDao.loadById(groupId);
			groups.add(group);
		}
		authUser.setGroups(groups);
		authUserDao.save(authUser);
	}
	
	public UserVM loginUser(String email, String password) {
		AuthUser user = authUserDao.loginUser(email, password);
		UserVM vm = new UserVM();
		if(user == null) {
			vm.setId(-1L);
		} 
		else {
	        
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
	        AuthUser details = new AuthUser();
	        details.setUsername(email);
	        token.setDetails(details);
	        Authentication auth = authenticationProvider.authenticate(token);
	        SecurityContextHolder.getContext().setAuthentication(auth);
	        
			System.out.println(TokenHandler.createTokenForUser(user.getEmail()));
			
			vm.setId(user.getId());
			vm.setEmail(user.getEmail());
			//vm.setPassword(user.getPassword());
			vm.setUserName(user.getUsername());
			vm.setFirstName(user.getFirstName());
			vm.setLastName(user.getLastName());
			vm.setPhone(user.getPhone());
			List<PermissionsVM> permissionsList = new ArrayList<>();
			for(GroupSet group: user.getGroups()) {
				List<PermissionMatrix> list = permissionMatrixDao.getPermissionsByGroup(group);
				for(PermissionMatrix matrix: list) {
					PermissionsVM permissionsVM = new PermissionsVM();
					permissionsVM.setId(matrix.getId());
					permissionsVM.setName(matrix.getPermissionObj().getName());
					permissionsVM.setModule(matrix.getPermissionObj().getModule());
					permissionsList.add(permissionsVM);
				}
			}
			vm.setPermissions(permissionsList);
			vm.setToken(TokenHandler.createTokenForUser(user.getEmail()));
			vm.setActive(user.isActive());
		}
		return vm;
	}
	
	public List<RoleVM> getAllRoles() {
		List<RoleVM> vmList = new ArrayList<>();
		List<Role> roleList = roleDao.loadAll();
		for(Role role: roleList) {
			RoleVM vm = new RoleVM();
			vm.setId(role.getId());
			vm.setName(role.getName());
			vmList.add(vm);
		}
		return vmList;
	}
	
	public List<UserVM> getAllUsers() {
		List<UserVM> userVMList = new ArrayList<>();
		List<AuthUser> users = authUserDao.loadAll();
		for(AuthUser user: users) {
			UserVM vm = new UserVM();
			vm.setId(user.getId());
			vm.setActive(user.isActive());
			vm.setEmail(user.getEmail());
			vm.setFirstName(user.getFirstName());
			vm.setLastName(user.getLastName());
			vm.setPassword(user.getPassword());
			vm.setPhone(user.getPhone());
			vm.setUserName(user.getUsername());
			if(!user.getRoles().isEmpty())
			vm.setRole(user.getRoles().get(0).getName());
			userVMList.add(vm);
		}
		return userVMList;
	}
	
	public UserVM getUserById(Long id) {
		AuthUser user = authUserDao.loadById(id);
		UserVM vm = new UserVM();
		vm.setId(user.getId());
		vm.setActive(user.isActive());
		vm.setEmail(user.getEmail());
		vm.setFirstName(user.getFirstName());
		vm.setLastName(user.getLastName());
		vm.setPassword(user.getPassword());
		vm.setPhone(user.getPhone());
		vm.setUserName(user.getUsername());
		if(!user.getRoles().isEmpty()) {
			vm.setRole(user.getRoles().get(0).getName());
			vm.setRoleId(user.getRoles().get(0).getId());
		}
		List<Long> groupIds = new ArrayList<>();
		for(GroupSet group: user.getGroups()) {
			groupIds.add(group.getId());
		}
		vm.setGroupIdList(groupIds);
		return vm;
	}

	
	public void deleteUserById(Long userId) {
		AuthUser user = authUserDao.loadById(userId);
		authUserDao.delete(user);
	}
	
	public void deleteGroupById(Long groupId) {
		GroupSet group = groupSetDao.loadById(groupId);
		permissionMatrixDao.deleteGroupPermissions(groupId);
		authUserDao.deleteUsersGroup(groupId);
		groupSetDao.delete(group);
	}

	String GenerateRandomNumber(int charLength) {
        return String.valueOf(charLength < 1 ? 0 : new Random()
                .nextInt((9 * (int) Math.pow(10, charLength - 1)) - 1)
                + (int) Math.pow(10, charLength - 1));
    }
	
	public void sendUserNameAndPassword(String email, String password) {

			 MimeMessage message = sender.createMimeMessage();
		      MimeMessageHelper helper = new MimeMessageHelper(message);
	          try {
	        	  helper.setFrom("mindnervesdemo@gmail.com");
	              helper.setTo(email);
	              helper.setText("Hey! "+email+"\nCongratulations !!\nYour AEIGS account is created by AEIGS Group,\n Your login id is :"+email+"\n and password is :"+password);
	              helper.setSubject("Congratulations Your Account was Successfully Created #AEGIS");
	              sender.send(message);
	              System.out.println("Email Send to :"+email);     
	          }
	          catch (Exception e) {
	        	 e.printStackTrace();	
		}
	}	
	@Override
	public boolean sentOTP(String email) {
		if(authUserDao.findByUserName(email) != null){
			 MimeMessage message = sender.createMimeMessage();
		      MimeMessageHelper helper = new MimeMessageHelper(message);
	          try {
	        	  helper.setFrom("mindnervesdemo@gmail.com");
	              helper.setTo(email.toString());
	              String otp=GenerateRandomNumber(4);
	              helper.setText("Hey! "+email+"\nAEIGS Group has received a request to reset the password for your account.\nFor reset your password OTP is : "+otp);
	              helper.setSubject("Password Recovery #AEGIS");
	              otpDetailsDao.setOTPandEmail(email, otp);
	              sender.send(message);
	              System.out.println("Email Send to :"+email);     
	              return true;
	          }
	          catch (Exception e) {
	        	 e.printStackTrace();
	             return false;
			}
		}
        return false;
 
	}
	
	@Override
	public boolean validateOTP(String email, String otp) {
		return otpDetailsDao.validateEmailAndOTP(email, otp);
	}
	
	@Override
	public boolean changeForgottenPassword(String email,String newPassword) {
		return authUserDao.ChangePassword(email, newPassword);
	}

	@Override
	public boolean changePasswordFirstTime(String email, String newPassword) {
		return authUserDao.ChangePasswordFirstTime(email , newPassword);
	}
}
