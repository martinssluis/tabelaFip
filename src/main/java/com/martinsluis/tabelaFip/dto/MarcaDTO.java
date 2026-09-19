package com.martinsluis.tabelaFip.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MarcaDTO(
        @JsonAlias("codigo") Integer codigo,
        @JsonAlias("nome") String nome
) {}
