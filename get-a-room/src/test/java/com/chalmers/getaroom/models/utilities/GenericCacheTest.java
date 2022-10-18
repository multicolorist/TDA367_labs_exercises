package com.chalmers.getaroom.models.utilities;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GenericCacheTest {

    @Test
    void refreshCache() throws IOException {
        CacheUpdateProvider<List<Integer>> cacheUpdateProvider = mock(CacheUpdateProvider.class);

        when(cacheUpdateProvider.getNewDataToCache()).thenReturn(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));

        GenericCacheInterface<List<Integer>> genericCacheInterface = new GenericCache<List<Integer>>(cacheUpdateProvider);

        genericCacheInterface.refreshCache();

        List<Integer> result = genericCacheInterface.getData();

        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, result.toArray());

    }

    @Test
    void getLastCacheRefreshInstant() throws IOException {
        CacheUpdateProvider<List<Integer>> cacheUpdateProvider = mock(CacheUpdateProvider.class);

        when(cacheUpdateProvider.getNewDataToCache()).thenReturn(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));

        GenericCacheInterface<List<Integer>> genericCacheInterface = new GenericCache<List<Integer>>(cacheUpdateProvider);

        genericCacheInterface.refreshCache();

        assertNotNull(genericCacheInterface.getLastCacheRefreshInstant());
    }

    @Test
    void getData() throws IOException {
        CacheUpdateProvider<List<Integer>> cacheUpdateProvider = mock(CacheUpdateProvider.class);

        when(cacheUpdateProvider.getNewDataToCache()).thenReturn(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));

        GenericCacheInterface<List<Integer>> genericCacheInterface = new GenericCache<List<Integer>>(cacheUpdateProvider);

        List<Integer> result = genericCacheInterface.getData();

        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, result.toArray());
    }
}