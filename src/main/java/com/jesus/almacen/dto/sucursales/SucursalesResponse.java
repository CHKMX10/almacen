package com.jesus.almacen.dto.sucursales;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Información de la sucursal")
public record SucursalesResponse(//no se valida porque viene del servidor y en teoria está correcto

        @Schema(description = "ID único de la sucursal", example = "1")
        Long id,

        @Schema(description = "Nombre de la sucursal", example = "Sucursal Sur")
        String nombre,

        @Schema(description = "Direccion de la sucursal", example = "Av. Revolucion, Calle Lazaro Cardenas")
        String direccion
) {
}
