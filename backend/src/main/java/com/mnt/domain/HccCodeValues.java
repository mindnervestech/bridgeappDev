package com.mnt.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.opencsv.bean.CsvBindByName;

@Entity
@Table(name = "hcc_code_values")
public class HccCodeValues extends BaseDomain {

	private static final long serialVersionUID = 1L;
	
	@CsvBindByName(column = "Diagnosis Code")
	@Column(name = "diagnosis_code")
	private String diagnosisCode;
	
	@CsvBindByName(column = "V 21")
	@Column(name = "v_21")
	private String v21;

	public String getDiagnosisCode() {
		return diagnosisCode;
	}

	public void setDiagnosisCode(String diagnosisCode) {
		this.diagnosisCode = diagnosisCode;
	}

	public String getV21() {
		return v21;
	}

	public void setV21(String v21) {
		this.v21 = v21;
	}
}
