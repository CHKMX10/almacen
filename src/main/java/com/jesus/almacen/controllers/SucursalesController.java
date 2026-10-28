package com.jesus.almacen.controllers;


import com.jesus.almacen.dto.productos.ProductoRequest;
import com.jesus.almacen.dto.productos.ProductoResponse;
import com.jesus.almacen.dto.sucursales.SucursalesRequest;
import com.jesus.almacen.dto.sucursales.SucursalesResponse;
import com.jesus.almacen.service.sucursales.SucursalesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/surcursales")
@RequiredArgsConstructor
@Tag(name = "API Sucursales", description = "Gestión de sucursales")
public class SucursalesController {

    private final SucursalesService sucursalesService;


    @GetMapping
    @Operation(summary = "Listar sucursales", description = "Obtiene todas las sucursales registradas")
    @ApiResponse(responseCode = "200", description = "Listado obtenido, te la rifaste")
    public ResponseEntity<List<SucursalesResponse>> listar(){
        return ResponseEntity.ok(sucursalesService.listar());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener sucursales por ID")
    @ApiResponse(responseCode = "200", description = "Sucursal obtenida, te la rifaste")
    public ResponseEntity<SucursalesResponse> obtenerPorId(

            @Parameter(description = "Id de la sucursal", example = "1")
            @PathVariable @Positive(message = "El ID debe ser psoitivo") Long id

    ){
        return ResponseEntity.ok(sucursalesService.obtenerPorId(id));
    }

    @PostMapping//CREACION POR MEDIO DE INSERCION
    @Operation(summary = "Agregar sucursal", description = "Agregar las sucursales en la DB")
    @ApiResponse(responseCode = "201", description = "Resgistro exitoso")
    public ResponseEntity<SucursalesResponse> registrar(
          @Valid @RequestBody SucursalesRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sucursalesService.registrar(request));
    }


    @PutMapping("/{id}")//
    @Operation(summary = "Actualizar recurso: sucursal", description = "Actualiza el recurso de sucursal en la DB")
    @ApiResponse(responseCode = "204", description = "Actualizacion exitosa")
    public ResponseEntity<SucursalesResponse> actualizar(

            @Parameter(description = "Id de la sucursal", example = "1")
            @PathVariable @Positive (message = "El ID debe ser psoitivo") Long id,

            @Valid @RequestBody SucursalesRequest request
    ){
        return ResponseEntity.ok(sucursalesService.actualizar(request, id));
    }

    @DeleteMapping("{id}")//ELIMINACION CON ID COMO PARAMETRO
    @Operation(summary = "Eliminar sucursales", description = "Elimina la sucursal en la DB")
    @ApiResponse(responseCode = "204", description = "Eliminacion exitosa")
    public ResponseEntity<Void> eliminar(

            @Parameter(description = "Id de la sucursal", example = "1")
            @PathVariable @Positive (message = "El ID debe ser psoitivo") Long id

    ){
        sucursalesService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

}
