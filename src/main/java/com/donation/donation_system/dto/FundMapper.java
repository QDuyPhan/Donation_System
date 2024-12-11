package com.donation.donation_system.dto;

import com.donation.donation_system.model.Fund;

//import com.donation.donation_system.entity.Fund;
//import org.mapstruct.Mapper;
////
//@Mapper(componentModel = "spring")
public interface FundMapper {
    Fund dtoToEntity(fundDTO fundDTO); // Chuyển DTO sang Entity
    fundDTO entityToDto(Fund fund);   // Chuyển Entity sang DTO
}
