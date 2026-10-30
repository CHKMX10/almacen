package com.jesus.almacen.service.productos;

import com.jesus.almacen.dto.productos.ProductoRequest;
import com.jesus.almacen.dto.productos.ProductoResponse;
import com.jesus.almacen.entities.Producto;
import com.jesus.almacen.enums.Categoria;
import com.jesus.almacen.exceptions.RecursoNoEncontradoException;
import com.jesus.almacen.mappers.ProductoMapper;
import com.jesus.almacen.repositories.ProductoRepository;
import com.jesus.almacen.specifications.ProductoSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


/*
 *
 *ORQUESTA EL CASO DE USO
 *
 * IMPLEMENTA LAS OPERACIONES
 *
 * FLUJO: BUSCAR, VALIDAR EXISTENCIA,
 *  GUARDAR, LANZAR EXCEPCIONES
 *
 *QUE PUEDES HACER PERO NO COMO
 * */


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductoServiceImpl implements ProductoService{

    private final ProductoRepository productoRepository;

    private final ProductoMapper productoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar(String nombre, String categoria, BigDecimal precioMin, BigDecimal precioMax) {
        log.info("Listando con filtros");

        Categoria categoriaEnum = (categoria == null || categoria.isBlank())
                ? null
                : Categoria.obtenerCategoriaPorDescripcion(categoria.trim());

        Specification<Producto> filtros = Specification.allOf(
                ProductoSpecifications.nombreParcial(nombre),
                ProductoSpecifications.categoriaIgual(categoriaEnum),
                ProductoSpecifications.precioMinimo(precioMin),
                ProductoSpecifications.precioMaximo(precioMax)
        );

        return productoRepository.findAll(filtros).stream()
                .map(productoMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorId(Long id) {
        return productoMapper.entidadAResponse(obtenerProductoOException(id));
    }

    @Override
    public ProductoResponse registrar(ProductoRequest request) {

        log.info("Registrando nuevo producto...");

        Producto producto = productoMapper.requestAEntidad(
                request,Categoria.obtenerCategoriaPorDescripcion(request.categoria().trim())
        );
        productoRepository.saveAndFlush(producto);

        log.info("Nuevo producto {} registrado" , producto.getNombre());

        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {

        Producto producto = obtenerProductoOException(id);

        log.info("Actualizando un producto con id: {} ", id);

        producto.actualizar(
                request.nombre(),
                Categoria.obtenerCategoriaPorDescripcion(request.categoria().trim()),
                request.precio(),
                request.cantidad()
                );

        productoRepository.saveAndFlush(producto);

        log.info("Producto con id: {} actualizado", id);

        return productoMapper.entidadAResponse(producto);
    }

    @Override
    public void eliminar(Long id) {

        Producto producto = obtenerProductoOException(id);

        log.info("Eliminando producto con id: {}", id);

        productoRepository.delete(producto);
        productoRepository.flush();

        log.info("Producto con id {} eliminado", id);

    }

    private Producto obtenerProductoOException(Long id){

        log.info("Buscando producto con id: {}", id);

        return productoRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException(
                        "Producto no encontrado con id: "+id
                )
        );
    }
}
