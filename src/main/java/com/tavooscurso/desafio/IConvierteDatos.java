package com.tavooscurso.desafio;

public interface IConvierteDatos {
    <T> T obtenerDaros(String json, Class<T> clase);
}
