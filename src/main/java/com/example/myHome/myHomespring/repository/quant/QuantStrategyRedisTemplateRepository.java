package com.example.myHome.myHomespring.repository.quant;

import io.lettuce.core.LettuceFutures;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class QuantStrategyRedisTemplateRepository implements QuantStrategyRedisRepository {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(List<String> companyInfoDataLists, String ZSetName) {
        //StatefulRedisConnection connect code
        RedisClient client = RedisClient.create("redis://localhost");

        // Redis Asynchronous Pipeline
        StatefulRedisConnection<String, String> connection = client.connect();
        RedisAsyncCommands<String, String> async = connection.async();

        //disable auto-flushing
        async.setAutoFlushCommands(false);

        // perform a series of independent calls
        ArrayList<RedisFuture<?>> futures = new ArrayList<>();

        int companyInfoDataListsSize = companyInfoDataLists.size();
        for ( int i = 0; i < companyInfoDataListsSize; i++ ) {
            futures.add(async.zadd(ZSetName, i, companyInfoDataLists.get(i)));
        }

        //write all commands to the transport layer
        async.flushCommands();

        // synchronization example: Wait until all futures complete
        LettuceFutures.awaitAll(5, TimeUnit.SECONDS, futures.toArray(new RedisFuture[futures.size()]));

        // later
        connection.close();
    }

    @Override
    public void saveVer2(List<String> companyInfoDataLists, String ZSetName) {
        //StatefulRedisConnection connect code
        RedisClient client = RedisClient.create("redis://localhost");

        try (StatefulRedisConnection<String, String> connection = client.connect()) {
            RedisAsyncCommands<String, String> async = connection.async();
            async.multi();
            for (int i = 0; i < companyInfoDataLists.size(); i++) {
                async.zadd(ZSetName, i, companyInfoDataLists.get(i));
            }
            async.exec();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}