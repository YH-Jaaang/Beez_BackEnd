package com.blockb.beez.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

import com.blockb.beez.dto.PassCheckDto;

@Mapper
public interface PassCheckDao {
    Optional<PassCheckDto> findByUserPassConfirm(Long userId);
    Optional<PassCheckDto> findByUserPassCheck(Long userId);
    void passCount(Long userId);
    void save(PassCheckDto passCheckDto);
}