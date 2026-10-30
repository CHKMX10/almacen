package com.jesus.almacen.dto.ventas;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
/*
 * DATA TRANSFER OBJECT
 * DEFINE QUE DATOS ENTRAN Y SALEN DE TU API, SEPARADA DE TU ENTIDAD
 * CAMPOS, @NOTBLANK, @CHEMA
 *
 * REQUEST: CLIENTE -> API
 * LO QUE EL CLIENTE PUEDE ENVIAR
 *
 *
 *NADA DE LOGICA, ANOTACIONES DE JPA
 * */
@Schema(description = "Detalle de un producto de venta")
public record DetalleVentaRequest(

        @Schema(description = "ID del producto", example = "1")
        @NotNull(message = "El ID del producto es requerido")
        @Positive(message = "El Id del producto debe ser positivo")
        Long idProducto,

        @Schema(description = "Cantidad del producto", example = "100")
        @NotNull(message = "La cantidad del producto es requerido")
        @Positive(message = "La cantidad debe ser positivo")
        Integer cantidadProducto
        ) {
}
