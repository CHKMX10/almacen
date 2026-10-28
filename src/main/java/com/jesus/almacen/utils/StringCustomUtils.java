package com.jesus.almacen.utils;

import com.jesus.almacen.exceptions.DatoInvalidoException;

import java.util.Locale;

public class StringCustomUtils {

    public static void validarNoVacio(String texto, String mensaje){
        if(texto == null || texto.isBlank())
            throw new DatoInvalidoException(mensaje);//esta es una exception, que nosotros mismos creamos
    }

    public static void validarTamanio(String texto, Integer min, Integer max, String mensaje){
        validarNoVacio(texto , mensaje);
        if(texto.length() < min || texto.length() > max)
            throw new DatoInvalidoException(mensaje);//esta es una exception, que nosotros mismos creamos
    }

    public static String normalizarTexto(String texto){
     return texto.toLowerCase()
             .replace("á","a").replace("é","e")
             .replace("í","i").replace("ó","o")
             .replace("ú","u").replace("ü","u")
             .replace("ñ","n");
    }

}
