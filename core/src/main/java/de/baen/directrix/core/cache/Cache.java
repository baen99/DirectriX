package de.baen.directrix.core.cache;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * Represents a current snapshot of the directory structure. 
 */
public class Cache {

    private Map<String, List<String>> cache;
    private CacheLoader cacheLoader = new CacheLoader();
    private CacheSaver cacheSaver = new CacheSaver();

    public void loadCache() {
        cache = cacheLoader.load();

        // kein Cache vorhanden, create new one
        if (cache == null) {
            cache = new HashMap<>(); // TODO: befüllen
        }
    }

    public void saveCache() {
        cacheSaver.save();
    }

    public void updateCache() {

    }

}
