package com.example.api.model;

import java.util.Set;

import org.hyperledger.fabric.sdk.Enrollment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User")
public class User {
	@Id
	private String name;
	private String password;
	private Set<String> roles;
	private String account;
	private String affiliation;
	private Enrollment enrollment;
	private String mspId;

	public User(String name, String password, Set<String> roles, String account, String affiliation,
			Enrollment enrollment, String mspId) {
		this.name = name;
		this.password = password;
		this.roles = roles;
		this.account = account;
		this.affiliation = affiliation;
		this.enrollment = enrollment;
		this.mspId = mspId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	public String getMspId() {
		return mspId;
	}

	public void setMspId(String mspId) {
		this.mspId = mspId;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + ", roles=" + roles + ", account=" + account
				+ ", affiliation=" + affiliation + ", enrollment=" + enrollment + ", mspId=" + mspId + "]";
	}

}
