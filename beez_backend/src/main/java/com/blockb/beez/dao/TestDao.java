package com.blockb.beez.dao;

import java.util.List;

import com.blockb.beez.vo.TestVo;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TestDao {
    public List<TestVo> testPrintMember();
}
