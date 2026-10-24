package de.baen.directrix.cli;

import java.util.Arrays;

import de.baen.directrix.core.cache.Cache;

public class Main {
    public static void main(String[] args) {
        System.out.println("DirectriX has launched!");
        Arrays.stream(args).forEach(System.out::println);

        System.out.println(Cache.TEST_ROOT_DIRECTORY);
        Cache cache = new Cache();
        System.out.println(cache.getString());
    }
}