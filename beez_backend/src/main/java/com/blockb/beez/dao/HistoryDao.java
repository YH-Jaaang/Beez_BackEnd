package com.blockb.beez.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HistoryDao {
    public void chargeHistory(Map<String, String> map);
}
