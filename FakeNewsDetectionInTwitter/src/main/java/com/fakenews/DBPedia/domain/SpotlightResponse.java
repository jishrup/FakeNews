package com.fakenews.DBPedia.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@Getter
@Setter
public class SpotlightResponse {
    private Annotation annotation;

}
