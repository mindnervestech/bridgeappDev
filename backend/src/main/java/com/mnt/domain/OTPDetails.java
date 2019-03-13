package com.mnt.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "otp_details")
public class OTPDetails extends BaseDomain {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "otp")
	private String opt;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}
	
	
}

