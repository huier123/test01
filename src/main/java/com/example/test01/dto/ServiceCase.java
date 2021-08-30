package com.example.test01.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "servicecase")
public class ServiceCase {

    @Id
    private String id;

    @Field(type = FieldType.Keyword, index = true)
    private String caseId;

    @Field(type = FieldType.Keyword, index = true)
    private String referenceId;

    @Field(type = FieldType.Keyword, index = true)
    private String serialNumber;

    @Field(type = FieldType.Keyword, index = true)
    private String machineType;

    @Field(type = FieldType.Text,index = true)
    private String subSeriesName;

    @Field(type = FieldType.Text,index = true)
    private String seriesName;

    @Field(type = FieldType.Keyword, index = true)
    private String sourceType;
}
