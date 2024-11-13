package com.tavooscurso.desafio.principal;

import com.tavooscurso.desafio.ConsumoAPI;
import com.tavooscurso.desafio.ConvierteDatos;
import com.tavooscurso.desafio.Model.datos;
import com.tavooscurso.desafio.Model.DatosLibros;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner tipiado = new Scanner(System.in);



    public void muestraElMenu(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDaros(json, com.tavooscurso.desafio.Model.datos.class);
        System.out.println(datos );

        // TOP 10  DE LOS LIBROS MAS DESCARGADOS.
        System.out.println("Top 10 de los libros más descargados");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l ->l.titulo().toUpperCase())
                .forEach(System.out::println);

        //BUSQUEDA DE LIBROS POR NOMBRES.
        System.out.println("Ingresa el nombre del libro que deseas buscar");
        var tituloLibro = tipiado.nextLine();
        json = consumoAPI.obtenerDatos(URL_BASE+"?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDaros(json, com.tavooscurso.desafio.Model.datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase())).findFirst();
        if (libroBuscado.isPresent()) {
            System.out.println("Libro emcpmtrado :) ");
            System.out.println(libroBuscado.get());
        }else {
            System.out.println("Libro no encontrado :,( ");
        }

        //TRABAJANDO CON LAS ESTADISTICAS

        DoubleSummaryStatistics est = datos.resultados().stream().filter(d -> d.numeroDeDescargas() > 0)
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));
        System.out.println("************************************************************************************");
        System.out.println("*Cantidad media de descargas: " + est.getAverage());
        System.out.println("*Cantida maxima de descargas " + est.getMax());
        System.out.println("*Cantidad minima de descargas " + est.getMin());
        System.out.println("*Cantidad de registros evaluados para calcular la estadistica: " +est.getCount());
        System.out.println("************************************************************************************");
    }





}
