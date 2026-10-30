package com.jesus.almacen.exceptions;


/*
 * CONVERTIR ERRORES EN RESPUESTAS
 *
 * EXCEPCIONES PROPIAS
 *
 * @EXCEPTIONHANDLER
 * */
public class ConflictoException extends RuntimeException {
    public ConflictoException(String message) {
        super(message);
    }
}
