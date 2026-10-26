package de.baen.directrix.core.cache;

import java.nio.file.Path;
import java.util.Map;

import tools.jackson.databind.ObjectWriter;
import tools.jackson.databind.SerializationFeature;

/**
 * 
 * Class for saving the current state of the {@link CacheWrapper} to disk when the program is being terminated.
 */
class CacheSaver<T extends Map<?,?>> {

    private static final ObjectWriter WRITER = 
        CacheWrapper.MAPPER.writer().with(SerializationFeature.INDENT_OUTPUT);


    void save(Path file, T cache) {
        WRITER.writeValue(file.toFile(), cache);
    }

}
