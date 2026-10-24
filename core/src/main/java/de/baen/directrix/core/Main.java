package de.baen.directrix.core;

import java.nio.file.Path;

import de.baen.directrix.core.cache.Cache;

public class Main {

    public static void main(String[] args) {
        System.out.println("DirectriX has launched!");
        System.out.println(Cache.ROOT_DIRECTORY);

        System.out.println(Cache.addChildrenToMapRecursively(Path.of(Cache.TEST_ROOT_DIRECTORY)));
        
        

    }
}