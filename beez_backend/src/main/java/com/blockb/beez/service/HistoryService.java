package com.blockb.beez.service;

import java.util.List;

import com.blockb.beez.dao.HistoryDao;
import com.blockb.beez.dto.HistoryDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HistoryService { 

    @Autowired
    HistoryDao historyDao;

    public List<HistoryDto> historyList(Long userId) {
        return historyDao.historyList(userId);
    }
}
