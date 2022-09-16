package com.chalmers.group30.models.utilities;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenericCacheTest {

    @Test
    void refreshCache() {
        CacheUpdateProvider<Integer> cacheUpdateProvider = mock(CacheUpdateProvider.class);

        when(cacheUpdateProvider.getNewDataToCache()).thenReturn(Arrays.asList(new Integer[]{1,2,3,4,5}));

        GenericCacheInterface<Integer> genericCacheInterface = new GenericCache<Integer>(cacheUpdateProvider);

        genericCacheInterface.RefreshCache();

        List<Integer> result = genericCacheInterface.getData();

        assertArrayEquals(new Integer[]{1,2,3,4,5}, result.toArray());

    }

    @Test
    void getLastCacheRefreshInstant() {
        CacheUpdateProvider<Integer> cacheUpdateProvider = mock(CacheUpdateProvider.class);

        when(cacheUpdateProvider.getNewDataToCache()).thenReturn(Arrays.asList(new Integer[]{1,2,3,4,5}));

        GenericCacheInterface<Integer> genericCacheInterface = new GenericCache<Integer>(cacheUpdateProvider);

        genericCacheInterface.RefreshCache();

        assertNotNull(genericCacheInterface.GetLastCacheRefreshInstant());
    }

    @Test
    void getData() {
        CacheUpdateProvider<Integer> cacheUpdateProvider = mock(CacheUpdateProvider.class);

        when(cacheUpdateProvider.getNewDataToCache()).thenReturn(Arrays.asList(new Integer[]{1,2,3,4,5}));

        GenericCacheInterface<Integer> genericCacheInterface = new GenericCache<Integer>(cacheUpdateProvider);

        List<Integer> result = genericCacheInterface.getData();

        assertArrayEquals(new Integer[]{1,2,3,4,5}, result.toArray());
    }
}