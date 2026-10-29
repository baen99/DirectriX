package de.baen.directrix.core;

import java.util.List;
import java.util.Map;

import de.baen.directrix.core.cache.CacheWrapper;

public class Main {

    public static void main(String[] args) {
        CacheWrapper cacheWrapper = new CacheWrapper();
        Map<String, List<String>> cache = cacheWrapper.loadCache();
        //System.out.println(cache);
        cacheWrapper.saveCache();
    }

}
