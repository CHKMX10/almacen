package com.jesus.almacen.service.sucursales;

import com.jesus.almacen.dto.productos.ProductoRequest;
import com.jesus.almacen.dto.productos.ProductoResponse;
import com.jesus.almacen.dto.sucursales.SucursalesRequest;
import com.jesus.almacen.dto.sucursales.SucursalesResponse;

import java.math.BigDecimal;
import java.util.List;

public interface SucursalesService {

    List<SucursalesResponse> listar();

    SucursalesResponse obtenerPorId(Long id);

    SucursalesResponse registrar(SucursalesRequest request);

    SucursalesResponse actualizar(SucursalesRequest request, Long id);

    void eliminar (Long id);
}
