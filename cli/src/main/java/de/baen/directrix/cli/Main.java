package de.baen.directrix.cli;

import de.baen.directrix.core.cache.Cache;

public class Main {
    public static void main(String[] args) {
        
        try {
            switch (args[0]) {
                case "a": 
                    System.out.println(Cache.TEST_ROOT_DIRECTORY);
                    break;

                case "b":
                    System.out.println(Cache.ROOT_DIRECTORY);
                    break;

                default:
                    System.out.println("Fehler: Einen der zwei gültigen Pfade (a,b) angeben!");

            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Fehler: Pfad muss als erstes Argument angegeben werden!");
            return;
        }

        
    }
}