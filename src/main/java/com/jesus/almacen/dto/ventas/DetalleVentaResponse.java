package com.jesus.almacen.dto.ventas;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
/*
 * DATA TRANSFER OBJECT
 * DEFINE QUE DATOS ENTRAN Y SALEN DE TU API, SEPARADA DE LA ENTIDAD
 *
 * RESPONSE: API -> CLIENTE
 * LO QUE LA API DECIDE MOSTRAR
 *
 *
 * */
@Schema(description = "Detalle de un producto dentro de una venta")
public record DetalleVentaResponse(
        @Schema(description = "ID único del producto", example = "1")
        Long idProducto,

        @Schema(description = "Nombre del producto", example = "Laptop gamer")
        String nombreProducto,

        @Schema(description = "Cantidad de unidades vendidas", example = "10")
        Integer cantidadProducto,

        @Schema(description = "Precio unitario del producto", example = "1500.00")
        BigDecimal precioProducto,

        @Schema(description = "Subtotal del producto", example = "300")
        BigDecimal subtotal
) {
}
