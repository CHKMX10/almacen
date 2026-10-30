package com.jesus.almacen.exceptions;


/*
 * CONVERTIR ERRORES EN RESPUESTAS
 *
 * EXCEPCIONES PROPIAS
 *
 * @EXCEPTIONHANDLER
 * */
public class DatoInvalidoException extends RuntimeException {
    public DatoInvalidoException(String message) {
        super(message);
    }
}
