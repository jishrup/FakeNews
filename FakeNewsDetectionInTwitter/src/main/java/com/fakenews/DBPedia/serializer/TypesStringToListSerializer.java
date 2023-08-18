package com.fakenews.DBPedia.serializer;


import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

public class TypesStringToListSerializer implements JsonSerializer<List<String>>,
        JsonDeserializer<List<String>> {

    @Override
    public JsonElement serialize(List<String> list, Type t,
                                 JsonSerializationContext jsc) {
        return jsc.serialize(Joiner.on(",").join(list));
    }
    @Override
    public List<String> deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext jsc)
            throws JsonParseException {
        return Splitter.on(",").trimResults().splitToList((String)jsc.deserialize(json, String.class));
    }
}