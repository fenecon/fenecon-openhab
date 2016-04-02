package org.openhab.binding.fenecononlinemonitoring.internal;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.openhab.binding.fenecononlinemonitoring.FeneconOnlineMonitoringBindingConstants;
import org.openhab.binding.fenecononlinemonitoring.internal.tools.FemsYaler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonStreamParser;

@SuppressWarnings("restriction")
public class OnlineMonitoringConnector implements Runnable {
    private Logger logger = LoggerFactory.getLogger(OnlineMonitoringConnector.class);
    private final String apikey;
    private final OnlineMonitoringCache cache;
    private static ConcurrentLinkedQueue<TimedItemState<?>> queue = new ConcurrentLinkedQueue<TimedItemState<?>>();

    public OnlineMonitoringConnector(String apikey) {
        this.apikey = apikey;
        cache = new OnlineMonitoringCache();
    }

    @Override
    public void run() {
        try {
            if (queue.isEmpty()) {
                logger.info("FENECON Online Monitoring: No new data to send");
                return;
            }

            String statsBefore = "Queue:" + queue.size() + "; Cache:" + cache.isEmpty();
            // Get entries from current queue
            ArrayList<TimedItemState<?>> currentQueue = new ArrayList<TimedItemState<?>>(queue.size());
            for (int i = 0; i < FeneconOnlineMonitoringBindingConstants.MAX_CACHE_ENTRIES_TO_TRANSFER; i++) {
                TimedItemState<?> tis = queue.poll();
                if (tis == null)
                    break;
                currentQueue.add(tis);
            }
            // send request
            JsonObject resultObj = sendToOnlineMonitoring(currentQueue);
            if (resultObj != null) { // sending was successful
                handleJsonRpcResult(resultObj);
                currentQueue.clear(); // clear currentQueue
                // transfer from cache
                try {
                    currentQueue.addAll(
                            cache.pollMany(FeneconOnlineMonitoringBindingConstants.MAX_CACHE_ENTRIES_TO_TRANSFER));
                } catch (Exception e1) {
                    logger.error("Error while receiving from cache: " + e1.getMessage());
                    e1.printStackTrace();
                }
                if (!currentQueue.isEmpty()) { // if there are elements in the list
                    logger.info("Send from cache: " + currentQueue.size());
                    resultObj = sendToOnlineMonitoring(currentQueue);
                    if (resultObj != null) { // sending was successful
                        currentQueue.clear();
                    } else { // sending was not successful
                        try {
                            cache.addAll(currentQueue);
                        } catch (Exception e) {
                            logger.error("Error while adding to cache: " + e.getMessage());
                            e.printStackTrace();
                            queue.addAll(currentQueue);
                        }
                    }
                }
            } else { // sending was not successful;
                try {
                    cache.addAll(currentQueue);
                } catch (Exception e) {
                    logger.error("Error while adding to cache");
                    e.printStackTrace();
                    queue.addAll(currentQueue);
                }
            }
            logger.info("  Before[" + statsBefore + "]; Now[" + "Queue:" + queue.size() + "; Cache:" + cache.isEmpty()
                    + "]");
        } catch (Throwable t) {
            logger.error("Error in FENECON Online-Monitoring: " + t.getMessage());
            t.printStackTrace();
        }
    }

    private JsonObject sendToOnlineMonitoring(ArrayList<TimedItemState<?>> queue) {
        JsonObject resultObj = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String json = tisListToJson(queue);
            HttpPost post = new HttpPost(FeneconOnlineMonitoringBindingConstants.ONLINE_MONITORING_URL);
            post.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
            HttpResponse response = client.execute(post);

            JsonStreamParser stream = new JsonStreamParser(new InputStreamReader(response.getEntity().getContent()));
            while (stream.hasNext()) {
                JsonElement mainElement = stream.next();
                if (mainElement.isJsonObject()) {
                    JsonObject mainObj = mainElement.getAsJsonObject();
                    // read result
                    JsonElement resultElement = mainObj.get("result");
                    if (resultElement != null) {
                        if (resultElement.isJsonObject()) {
                            resultObj = resultElement.getAsJsonObject();
                        }
                    }
                    // read error
                    JsonElement errorElement = mainObj.get("error");
                    if (errorElement != null) {
                        throw new IOException(errorElement.toString());
                    }
                }
            }
            if (resultObj == null) {
                resultObj = new JsonObject();
            }
            logger.info("Successfully sent data");
        } catch (IOException | JsonParseException e) {
            logger.error("Send error: " + e.getMessage());
        }
        return resultObj;
    }

    public static void offer(TimedItemState<?> timedItemState) {
        queue.offer(timedItemState);
    }

    private String tisListToJson(List<TimedItemState<?>> queue) {
        HashMap<String, HashMap<Long, Object>> statesPerItem = new HashMap<String, HashMap<Long, Object>>();
        for (TimedItemState<?> entry : queue) {
            HashMap<Long, Object> states = statesPerItem.get(entry.getItemName());
            if (states == null) {
                states = new HashMap<Long, Object>();
                statesPerItem.put(entry.getItemName(), states);
            }
            states.put(entry.getTime(), entry.getState());
        }
        Gson gson = new GsonBuilder().create();
        JsonElement jsonStates = gson.toJsonTree(statesPerItem);

        // create json rpc
        JsonObject json = new JsonObject();
        json.addProperty("jsonrpc", "2.0");
        json.addProperty("method", apikey);
        json.addProperty("id", 1);
        json.add("params", jsonStates);
        return gson.toJson(json);

    }

    private void handleJsonRpcResult(JsonObject resultObj) {
        JsonElement yalerElement = resultObj.get("yaler");
        if (yalerElement != null) {
            String yalerRelayDomain = yalerElement.getAsString();
            try {
                if (yalerRelayDomain.equals("false")) {
                    FemsYaler.getFemsYaler().deactivateTunnel();
                } else {
                    FemsYaler.getFemsYaler().activateTunnel(yalerRelayDomain);
                }
            } catch (Exception e) {
                logger.error("Error while activating/deactivating yaler: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void dispose() {
        cache.dispose();
    }

}
