package com.chalmers.group30.models.utilities;

import java.io.IOException;
import java.time.Instant;

public interface GenericCacheInterface<T> {

    /**
     * Causes a refresh of the cached data. Thread safe.
     */
    void refreshCache() throws IOException;

    /**
     * Gets the last instant the cache was refreshed
     *
     * @return The last refresh instant
     */
    Instant getLastCacheRefreshInstant();

    /**
     * Get the data from the cache. If cache is empty, will cause a cache refresh.
     *
     * @return The cached data
     */
    T getData() throws IOException;
}
