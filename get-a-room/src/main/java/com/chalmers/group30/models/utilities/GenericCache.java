package com.chalmers.group30.models.utilities;

import java.io.*;
import java.time.Instant;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GenericCache<T> implements GenericCacheInterface, Serializable {

    private T cache = null;
    private transient CacheUpdateProvider<T> updateProvider;
    private Instant lastRefresh = null;
    private transient Lock cacheRefreshInProgressLock = new ReentrantLock();
    private boolean cacheRefreshSucceeded = false;

    /**
     * Create a new instance of GenericCache
     * @param updateProvider Provider used to update cache data
     */
    public GenericCache(CacheUpdateProvider<T> updateProvider){

        this.updateProvider = updateProvider;
        FileInputStream fileIn = null;
        ObjectInputStream ois = null;
        try {
            fileIn = new FileInputStream(updateProvider.toString().split("@", 2)[0] + ".cache");
            ois = new ObjectInputStream(fileIn);
            GenericCache<T> oldCache = (GenericCache<T>) ois.readObject();
            this.cache = oldCache.cache;
            this.lastRefresh = oldCache.lastRefresh;
            this.cacheRefreshSucceeded = oldCache.cacheRefreshSucceeded;
        }catch (Exception e){

        }finally {
            try {
                if (ois != null) {
                    ois.close();
                }

            } catch (Exception e) {

            }
            try {
                if (fileIn != null) {
                    fileIn.close();
                }
            } catch (Exception e) {

            }
        }
    }

    /**
     * Causes a refresh of the cached data. Thread safe.
     */
    public void refreshCache() throws IOException {
        if (cacheRefreshInProgressLock.tryLock()){
            try{
                cacheRefreshSucceeded = false;
                cache = updateProvider.getNewDataToCache();
                lastRefresh = Instant.now();
                cacheRefreshSucceeded = true;

                saveCacheToFile();

            }finally {
                cacheRefreshInProgressLock.unlock();
            }
        }else {
            //Cache update already in progress on another thread. Wait for it to complete
            cacheRefreshInProgressLock.lock();
            cacheRefreshInProgressLock.unlock();
            if (!cacheRefreshSucceeded){
                //Cache update appears to have failed. Trying again
                refreshCache();
            }
        }
    }

    private void saveCacheToFile() {
        FileOutputStream fileOut = null;
        ObjectOutputStream oos = null;
        try {
            fileOut = new FileOutputStream(updateProvider.toString().split("@", 2)[0] + ".cache", false);
            oos = new ObjectOutputStream(fileOut);
            oos.writeObject(this);
        } catch (IOException e) {

        }finally {
            try {
                if (oos != null) {
                    oos.close();
                }

            } catch (Exception e) {

            }
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (Exception e) {

            }
        }
    }

    /**
     * Gets the last instant the cache was refreshed
     * @return The last refresh instant
     */
    public Instant getLastCacheRefreshInstant(){
        return lastRefresh;
    }

    /**
     * Get the data from the cache. If cache is empty, will cause a cache refresh.
     * @return The cached data
     */
    public T getData() throws IOException {
        if (cache == null){
            refreshCache();
        }

        return cache;
    }



}
