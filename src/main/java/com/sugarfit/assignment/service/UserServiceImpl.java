package com.sugarfit.assignment.service;

import com.sugarfit.assignment.exception.UserNotFoundException;
import com.sugarfit.assignment.model.User;
import com.sugarfit.assignment.model.UserRequestDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private Map<String, User> savedUserMap;

    public UserServiceImpl() {
        savedUserMap = new HashMap<>();
    }

    @Override
    public String saveUser(UserRequestDto userRequestDto) {
        log.info("Received new Save request");
        User user = new User(userRequestDto.getUserId(), userRequestDto.getValue());
        savedUserMap.put(user.getUuId(), user);
        log.debug("Saved user: {} and generated UUID: {}", user.getUserId(), user.getUuId());
        return user.getUuId();
    }

    @Override
    public User getUser(String uuid) {
        log.info("Get user having UUID: {}", uuid);

        if (!savedUserMap.containsKey(uuid))
            throw new UserNotFoundException("No Data exists for uuid:" + uuid);

        return savedUserMap.get(uuid);
    }
}