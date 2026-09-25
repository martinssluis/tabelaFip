package com.martinsluis.tabelaFip.exception;

public class ValorNaoEncontradoOuNuloException extends RuntimeException {

    public ValorNaoEncontradoOuNuloException(){
        super("Valor nulo ou não encontrado");
    }
    public ValorNaoEncontradoOuNuloException(String message) {
        super(message);
    }
}
