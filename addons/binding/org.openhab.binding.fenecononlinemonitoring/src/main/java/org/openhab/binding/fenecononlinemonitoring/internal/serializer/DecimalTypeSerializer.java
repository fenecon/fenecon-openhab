package org.openhab.binding.fenecononlinemonitoring.internal.serializer;

import java.lang.reflect.Type;

import org.eclipse.smarthome.core.library.types.DecimalType;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DecimalTypeSerializer implements JsonSerializer<DecimalType> {

    @Override
    public JsonElement serialize(DecimalType src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toBigDecimal());
    }

}
