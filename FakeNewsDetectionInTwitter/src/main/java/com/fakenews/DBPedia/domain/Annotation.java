package com.fakenews.DBPedia.domain;

import com.fakenews.DBPedia.serializer.SingleToArrayTypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Annotation {
    @SerializedName("@text")
    private String text;
    @JsonAdapter(SingleToArrayTypeAdapterFactory.class)
    private List<SurfaceForm> surfaceForm;
}
