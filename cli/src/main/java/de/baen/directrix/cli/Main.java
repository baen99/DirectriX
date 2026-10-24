package de.baen.directrix.cli;

import de.baen.directrix.core.cache.Cache;

public class Main {
    public static void main(String[] args) {
        /* 
        System.out.println("DirectriX has launched!");
        Arrays.stream(args).forEach(System.out::println);

        Cache cache = new Cache();
        System.out.println(cache.getString());
        */

        System.out.println(Cache.TEST_ROOT_DIRECTORY);
        
    }
}