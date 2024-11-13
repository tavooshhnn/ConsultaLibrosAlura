package com.tavooscurso.desafio.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record datos(@JsonAlias("results") List<DatosLibros>resultados) {
}

