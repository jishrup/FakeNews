package com.fakenews.DBPedia.domain;

import com.fakenews.DBPedia.serializer.TypesStringToListSerializer;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@ToString
public class Resource {
    @SerializedName("@label")
    private String label;
    @SerializedName("@uri")
    private String uri;
    @SerializedName("@contextualScore")
    private BigDecimal contextualScore;
    @SerializedName("@percentageOfSecondRank")
    private BigDecimal percentageOfSecondRank;
    @SerializedName("@support")
    private Integer support;
    @SerializedName("@priorScore")
    private BigDecimal priorScore;
    @SerializedName("@finalScore")
    private BigDecimal finalScore;
    @SerializedName("@types")
    @JsonAdapter(TypesStringToListSerializer.class)
    private List<String> types;
}
