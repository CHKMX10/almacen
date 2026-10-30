package com.jesus.almacen.controllers;

import com.jesus.almacen.docs.ProblemaDoc;
import com.jesus.almacen.dto.productos.ProductoRequest;
import com.jesus.almacen.dto.productos.ProductoResponse;
import com.jesus.almacen.entities.Producto;
import com.jesus.almacen.service.productos.ProductoService;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/*
CLASE "MAIN" QUE DEVUELVE DATOS A MENUDO JSON
SPRING LO MANDA DIRECTAMENTE AL CLIENTE
DEVUELVE DATOS
*/

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "API Productos", description = "Gestión de inventario de productos")
// Errores que pueden ocurrir en cualquier endpoint:
@ApiResponse(responseCode = "400", description = "Datos o parámetros inválidos",
        content = @Content(mediaType = "application/problem+json",
                schema = @Schema(implementation = ProblemaDoc.class)))
@ApiResponse(responseCode = "500", description = "Error interno del servidor",
        content = @Content(mediaType = "application/problem+json",
                schema = @Schema(implementation = ProblemaDoc.class)))
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    @Operation(summary = "Listar productos", description = "Todos los filtros son opcionales")
    @ApiResponse(responseCode = "200", description = "Listado obtenido")
    public ResponseEntity <List<ProductoResponse>> listar(

            @Parameter(description = "Busqueda por nombre", example = "Laptop")
            @RequestParam(required = false) String nombre,

            @Parameter(description = "Filtro por categoria", example = "Electrónica",
                schema = @Schema(allowableValues = {"ALIMENTO", "HIGIENE", "JUGUETE",
            "ELECTRONICA", "ROPA", "ACCESORIO", "FARMACIA"}))
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
    @ApiResponse(responseCode = "404", description = "El producto no existe",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))
    public ResponseEntity<ProductoResponse> obtenerPorId(

            @Parameter(description = "Id del producto", example = "1")
            @PathVariable @Positive (message = "El ID debe ser psoitivo") Long id

    ){
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }


    @PostMapping//CREACION POR MEDIO DE INSERCION
    @Operation(summary = "Agregar productos", description = "Agregar los productos en la DB")
    @ApiResponse(responseCode = "201", description = "Resgistro exitoso")
    @ApiResponse(responseCode = "409", description = "Conflicto con datos existentes",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))
    public ResponseEntity<ProductoResponse> registrar(

           @Valid @RequestBody ProductoRequest request

    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.registrar(request));

    }

    @DeleteMapping("{id}")//ELIMINACION CON ID COMO PARAMETRO
    @Operation(summary = "Eliminar un producto")
    @ApiResponse(responseCode = "204", description = "Producto eliminado")
    @ApiResponse(responseCode = "404", description = "El producto no existe",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))
    @ApiResponse(responseCode = "409", description = "El producto está en uso y no puede eliminarse",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))

    public ResponseEntity<Void> eliminar(
            @Parameter(description = "Id del producto", example = "1")
            @PathVariable @Positive (message = "El ID debe ser psoitivo") Long id

    ){

        productoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")//
    @Operation(summary = "Actualizar", description = "Actualizar un producto")
    @ApiResponse(responseCode = "200", description = "Producto actualizado")
    @ApiResponse(responseCode = "404", description = "El producto no existe",
            content = @Content(mediaType = "application/problem+json",
                    schema = @Schema(implementation = ProblemaDoc.class)))
    public ResponseEntity<ProductoResponse> actualizar(

            @Parameter(description = "Id del producto", example = "1")
            @PathVariable @Positive (message = "El ID debe ser psoitivo") Long id,

            @Valid @RequestBody ProductoRequest request
    ){
        return ResponseEntity.ok(productoService.actualizar(request, id));
    }

}