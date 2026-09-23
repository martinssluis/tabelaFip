package com.martinsluis.tabelaFip.model;

import com.martinsluis.tabelaFip.dto.ModeloDTO;

public class Modelo {
    private Integer codigo;
    private String descricao;

    public Modelo(Integer codigo, String nome) {
        this.codigo = codigo;
        this.descricao = nome;
    }

    public Modelo(ModeloDTO modeloDTO){
        this.codigo = modeloDTO.codigo();
        this.descricao = modeloDTO.descricao();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return "Modelo{" +
                "codigo=" + codigo +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
