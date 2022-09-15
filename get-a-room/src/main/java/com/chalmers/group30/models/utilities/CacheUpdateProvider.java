package com.chalmers.group30.models.utilities;

import java.util.List;

public interface CacheUpdateProvider<T> {
    List<T> getNewDataToCache();
}
