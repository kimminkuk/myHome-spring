package com.example.myHome.myHomespring.repository;
import java.io.IOException;
import java.util.*;

public class RedisMemberRepository_2 implements RedisRepository {

    private static Map<String, String> store = new HashMap<>();
    private static Long sequence = 0L;

    @Override
    public void saveByRedisKey(String key, String data) {
        store.put(key, data);
        return;
    }

    @Override
    public Optional<String> findByRedisKey(String key) {
        return Optional.ofNullable(store.get(key));
    }

    @Override
    public List<String> findAll() {
        return new ArrayList<>(store.values());
    }
    @Override
    public Map<String, String> findAllVer2() throws IOException {
        return null;
    }
}
