package de.baen.directrix.cli;

import java.util.List;

import de.baen.directrix.core.cache.CacheWrapper;
import de.baen.directrix.core.navigation.Navigator;
import de.baen.directrix.util.AmbiguousPathException;

public class Main {
    /**
     * Printing the target directory to stdout such that the Powershell process can capture it.
     * @param args Array containing one entry: the name of the target directory.
     */
    public static void main(String[] args) {

        CacheWrapper cacheWrapper = new CacheWrapper();
        Navigator navigator = new Navigator(cacheWrapper);
        
        try {
            System.out.println(navigator.findPathToDirectory(args[0]));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Fehler: Pfad muss als erstes Argument angegeben werden!");
            return;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.out.println("Hello from stdout!");
            System.exit(1);
        } catch (AmbiguousPathException e) {
            System.err.println(e.getMessage());
            List<String> paths = e.getPaths();
            paths.forEach(System.err::println);
            //for (int i=0; i<paths.size(); i++) {
              //  System.err.println(String.format("%d) %s", i, paths.get(i)));
            //}
            System.exit(1);
        }

        
    }
}