package com.mnt.dao;

import com.mnt.domain.OTPDetails;

public interface OTPDetailsDao  extends BaseDao<OTPDetails> {

	boolean setOTPandEmail(String email, String otp);

	boolean validateEmailAndOTP(String email, String otp);


}
