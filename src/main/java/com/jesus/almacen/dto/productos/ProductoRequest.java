package com.jesus.almacen.dto.productos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Datos necesarios para crear o actualizar un producto")
public record ProductoRequest(

        @Schema(description = "Nombre del producto", example = "Laptop")
        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 30, message = "El nombre debe tener entre 5 y 30 caracteres")
        String nombre,

        @Schema(
                description = "Categoria del producto (puede estar en mayúsculas o minúsculas)",
                example = "Electronico",
                allowableValues = {
                        "ALIMENTO",
                        "HIGIENE",
                        "JUGUETE",
                        "ELECTRONICA",
                        "ROPA",
                        "ACCESORIO",
                        "FARMACIA"
                }
                )
        @NotBlank(message = "La categoria es requerida")
        String categoria,

        @Schema(description = "El precio del producto", example = "1599.99")
        @NotNull(message = "El precio es requerido")
        @Positive(message = "El precio debe ser positivo")
        BigDecimal precio,

        @Schema(description = "El cantidad disponible del producto", example = "300")
        @NotNull(message = "La cantidad es requerido")
        @Positive(message = "La cantidad debe ser positivo")
        Integer cantidad
) {
}
