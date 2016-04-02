package org.openhab.binding.wago.internal.modules;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FbModuleFactory {
    private Logger logger = LoggerFactory.getLogger(FbModuleFactory.class);

    private final HashMap<String, Integer> moduleCounter = new HashMap<String, Integer>();;

    private int getNextCount(String clazz) {
        Integer count = moduleCounter.get(clazz);
        if (count == null) {
            count = 0;
        }
        count++;
        moduleCounter.put(clazz, count);
        return count;
    }

    /**
     * Identify the WAGO I/O module using article number, moduletype and channels
     *
     * @param article
     * @param moduletype
     * @param channeltypes
     * @return a FbModule object that represents the WAGO I/O module
     */
    public FbModule getFbModule(String article, String moduletype, List<String> channeltypes) {
        if (article.equals("750-5xx")) {
            if (moduletype.equals("DO") && channeltypes.size() == 2) {
                return new Fb501DO2Ch(getNextCount(Fb501DO2Ch.class.getName()));
            } else if (moduletype.equals("DO/DIA") && channeltypes.size() == 4) {
                return new Fb523RO1Ch(getNextCount(Fb523RO1Ch.class.getName()));
            }
        }
        logger.warn("Unable to identify module: " + article + ", " + moduletype + ", " + channeltypes.size());
        return null;
    }
}
