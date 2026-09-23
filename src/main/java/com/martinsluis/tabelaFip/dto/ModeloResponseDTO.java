package com.martinsluis.tabelaFip.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ModeloResponseDTO(
        @JsonAlias("modelos") List<ModeloDTO> modelos
) {}
