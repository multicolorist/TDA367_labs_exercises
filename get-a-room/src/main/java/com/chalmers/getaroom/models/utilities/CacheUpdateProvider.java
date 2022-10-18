package com.chalmers.getaroom.models.utilities;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Interface for providing objects to cache
 * @param <T> The type of the cached objects
 */
public interface CacheUpdateProvider<T> {
    @NotNull
    T getNewDataToCache() throws IOException;
}
