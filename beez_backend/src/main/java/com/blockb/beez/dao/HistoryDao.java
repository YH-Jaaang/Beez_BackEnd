package com.blockb.beez.dao;

import java.util.Map;
import java.util.List;

import com.blockb.beez.dto.HistoryDto;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface HistoryDao {
    public void chargeHistory(Map<String, String> map);
    public void chargeHistory(HistoryDto historyDto);
    public List<HistoryDto> historyList(Long userId);
}
