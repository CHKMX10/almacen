package com.jesus.almacen.dto.reportes;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Reporte de ventas por sucursal")
public record ReporteVentasSucursal(
        @Schema(description = "ID de la sucursal", example = "1")
        Long idSucursal,

        @Schema(description = "Nombre de la sucursal", example = "Sucursal Centro")
        String nombreSucursal,

        @Schema(description = "Total facturado de ventas activas", example = "250.00")
        BigDecimal totalFacturado,

        @Schema(description = "Piezas vendidas", example = "3")
        Long cantidadProductosVendidos
) {
        public ReporteVentasSucursal {
                totalFacturado = (totalFacturado == null) ? BigDecimal.ZERO : totalFacturado;
                cantidadProductosVendidos = (cantidadProductosVendidos == null) ? 0L : cantidadProductosVendidos;
        }

        // Constructor para la consulta: acepta cualquier número
        public ReporteVentasSucursal(Long idSucursal, String nombreSucursal,
                                     Number totalFacturado, Number cantidadProductosVendidos) {
                this(idSucursal,
                        nombreSucursal,
                        totalFacturado == null ? BigDecimal.ZERO : new BigDecimal(totalFacturado.toString()),
                        cantidadProductosVendidos == null ? 0L : cantidadProductosVendidos.longValue());
        }
}
