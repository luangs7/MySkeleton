package br.com.squarebits.myskeleton.extras;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Luan on 18/12/17.
 */

public class CollectionEmptySerializer implements JsonSerializer<List<?>> {
    @Override
    public JsonElement serialize(List<?> src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null || src.isEmpty()) // exclusion is made here
            return null;

        JsonArray array = new JsonArray();

        for (Object child : src) {
            JsonElement element = context.serialize(child);
            array.add(element);
        }

        return array;
    }
}

