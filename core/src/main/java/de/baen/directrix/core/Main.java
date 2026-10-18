package de.baen.directrix.core;

import java.io.IOException;
import java.nio.file.Path;

import de.baen.directrix.core.cache.Cache;

public class Main {

    public static void main(String[] args) {
        System.out.println("DirectriX has launched!");
        System.out.println(Cache.ROOT_DIRECTORY);

        try {
            System.out.println(Cache.fuegeChildrenRekursivListeHinzu(Path.of(Cache.TEST_ROOT_DIRECTORY)));
        }
        catch (IOException e) {
            
        }
        

        /*try {
            Cache.addChildrenToMap(Path.of("C:\\Coding Projekte\\Express cd"));
            Cache.addChildrenToMap(Path.of("C:\\Coding Projekte\\Express cd\\cli"));
            System.out.println(Cache.cache);
            
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }*/
        

    }
}