package com.blockb.beez.dao;

import java.util.Collection;
import java.util.List;

import com.blockb.beez.dto.AddressDto;
import com.blockb.beez.dto.AddressListDto;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AddressDao {
    public List<AddressDto> findAddress(AddressListDto address);
}
