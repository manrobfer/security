package br.com.jwt.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

import static java.util.concurrent.TimeUnit.MINUTES;

@Service
public class LoginAttemptService {

    private static final int MAXIMUM_NUMBER_OF_ATTEMPTS = 5;
    private static final int ATTEMPT_INCREMENT =  1;
    private LoadingCache<String, Integer> loginAttempCache;

    public LoginAttemptService(){
        super();
        loginAttempCache = CacheBuilder.newBuilder().expireAfterWrite(15, MINUTES)
                .maximumSize(100).build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) throws Exception {
                        return 0;
                    }
                });
    }

    public void evictUserFromLoginAttemptCache(String userName){
        loginAttempCache.invalidate(userName);
    }

    public void addUserToLoginAttemptCache(String username) throws ExecutionException {
        int attempts = 0;
        attempts = ATTEMPT_INCREMENT + loginAttempCache.get(username);
        loginAttempCache.put(username,attempts);
    }

    public boolean hasExeededMaxAttempt(String userName) throws ExecutionException {
        return loginAttempCache.get(userName) >= MAXIMUM_NUMBER_OF_ATTEMPTS;
    }


}
