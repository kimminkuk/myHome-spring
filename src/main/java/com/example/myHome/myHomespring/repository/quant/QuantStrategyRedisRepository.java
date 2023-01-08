package com.example.myHome.myHomespring.repository.quant;

import java.util.List;

public interface QuantStrategyRedisRepository {
    void save(List<String> companyInfoDataLists, String ZSetName);
    void saveVer2(List<String> companyInfoDataLists, String ZSetName);
}
