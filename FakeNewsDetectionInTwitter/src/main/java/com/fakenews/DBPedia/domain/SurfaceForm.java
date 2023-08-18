package com.fakenews.DBPedia.domain;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class SurfaceForm {
    @SerializedName("@name")
    private String name;
    private Resource resource;
}
