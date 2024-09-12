package com.banking.oracle.dao.branch;

public class Branch {
	Integer branchCode;
	String branchName;
	String ifscCode;
	
	public Branch() {}

	public Branch(Integer branchCode, String branchName, String ifscCode) {
		super();
		this.branchCode = branchCode;
		this.branchName = branchName;
		this.ifscCode = ifscCode;
	}

	public Integer getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(Integer branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	@Override
	public String toString() {
		return "Branch [branchCode=" + branchCode + ", branchName=" + branchName + ", ifscCode=" + ifscCode + "]";
	}
} 