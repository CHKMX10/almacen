package com.jesus.almacen.controllers;

import com.jesus.almacen.dto.productos.ProductoRequest;
import com.jesus.almacen.dto.productos.ProductoResponse;
import com.jesus.almacen.entities.Producto;
import com.jesus.almacen.service.productos.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "API Productos", description = "Gestión de inventario de productos")
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    @Operation(summary = "Listar productos", description = "todos los filtros son opcionales")
    @ApiResponse(responseCode = "200", description = "Listado obtenido, te la rifaste")
    public ResponseEntity <List<ProductoResponse>> listar(

            @Parameter(description = "Busqueda por nombre", example = "Laptop")
            @RequestParam(required = false) String nombre,

            @Parameter(description = "Filtro por categoria", example = "Electrónica")
            @RequestParam(required = false) String categoria,

            @Parameter(description = "Precio mínimo", example = "1")
            @RequestParam(required = false) BigDecimal precioMin,

            @Parameter(description = "Precio maximo", example = "50000.00")
            @RequestParam(required = false) BigDecimal precioMax

    ){
        return ResponseEntity.ok(productoService.listar(nombre, categoria, precioMin, precioMax));
    }

    @GetMapping("/{id}")//CONSULTA POR MEDIO DEL ID
    @Operation(summary = "Producto por ID", description = "Busqueda de Producto por ID")
    @ApiResponse(responseCode = "200", description = "Producto encontrado")
    public ResponseEntity<ProductoResponse> obtenerPorId(

            @Parameter(description = "Id del producto", example = "1")
            @PathVariable @Positive (message = "El ID debe ser psoitivo") Long id

    ){
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }


    @PostMapping//CREACION POR MEDIO DE INSERCION
    @Operation(summary = "Agregar productos", description = "Agregar los productos en la DB")
    @ApiResponse(responseCode = "201", description = "Resgistro exitoso")
    public ResponseEntity<ProductoResponse> registrar(
            ProductoRequest request

    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.registrar(request));

    }

    @DeleteMapping("{id}")//ELIMINACION CON ID COMO PARAMETRO
    @Operation(summary = "Eliminar productos", description = "Elimina los productos en la DB")
    @ApiResponse(responseCode = "204", description = "Eliminacion exitosa")
    public ResponseEntity<Void> eliminar(

            @Parameter(description = "Id del producto", example = "1")
            @PathVariable @Positive (message = "El ID debe ser psoitivo") Long id

    ){

        productoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")//
    @Operation(summary = "Actualizar recurso: productos", description = "Actualiza el recurso de productos en la DB")
    @ApiResponse(responseCode = "204", description = "Actualizacion exitosa")
    public ResponseEntity<ProductoResponse> actualizar(

            @Parameter(description = "Id del producto", example = "1")
            @PathVariable @Positive (message = "El ID debe ser psoitivo") Long id,

            @Valid @RequestBody ProductoRequest request
    ){
        return ResponseEntity.ok(productoService.actualizar(request, id));
    }

}