package com.chalmers.group30.models.utilities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class GenericCache<T> {

    private List<T> cache = null;
    private CacheUpdateProvider<T> updateProvider;

    private Instant lastRefresh = null;

    public GenericCache(CacheUpdateProvider<T> updateProvider){
        this.updateProvider = updateProvider;
    }

    public void RefreshCache(){
        cache = updateProvider.getNewDataToCache();
        lastRefresh = Instant.now();
    }

    public Instant GetLastCacheRefreshInstant(){
        return lastRefresh;
    }

    public List<T> getData(){
        if (cache == null){
            RefreshCache();
        }

        return cache;
    }



}
