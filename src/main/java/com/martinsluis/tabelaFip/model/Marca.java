package com.martinsluis.tabelaFip.model;


import com.martinsluis.tabelaFip.dto.MarcaDTO;

public class Marca  {
    private Integer codigo;
    private String nome;

    public Marca(Integer codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Marca(MarcaDTO marcaDTO){
        this.codigo = marcaDTO.codigo();
        this.nome = marcaDTO.nome();
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Marca{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                '}';
    }
}
