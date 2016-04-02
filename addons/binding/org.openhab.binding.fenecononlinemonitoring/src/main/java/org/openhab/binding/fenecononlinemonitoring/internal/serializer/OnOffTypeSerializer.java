package org.openhab.binding.fenecononlinemonitoring.internal.serializer;

import java.lang.reflect.Type;

import org.eclipse.smarthome.core.library.types.OnOffType;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class OnOffTypeSerializer implements JsonSerializer<OnOffType> {

    @Override
    public JsonElement serialize(OnOffType src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == OnOffType.OFF) {
            return new JsonPrimitive(0);
        } else {
            return new JsonPrimitive(1);
        }
    }

}
