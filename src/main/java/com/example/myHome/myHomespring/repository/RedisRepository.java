package com.example.myHome.myHomespring.repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RedisRepository {
    Optional<String> findByRedisKey(String key);
    void saveByRedisKey(String key, String data);
    List<String> findAll();

    Map<String, String> findAllVer2() throws IOException;
}
