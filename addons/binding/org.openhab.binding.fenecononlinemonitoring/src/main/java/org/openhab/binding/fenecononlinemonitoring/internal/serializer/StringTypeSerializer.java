package org.openhab.binding.fenecononlinemonitoring.internal.serializer;

import java.lang.reflect.Type;

import org.eclipse.smarthome.core.library.types.StringType;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class StringTypeSerializer implements JsonSerializer<StringType> {

    @Override
    public JsonElement serialize(StringType src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString());
    }

}
