package com.jesus.almacen.dto.sucursales;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/*
 * DATA TRANSFER OBJECT
 * DEFINE QUE DATOS ENTRAN Y SALEN DE TU API, SEPARADA DE TU ENTIDAD
 * CAMPOS, @NOTBLANK, @CHEMA
 *
 * REQUEST: CLIENTE -> API
 * LO QUE EL CLIENTE PUEDE ENVIAR
 *
 *sin id
 *
 *NADA DE LOGICA, ANOTACIONES DE JPA
 * */
@Schema(description = "Datos necesarios para crear o actualizar un producto")
public record SucursalesRequest(//se valida porque viene de fuera OSEA DEL CLIENTE

        @Schema(description = "Nombre de la sucursal", example = "Lazaro")
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 50, message = "El nombre debe tener entre 5 y 150 caracteres")
        String nombre,

        @Schema(description = "Direccion de la sucursal", example = "Laptop")
        @NotBlank(message = "La direccion es requerida")
        @Size(min = 10, max = 150, message = "La direccion debe tener entre 5 y 30 caracteres")
        String direccion
) {
}
