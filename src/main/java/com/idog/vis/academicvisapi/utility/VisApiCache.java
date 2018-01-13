/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.collections.LRUMap;

/**
 *
 * @author idoga
 */
public class VisApiCache<K, T> {

    private long timeToLive;
    private Map cache;

    protected class VisApiCacheObject {

        public long lastAccessed = System.currentTimeMillis();
        public T value;

        protected VisApiCacheObject(T value) {
            this.value = value;
        }
    }

    public VisApiCache(long timeToLive, final long timerInterval, int maxItems) {
        this.timeToLive = timeToLive * 1000;
        this.cache = new LRUMap(maxItems);

        if (timeToLive > 0 && timerInterval > 0) {

            Runnable cleanupJob = new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(timerInterval * 1000);
                        } catch (InterruptedException ex) {
                        }
                        
                        cleanup();
                    }
                }
            };

            Thread cleanerThread = new Thread(cleanupJob);

            cleanerThread.setName("CacheCleaner");
            cleanerThread.setDaemon(true);
            cleanerThread.start();
        }
    }

    public void put(K key, T value) {
        synchronized (cache) {
            cache.put(key, new VisApiCacheObject(value));
        }
    }

    @SuppressWarnings("unchecked")
    public T get(K key) {
        synchronized (cache) {
            VisApiCacheObject c = (VisApiCacheObject) cache.get(key);

            if (c == null) {
                return null;
            } else {
                c.lastAccessed = System.currentTimeMillis();
                return c.value;
            }
        }
    }

    public void remove(K key) {
        synchronized (cache) {
            cache.remove(key);
        }
    }

    public int size() {
        synchronized (cache) {
            return cache.size();
        }
    }

    @SuppressWarnings("unchecked")
    public void cleanup() {

        long now = System.currentTimeMillis();
        ArrayList<K> deleteKey = null;

        synchronized (cache) {
            Iterator iterator = cache.keySet().iterator();

            deleteKey = new ArrayList<K>((cache.size() / 2) + 1);
            K key = null;
            VisApiCacheObject c = null;

            while (iterator.hasNext()) {
                key = (K) iterator.next();
                c = (VisApiCacheObject) iterator.next();

                if (c != null && (now > (timeToLive + c.lastAccessed))) {
                    deleteKey.add(key);
                }
            }
        }

        for (K key : deleteKey) {
            synchronized (cache) {
                cache.remove(key);
            }

            Thread.yield();
        }
    }
}
