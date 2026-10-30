package com.jesus.almacen.service.sucursales;

import com.jesus.almacen.dto.productos.ProductoRequest;
import com.jesus.almacen.dto.sucursales.SucursalesRequest;
import com.jesus.almacen.dto.sucursales.SucursalesResponse;
import com.jesus.almacen.entities.Producto;
import com.jesus.almacen.entities.Sucursales;
import com.jesus.almacen.enums.Categoria;
import com.jesus.almacen.exceptions.ConflictoException;
import com.jesus.almacen.exceptions.RecursoNoEncontradoException;
import com.jesus.almacen.mappers.SucursalesMapper;
import com.jesus.almacen.repositories.SucursalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class SucursalesServiceImpl implements SucursalesService {

    private final SucursalRepository sucursalesRepository;
    private final SucursalesMapper sucursalesMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SucursalesResponse> listar() {
        log.info("Listando todos los productos");
        return sucursalesRepository.findAll().stream()
                .map(sucursalesMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SucursalesResponse obtenerPorId(Long id) {
        return sucursalesMapper.entidadAResponse(obtenerSucursalOException(id));
    }

    @Override
    public SucursalesResponse registrar(SucursalesRequest request) {

        log.info("Registrando nueva sucursal...");

        validarDatosUnicos(request);

        Sucursales sucursales = sucursalesMapper.requestAEntidad(request);

        sucursalesRepository.saveAndFlush(sucursales);

        log.info("Nueva sucursal {} registrado" , sucursales.getNombre());

        return sucursalesMapper.entidadAResponse(sucursales);
    }

    @Override
    public SucursalesResponse actualizar(SucursalesRequest request, Long id) {
        log.info("Actualizando una sucursal con id: {} ", id);

        Sucursales sucursales = obtenerSucursalOException(id);

        validarCambiosUnicos(request, id);

        log.info("Actualizando una sucursal con id: {} ", id);

        sucursales.actualizar(
                request.nombre(),
                request.direccion()
        );

        sucursalesRepository.saveAndFlush(sucursales);

        log.info("Sucursal con id: {} actualizado", id);

        return sucursalesMapper.entidadAResponse(sucursales);
    }

    @Override
    public void eliminar(Long id) {
        Sucursales sucursales = obtenerSucursalOException(id);

        log.info("Eliminando sucursal con id: {}", id);

        sucursalesRepository.delete(sucursales);
        sucursalesRepository.flush();

        log.info("Sucursal con id {} eliminado", id);
    }

    private Sucursales obtenerSucursalOException(Long id){

        log.info("Buscando producto con id: {}", id);

        return sucursalesRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException(
                        "Sucursal no encontrada con id: "+id
                )
        );
    }

    private void validarDatosUnicos(SucursalesRequest request){
        log.info("Validando nombre único");

        if(sucursalesRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new ConflictoException(
                    "Ya existe una sucursal con el nombre de: " + request.nombre()
            );
    }
    private void validarCambiosUnicos(SucursalesRequest request, Long id) {
        log.info("Validando nombre único");

        if (sucursalesRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(), id))
            throw new ConflictoException(
                    "Ya existe una sucursal con el nombre de: " + request.nombre()
            );
    }
}

