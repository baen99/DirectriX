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
public class CacheWrapper {

    public Map<String, List<String>> cache;
    private CacheLoader cacheLoader = new CacheLoader();
    private CacheSaver cacheSaver = new CacheSaver();
    public static final String ROOT_DIRECTORY = "C:\\";
    public static final String TEST_ROOT_DIRECTORY = "C:\\Coding Projekte\\Express cd\\Playground\\A";

    public String getString() {
        return "Yoo wtf";
    }

    

    /**
     * Mappt rekursiv jeden Unterordner (auch nested) von dir auf seinen Pfad. Geht die komplette Baumstruktur
     * entlang, bis kein Unterordner mehr vorhanden ist.
     * @param dir Ordner, von dem aus alle Unterordner aufgelistet werden.
     * @return Map mit Ordnernamen und deren absoluten Pfaden.
     */
    public Map<String, List<String>> addChildrenToMapRecursively(Path dir) {
        try (Stream<Path> children = Files.list(dir)) {

            List<Path> childrenList = children.filter(Files::isDirectory).toList();

            MapEntry currentDirAsEntry = new MapEntry( dir.getFileName().toString(), new ArrayList<>( List.of( dir.toString() ) ) );
            
            // Abbruchbedingung
            if (childrenList.isEmpty()) { 
                return Map.of(currentDirAsEntry.key(), currentDirAsEntry.value() );
            }

            Map<String, List<String>> directoryMap = new HashMap<>();
            directoryMap.put(currentDirAsEntry.key(), currentDirAsEntry.value());
            childrenList
                .forEach(subdir -> {
                    Map<String, List<String>> childrenMap = addChildrenToMapRecursively(subdir); // Rekursionsschritt
                    addChildrenToCurrentDirectoryMap(directoryMap, childrenMap);
                });
            
            return directoryMap;
        }
        catch (IOException e) { 
            System.err.println(e.getMessage()); 
            return null;
        }
           
    }

    public Map<String, List<String>> loadCache() {
        cache = cacheLoader.load();

        // kein Cache vorhanden, create new one
        if (cache == null || cache.isEmpty()) {
            cache = new HashMap<>();
            cache = addChildrenToMapRecursively(Path.of(TEST_ROOT_DIRECTORY));
        }

        return cache;
    }


    public void saveCache() {
        cacheSaver.save();
    }


    public void updateCache() {

    }


    record MapEntry(String key, List<String> value) {}






    /**
     * Fügt eine Liste von Unterordnern mit deren Pfaden der bestehenden Gesamtmap hinzu.
     * @param directoryMap
     * @param childrenMap
     */
    private void addChildrenToCurrentDirectoryMap(Map<String, List<String>> directoryMap, Map<String, List<String>> childrenMap) {
        childrenMap
            .entrySet()
            .forEach(entry -> {
                String subdirName = entry.getKey();
                List<String> pathsToSubdir = directoryMap.getOrDefault(subdirName , new ArrayList<>());
                pathsToSubdir.addAll(entry.getValue());
                directoryMap.put(subdirName, pathsToSubdir);
            });
    }



    /**
     * Methode, um das rekursive durchschreiten des Ordner-Baumes zu testen.
     * @param dir
     * @return
     * @throws IOException
     */
    @Deprecated 
    public List<String> fuegeChildrenRekursivListeHinzu(Path dir) throws IOException {
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

    @Deprecated 
    private void addChildrenToMap(Path dir) throws IOException {   
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

}
