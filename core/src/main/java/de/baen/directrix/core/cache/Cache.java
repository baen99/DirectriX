package de.baen.directrix.core.cache;

import java.util.Map;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 
 * Represents a current snapshot of the directory structure. 
 */
public class Cache {

    public static Map<String, List<String>> cache = new HashMap<>();
    private static CacheLoader cacheLoader = new CacheLoader();
    private static CacheSaver cacheSaver = new CacheSaver();
    public static final String ROOT_DIRECTORY = "C:\\";
    public static final String TEST_ROOT_DIRECTORY = "C:\\Coding Projekte\\Express cd\\Playground\\A";


    public static void loadCache() {
        cache = cacheLoader.load();

        // kein Cache vorhanden, create new one
        if (cache == null) {
            cache = new HashMap<>(); // TODO: befüllen
        }
    }

    public static void addChildrenToMap(Path dir) throws IOException {   
        Files.list(dir)
            .filter(Files::isDirectory)
            //.map(Path::getFileName)
            //.map(Path::toString)
            .forEach(subdir -> {
                String subdirName = subdir.getFileName().toString(); 
                List<String> pathsToSubdir = cache.getOrDefault(subdirName, new ArrayList<>());
                pathsToSubdir.add(subdir.toString());
                cache.put(subdirName,pathsToSubdir);
            });  
    }


    public static void saveCache() {
        cacheSaver.save();
    }

    public static void updateCache() {

    }


    /**
     * Methode, um das rekursive durchschreiten des Ordner-Baumes zu testen.
     * @param dir
     * @return
     * @throws IOException
     */
    @Deprecated 
    public static List<String> fuegeChildrenRekursivListeHinzu(Path dir) throws IOException {
        try (Stream<Path> children = Files.list(dir).filter(Files::isDirectory)) {
            
            // Abbruchbedingung
            if (children.findAny().isEmpty()) { // lookup consumes stream
                return List.of(dir.getFileName().toString());
            }

            Stream<Path> childrenReopenedStream = Files.list(dir).filter(Files::isDirectory);
            List<String> l = new ArrayList<>();
            l.add(dir.getFileName().toString());
            childrenReopenedStream
                .forEach(subdir -> {
                    try {
                        l.addAll(fuegeChildrenRekursivListeHinzu(subdir)); // Rekursionsschritt
                    }
                    catch (IOException e) {}
                    
                });
            
            return l;


        }
        
        
    }

}
