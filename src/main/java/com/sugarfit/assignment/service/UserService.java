package com.sugarfit.assignment.service;

import com.sugarfit.assignment.model.User;
import com.sugarfit.assignment.model.UserRequestDto;

public interface UserService {

     public String saveUser(UserRequestDto userRequestDto);

     public User getUser(String uuid);
}
