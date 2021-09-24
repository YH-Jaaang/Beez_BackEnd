package com.blockb.beez.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

import com.blockb.beez.dto.UserDto;

@Mapper
public interface UserMapper {
    //Optional는 널포인트오류를 방지
    Optional<UserDto> findUserByUsername(String username);
    Optional<UserDto> findByUserId(Long userId);
    void save(UserDto userDto);
}