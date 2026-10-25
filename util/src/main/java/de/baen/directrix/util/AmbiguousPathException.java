package de.baen.directrix.util;

import java.util.List;

public class AmbiguousPathException extends AbstractDirectrixException {

    private List<String> paths;

    public AmbiguousPathException(String message, List<String> paths) {
        super(message);
        this.paths = paths;
    }

    public List<String> getPaths() {
        return paths;
    } 

}
