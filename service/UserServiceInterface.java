package com.salah.springSecurity.service;

import java.util.List;

import com.salah.springSecurity.entity.User;
import com.salah.springSecurity.entity.UserRecord;

public interface UserServiceInterface {
	User add(User user);

	List<UserRecord> getAllUsers();

	void delete(String email);

	User getUser(String email);

	User update(User user);

}
