package com.example.careerlauncher;

public class UserClass {
	String name, email;
	int uid;

	public UserClass(int uid, String name, String email) {
		super();
		this.name = name;
		this.email = email;
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

}
