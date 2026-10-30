package com.jesus.almacen.dto.ventas;

import com.jesus.almacen.dto.sucursales.SucursalesResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;
/*
 * DATA TRANSFER OBJECT
 * DEFINE QUE DATOS ENTRAN Y SALEN DE TU API, SEPARADA DE LA ENTIDAD
 *
 * RESPONSE: API -> CLIENTE
 * LO QUE LA API DECIDE MOSTRAR
 *
 *
 * */
@Schema(description = "Datos de una venta")
public record VentaResponse (
        @Schema(description = "Identificador de la venta", example = "1")
        Long id,

        @Schema(description = "Fecha de la venta", example = "13/09/2001")
        String fecha,

        @Schema(description = "Estado de la venta", example = "Registrada")
        String estado,

        @Schema(description = "Sucursal donde se realizo la venta")
        SucursalesResponse sucursales,//ESTA ES ASI PORQUE VIENE DE UNA LLAVE FORANEA

        @Schema(description = "Lista de productos de la venta")
        List<DetalleVentaResponse> detalles,

        @Schema(description = "Total de la venta", example = "1500.00")
        BigDecimal total) {
}
