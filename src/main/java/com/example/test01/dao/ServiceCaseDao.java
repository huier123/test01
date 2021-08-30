package com.example.test01.dao;

import com.example.test01.dto.ServiceCase;
import org.springframework.data.elasticsearch.core.MultiGetItem;

import java.util.List;

public interface ServiceCaseDao {

    boolean indexExists();

    void saveServiceCases(List<ServiceCase> serviceCases);

    List<ServiceCase> selectServiceCase(String field, String value);
}
