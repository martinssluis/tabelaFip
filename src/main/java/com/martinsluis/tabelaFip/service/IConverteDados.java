package com.martinsluis.tabelaFip.service;

import java.util.List;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> tClass);

    <T>List<T> obterListaDeDados(String json, Class<T> tClass);
}
