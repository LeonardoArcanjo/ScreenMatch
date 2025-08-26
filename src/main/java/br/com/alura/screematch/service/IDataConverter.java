package br.com.alura.screematch.service;

public interface IDataConverter {
    <T> T GetData(String json, Class<T> classe);         // <T> eh o generics do Java indicando que o valor retornardo pode ser qualquer coisa
}
