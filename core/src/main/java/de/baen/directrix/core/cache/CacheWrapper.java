package de.baen.directrix.core.cache;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import tools.jackson.databind.ObjectMapper;

/**
 * 
 * Represents a current snapshot of the directory structure. 
 */
public class CacheWrapper {

    private Map<String, List<String>> cache;
    private CacheLoader<Map<String, List<String>>> cacheLoader = new CacheLoader<>();
    private CacheSaver<Map<String, List<String>>> cacheSaver = new CacheSaver<>();
    public static final String ROOT_DIRECTORY = "C:\\";
    public static final String TEST_ROOT_DIRECTORY = "C:\\Coding Projekte\\Express cd\\Playground\\A";
    /**
     * Nur bestimmte, hardcodierte Ordner unter C: erlauben,
     * da spezielle Windows-Ordner Probleme beim Scannen machen.
     */
    private static final Path[] ALLOWED_FOLDERS_UNDER_C = {
        Path.of("C:\\Users"),
        Path.of("C:\\Coding Projekte"),
        Path.of("C:\\Program Files"),
        Path.of("C:\\Program Files (x86)"),
        Path.of("C:\\Python312")
    };
    private static final String PERSISTED_CACHE_MAP = "C:\\Coding Projekte\\Express cd\\core\\src\\main\\resources\\cache.json";
    static final ObjectMapper MAPPER = new ObjectMapper();

    

    /**
     * Mappt rekursiv jeden Unterordner (auch nested) von dir auf seinen Pfad. Geht die komplette Baumstruktur
     * entlang, bis kein Unterordner mehr vorhanden ist.
     * @param dir Ordner, von dem aus alle Unterordner aufgelistet werden.
     * @return Map mit Ordnernamen und deren absoluten Pfaden.
     */
    public Map<String, List<String>> addChildrenToMapRecursively(Path dir) {
        try (Stream<Path> children = Files.list(dir)) {

            List<Path> childrenList;
            if (dir.equals(Path.of(ROOT_DIRECTORY))) {
                // Sonderfall am Anfang: filtere nur bestimmte Ordner unter C: heraus
                childrenList = List.of(ALLOWED_FOLDERS_UNDER_C);
            } else {
                childrenList = children
                .filter(Files::isDirectory)
                .filter(CacheWrapper::isVisible)
                .toList();
            }
            

            Path fileName = dir.getFileName();
            String directoryName = fileName == null ? dir.toString() : fileName.toString(); // für C: ist filename null
            MapEntry currentDirAsEntry = new MapEntry( directoryName, new ArrayList<>( List.of( dir.toString() ) ) );
            
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
            System.err.println("Ordner " + dir + " konnte nicht gescannt werden: " + e.getMessage());
            return Map.of();
        }
           
    }

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

    private static boolean isVisible(Path dir) {
        try {
            return !Files.isHidden(dir);
        } catch (IOException e) {
            return false;
        }
    }

    public Map<String, List<String>> loadCache() {
        cache = cacheLoader.load(Path.of(PERSISTED_CACHE_MAP));

        // kein Cache vorhanden, create new one
        if (cache == null || cache.isEmpty()) {
            cache = addChildrenToMapRecursively(Path.of(ROOT_DIRECTORY));
            //System.out.println("Cache neu eingelesen!");
        }

        return cache;
    }


    public void saveCache() {
        cacheSaver.save(Path.of(PERSISTED_CACHE_MAP), cache);
    }


    public void updateCache() {

    }


    record MapEntry(String key, List<String> value) {}





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
