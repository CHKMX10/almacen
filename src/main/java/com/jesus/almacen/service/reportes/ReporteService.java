package com.jesus.almacen.service.reportes;

import com.jesus.almacen.dto.reportes.ReporteVentasSucursal;

import java.util.List;

public interface ReporteService {

    List<ReporteVentasSucursal> ventasPorSucursal();
}