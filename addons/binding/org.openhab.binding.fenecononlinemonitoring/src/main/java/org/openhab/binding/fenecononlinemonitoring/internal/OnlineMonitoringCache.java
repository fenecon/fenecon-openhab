package org.openhab.binding.fenecononlinemonitoring.internal;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.openhab.binding.fenecononlinemonitoring.FeneconOnlineMonitoringBindingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnlineMonitoringCache {
    private Logger logger = LoggerFactory.getLogger(OnlineMonitoringCache.class);
    private DB db = null; // only use after calling getMapDB()
    BlockingQueue<TimedItemState<?>> cache = null; // only use after calling getMapDB()

    @SuppressWarnings("deprecation")
    private void getMapDB() throws Exception {
        if (cache == null) {
            File cacheDbFile = new File(FeneconOnlineMonitoringBindingConstants.CACHE_DB_PATH);
            try {
                logger.info("Opening cache database");
                if (db == null) {
                    db = DBMaker.fileDB(cacheDbFile).fileLockDisable().serializerRegisterClass(TimedItemState.class)
                            .closeOnJvmShutdown().make();
                }
                cache = db.getQueue("fems");
                logger.info("Opening cache database: finished");

            } catch (Exception e) {
                logger.error("Error opening cache database; delete and try again");
                OnlineMonitoringConnector
                        .offer(new TimedItemState<String>(FeneconOnlineMonitoringBindingConstants.FEMS_SYSTEMMESSAGE,
                                "ERROR opening cache database: " + e.getMessage()));
                e.printStackTrace();
                Files.delete(cacheDbFile.toPath());
                try {
                    if (db == null) {
                        db = DBMaker.fileDB(cacheDbFile).fileLockDisable().serializerRegisterClass(TimedItemState.class)
                                .closeOnJvmShutdown().make();
                    }
                    cache = db.getQueue("fems");
                } catch (Exception e1) {
                    OnlineMonitoringConnector.offer(
                            new TimedItemState<String>(FeneconOnlineMonitoringBindingConstants.FEMS_SYSTEMMESSAGE,
                                    "REPEATED ERROR opening cache database: " + e.getMessage()));
                    e.printStackTrace();
                    db = null;
                    throw e;
                }
            }
        }
    }

    public void dispose() {
        logger.info("Closing cache database");
        try {
            getMapDB();
            db.commit();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<TimedItemState<?>> pollMany(int count) throws Exception {
        ArrayList<TimedItemState<?>> returnList = new ArrayList<TimedItemState<?>>(count);
        getMapDB();
        for (int i = 0; i < count && !cache.isEmpty(); i++) {
            TimedItemState<?> tis = cache.poll();
            if (tis == null)
                break;
            returnList.add(tis);
        }
        db.commit();
        return returnList;
    }

    public void addAll(Collection<TimedItemState<?>> c) throws Exception {
        getMapDB();
        for (TimedItemState<?> tis : c) {
            cache.add(tis);
        }
        db.commit();
    }

    public String isEmpty() {
        try {
            getMapDB();
            if (cache.isEmpty()) {
                return "empty";
            } else {
                return "filled";
            }
        } catch (Exception e) {
            return "not available";
        }
    }
}
