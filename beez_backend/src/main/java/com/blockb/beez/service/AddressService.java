package com.blockb.beez.service;

import java.util.Collection;
import java.util.List;

import com.blockb.beez.dao.AddressDao;
import com.blockb.beez.dto.AddressDto;
import com.blockb.beez.dto.AddressListDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    AddressDao addressDao;
    
    public List<AddressDto> findAddress(AddressListDto address){
        System.out.println(address);
        return addressDao.findAddress(address);
    }
}
