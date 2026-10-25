package de.baen.directrix.core.navigation;

import java.util.List;
import java.util.Map;

import de.baen.directrix.core.cache.CacheWrapper;
import de.baen.directrix.util.*;

/**
 * Encapsulates the navigation logic
 */
public class Navigator {

    private CacheWrapper cacheWrapper;

    public Navigator(CacheWrapper cacheWrapper) {
        this.cacheWrapper = cacheWrapper;
    }

    public String findPathToDirectory(String dirName) {
        Map<String, List<String>> cache = cacheWrapper.loadCache();
        List<String> targetPathList = cache.get(dirName);
        // Pfad nicht vorhanden
        if (targetPathList == null || targetPathList.isEmpty()) {
            throw new IllegalArgumentException(String.format("Keinen Pfad zu Ordner %s gefunden!", dirName));
        }
        // Ambiguität
        if (targetPathList.size() > 1) {
            String errorMessage = String.format("Pfad zu Ordner %s nicht eindeutig zuordenbar!", dirName);
            throw new AmbiguousPathException(errorMessage, targetPathList);
        }

        // Happy Case
        return targetPathList.getFirst();
        


    }



}
