package com.petersonv.eglucometer.userRegistrationService.repositories;

import java.io.IOException;

import com.petersonv.eglucometer.userRegistrationService.configurations.RedisConfiguration;
import com.petersonv.eglucometer.userRegistrationService.entities.User;

import org.msgpack.MessagePack;
import org.redisson.Redisson;
import org.redisson.api.RBucketReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import reactor.core.publisher.Mono;

@Component
public class RedisUserRepositoryImpl implements UserRepository {

    private static Serializer serializer = new RedisUserRepositoryImpl.Serializer();

    @Autowired
    public RedisConfiguration config;

    private final int connectionTimeout = 15 * 1000;
    private final int dbNumber = 0;
    private final RedissonReactiveClient redisClient;

    public RedisUserRepositoryImpl() {
        final var cnfg = new Config();
        cnfg.useSingleServer()
            .setAddress(config.address)
            .setConnectTimeout(connectionTimeout)
            .setDatabase(dbNumber)
            .setPassword(config.password);
        redisClient = Redisson.createReactive(cnfg);
    }

    public Mono<User> getByEmail(final String emailAddress) {
        if (StringUtils.isEmpty(emailAddress))
            throw new IllegalArgumentException("User address cannot be empty.");

        final var key = getUserKey(emailAddress);
        final RBucketReactive<byte[]> bucket = redisClient.getBucket(key);

        return bucket.get().map(serializer::deserialize);
    }
    
    public Mono<Void> insert(final User user) {
        if (user == null)
            throw new IllegalArgumentException("User is null");

        final var key = getUserKey(user.getEmail());
        final var value = serializer.serialize(user);

        final var bucket = redisClient.getBucket(key);
        return bucket.set(value);
    }

    private String getUserKey(final String emailAddress) {
        return "Users:" + emailAddress;
    }

    private static class Serializer {
        private final MessagePack messagePack = new MessagePack();

        private byte[] serialize(final User user) {
            if (user == null)
                throw new IllegalArgumentException("User should not be null");
    
            try {
                return messagePack.write(user);
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    
        private User deserialize(final byte[] bytes) {
            if (bytes == null)
                throw new IllegalArgumentException("No bytes provided");
    
            try {
                return messagePack.read(bytes, User.class);
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}