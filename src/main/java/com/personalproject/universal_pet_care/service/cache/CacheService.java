package com.personalproject.universal_pet_care.service.cache;

import java.util.concurrent.TimeUnit;

public interface CacheService {
    void set(String key, Object value, long timeout, TimeUnit unit);

    void set(String key, Object value);

    Object get(String key);

    boolean hasKey(String key);

    void delete(String key);

    void expire(String key, long timeout, TimeUnit unit);

    void clearCache();

    Long getTimeToLive(String key);
}
