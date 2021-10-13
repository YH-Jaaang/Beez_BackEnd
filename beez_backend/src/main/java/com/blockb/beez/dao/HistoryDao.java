package com.blockb.beez.dao;

import java.util.List;
import java.util.Map;

import com.blockb.beez.dto.HistoryDto;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HistoryDao {
    public void chargeHistory(Map<String, String> map);
    public List<HistoryDto> historyList(Long userId);
}
