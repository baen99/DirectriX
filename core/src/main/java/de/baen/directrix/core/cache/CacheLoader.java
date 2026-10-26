package de.baen.directrix.core.cache;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import tools.jackson.core.type.TypeReference;

/**
 * Class for loading the persisted {@link CacheWrapper} into memory when the program is initialized.
 */
class CacheLoader<T extends Map<?,?>> {

    /**
     * @return loaded cache, null if none found.
     */
    T load(Path file) {
        if (Files.notExists(file)) {
            return null;
        }
        
        return CacheWrapper.MAPPER.readValue(file.toFile(), new TypeReference<T>() {});
        
    }

}
