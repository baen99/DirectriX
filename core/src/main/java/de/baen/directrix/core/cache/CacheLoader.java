package de.baen.directrix.core.cache;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * Class for loading the persisted {@link Cache} into memory when the program is initialized.
 */
public class CacheLoader {

    /**
     * @TODO implement me!
     * @return loaded cache, null if none found.
     */
    protected Map<String, List<String>> load() {
        return new HashMap<>();
    }

}
