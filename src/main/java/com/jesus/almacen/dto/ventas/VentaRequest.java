package com.jesus.almacen.dto.ventas;

import com.jesus.almacen.entities.DetalleVenta;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;
/*
 * DATA TRANSFER OBJECT
 * DEFINE QUE DATOS ENTRAN Y SALEN DE TU API, SEPARADA DE TU ENTIDAD
 * CAMPOS, @NOTBLANK, @SCHEMA
 *
 * REQUEST: CLIENTE -> API
 * LO QUE EL CLIENTE PUEDE ENVIAR
 *
 *
 *NADA DE LOGICA, ANOTACIONES DE JPA
 * */
@Schema(description = "Datos necesarios para crear una venta")
public record VentaRequest(
        @Schema(description = "ID de la sucursal", example = "1")
        @NotNull(message = "El ID es requerido")
        @Positive(message = "El ID debe ser positivo")
        Long idSucursal,

        @Schema(description = "Lista de productos de la venta", example = "1")
        @NotEmpty(message = "La Lista de productos es requerida y no puede estar vacia")
        List<@Valid DetalleVentaRequest> productos
) {
}
