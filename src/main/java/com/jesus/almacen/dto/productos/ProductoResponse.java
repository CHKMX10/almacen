package com.jesus.almacen.dto.productos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/*
 * DATA TRANSFER OBJECT
 * DEFINE QUE DATOS ENTRAN Y SALEN DE TU API, SEPARADA DE LA ENTIDAD
 * CAMPOS, @NOTBLANK @SCHEMA
 *
 * RESPONSE: API -> CLIENTE
 * LO QUE LA API DECIDE MOSTRAR
 *
 *
 * */
@Schema(description = "Información del producto")
public record ProductoResponse (

        @Schema(description = "ID único del producto", example = "1")
        Long id,

        @Schema(description = "Nombre del producto", example = "Laptop gamer")
        String nombre,

        @Schema(description = "Categoria del producto", example = "Electrónico")
        String categoria,

        @Schema(description = "Precio del producto", example = "15999.00")
        BigDecimal precio,

        @Schema(description = "Cantidad disponible del producto", example = "300")
        Integer cantidad
        ){

}