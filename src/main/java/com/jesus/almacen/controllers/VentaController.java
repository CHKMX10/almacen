package com.jesus.almacen.controllers;


import com.jesus.almacen.docs.ProblemaDoc;
import com.jesus.almacen.dto.ventas.VentaRequest;
import com.jesus.almacen.dto.ventas.VentaResponse;
import com.jesus.almacen.service.ventas.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
@Tag(name = "API ventas", description = "Gestión de inventario de ventas")
// Errores que pueden ocurrir en cualquier endpoint:
@ApiResponse(responseCode = "400", description = "Datos o parámetros inválidos",
        content = @Content(mediaType = "application/problem+json",
                schema = @Schema(implementation = ProblemaDoc.class)))
@ApiResponse(responseCode = "500", description = "Error interno del servidor",
        content = @Content(mediaType = "application/problem+json",
                schema = @Schema(implementation = ProblemaDoc.class)))
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    @Operation(summary = "Listar ventas", description = "Todos los filtros son opcionales")
    @ApiResponse(responseCode = "200", description = "Listado obtenido")
    public ResponseEntity<List<VentaResponse>> listar() {
        return ResponseEntity.ok(ventaService.listar());
    }

    @GetMapping("/canceladas")
    @Operation(summary = "Ventas canceladas", description = "Lista de ventas canceladas")
    @ApiResponse(responseCode = "200", description = "Lista obtenida")
    @ApiResponse(responseCode = "404", description = "No existen registros",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))
    public ResponseEntity<List<VentaResponse>> listarCanceladas() {
        ventaService.listarCanceladas();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Venta por ID", description = "Busqueda de Venta por ID")
    @ApiResponse(responseCode = "200", description = "Venta encontrada")
    @ApiResponse(responseCode = "404", description = "La venta no existe",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))
    public ResponseEntity<VentaResponse> obtenerPorIdActiva(@PathVariable Long id) {

        return ResponseEntity.ok(ventaService.obtenerPorIdActiva(id));
    }

    @PostMapping
    @Operation(summary = "Agregar venta", description = "Registrar la venta en la DB")
    @ApiResponse(responseCode = "201", description = "Resgistro exitoso")
    @ApiResponse(responseCode = "409", description = "Conflicto con datos existentes",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))
    public ResponseEntity<VentaResponse> registrar(
            @Valid
            @RequestBody VentaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.registrar(request));
    }


    @DeleteMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar venta")
    @ApiResponse(responseCode = "204", description = "Venta cancelada")
    @ApiResponse(responseCode = "404", description = "La venta no existe",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))

    public ResponseEntity<Void> cancelar(
            @Parameter(description = "Id de la venta", example = "1")
            @PathVariable @Positive(message = "El id debe ser positivo" ) Long id)
    {

        ventaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

}
