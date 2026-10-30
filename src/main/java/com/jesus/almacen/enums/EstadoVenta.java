package com.jesus.almacen.enums;

import com.jesus.almacen.exceptions.DatoInvalidoException;
import com.jesus.almacen.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
/*
 * REPRESENTA UN CONJUNTO FIJO DE VALORES VÁLIDOS
 * PARA NO DEPENDER DE STRING SUELTOS
 *
 *
 * VALORES FIJOS
 *
 * UN METODO PARA CONVERTIR TEXTO -> ENUM
 *
 * DATOS PROPIOS DEL VALOR (NOMBRE PARA MOSTRAR)
 * */
@RequiredArgsConstructor
@Getter
public enum EstadoVenta {
    REGISTRADA(1L, "Registrada"),
    CANCELADA(0L, "Cancelada");

    private final Long codigo;

    private final String descripcion;

    public static EstadoVenta obtenerEstadoVentaPorDescripcion(String descripcion){

        StringCustomUtils.validarNoVacio(descripcion, "La descripción es requerida");

        String descripcionNormalizada = StringCustomUtils.normalizarTexto(descripcion);

        for (EstadoVenta estadoVenta : values()){
            if(StringCustomUtils.normalizarTexto(estadoVenta.descripcion).equalsIgnoreCase(descripcionNormalizada))
                return estadoVenta;
        }
        throw new DatoInvalidoException("No existe una categoria con la descripcion =" + descripcion);
    }

    public static EstadoVenta obtenerEstadoVentaPorCodigo(Long codigo){

        for (EstadoVenta estadoVenta : values()){
            if(estadoVenta.codigo.equals(codigo))
                return estadoVenta;
        }
        throw new DatoInvalidoException("No existe una categoria con la descripcion =" + codigo);
    }
}
