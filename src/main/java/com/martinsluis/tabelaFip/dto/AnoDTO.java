package com.martinsluis.tabelaFip.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AnoDTO(
        @JsonAlias("codigo") String codigo
) {}
