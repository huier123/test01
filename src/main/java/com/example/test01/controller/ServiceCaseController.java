package com.example.test01.controller;

import com.example.test01.dao.ServiceCaseDao;
import com.example.test01.dto.ServiceCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.MultiGetItem;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/serviceCase")
public class ServiceCaseController {

    @Autowired
    ServiceCaseDao serviceCaseDao;

    @PostMapping(value = "/batchServiceCase", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void batchServiceCase(@RequestBody() List<ServiceCase> serviceCases){
        serviceCaseDao.saveServiceCases(serviceCases);
    }


    @GetMapping(value = "/indexExists")
    public Boolean indexExists(){
        return serviceCaseDao.indexExists();
    }

    @GetMapping(value = "/selectServiceCase")
    public List<ServiceCase> selectServiceCase(String field, String value){
        return serviceCaseDao.selectServiceCase(field, value);
    }
}
