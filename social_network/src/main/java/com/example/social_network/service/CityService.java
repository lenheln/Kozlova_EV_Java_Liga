package com.example.social_network.service;

import com.example.social_network.domain.City;
import com.example.social_network.domain.Region;
import com.example.social_network.dto.CityOnUserPageDto;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    public CityOnUserPageDto convertToCityOnPageDto(City city){
        CityOnUserPageDto cityDto = new CityOnUserPageDto();
        cityDto.setName(city.getName());
        Region region = city.getRegion();
        if (region != null) {
            cityDto.setRegionName(region.getName());
        }
        return cityDto;
    }
}
