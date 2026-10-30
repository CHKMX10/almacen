package com.jesus.almacen.service.ventas;

import com.jesus.almacen.dto.ventas.VentaRequest;
import com.jesus.almacen.dto.ventas.VentaResponse;

import java.util.List;
/*
 *
 *ORQUESTA EL CASO DE USO
 *
 * FLUJO: BUSCAR, VALIDAR EXISTENCIA,
 *  GUARDAR, LANZAR EXCEPCIONES
 *
 *QUE PUEDES HACER PERO NO COMO
 * */
public interface VentaService {

    List<VentaResponse> listar();

    List<VentaResponse> listarCanceladas();

    VentaResponse obtenerPorIdActiva(Long id);

    VentaResponse registrar(VentaRequest request);

    void cancelar(Long id);
}
