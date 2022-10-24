package com.chalmers.getaroom.models.utilities;

import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A cache that can hold any object of type T
 *
 * @param <T> The type of the cached objects
 */
public class GenericCache<T> implements GenericCacheInterface<T> {

    private T cache = null;
    private final transient CacheUpdateProvider<T> updateProvider;
    private Instant lastRefresh = null;
    private final transient Lock cacheRefreshInProgressLock = new ReentrantLock();
    private boolean cacheRefreshSucceeded = false;

    /**
     * Create a new instance of GenericCache
     *
     * @param updateProvider Provider used to update cache data
     */
    public GenericCache(CacheUpdateProvider<T> updateProvider) {
        this.updateProvider = updateProvider;
    }

    /**
     * Causes a refresh of the cached data. Thread safe.
     */
    public void refreshCache() throws IOException {
        if (cacheRefreshInProgressLock.tryLock()) {
            try {
                cacheRefreshSucceeded = false;
                cache = updateProvider.getNewDataToCache();
                lastRefresh = Instant.now();
                cacheRefreshSucceeded = true;
            } finally {
                cacheRefreshInProgressLock.unlock();
            }
        } else {
            // Cache update already in progress on another thread. Wait for it to complete
            cacheRefreshInProgressLock.lock();
            cacheRefreshInProgressLock.unlock();
            if (!cacheRefreshSucceeded) {
                // Cache update appears to have failed. Trying again
                refreshCache();
            }
        }
    }

    /**
     * Gets the last instant the cache was refreshed
     *
     * @return The last refresh instant
     */
    public Instant getLastCacheRefreshInstant() {
        return lastRefresh;
    }

    /**
     * Get the data from the cache. If cache is empty, will cause a cache refresh.
     *
     * @return The cached data
     */
    public T getData() throws IOException {
        if (cache == null) {
            refreshCache();
        }

        return cache;
    }


}
