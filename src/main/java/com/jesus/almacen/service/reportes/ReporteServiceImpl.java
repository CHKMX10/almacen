package com.jesus.almacen.service.reportes;

import com.jesus.almacen.dto.reportes.ReporteVentasSucursal;
import com.jesus.almacen.enums.EstadoVenta;
import com.jesus.almacen.repositories.VentasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReporteServiceImpl implements ReporteService{
    private final VentasRepository ventasRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReporteVentasSucursal> ventasPorSucursal() {
        log.info("Generando reporte de ventas por sucursal");
        return ventasRepository.reportePorSucursal(EstadoVenta.REGISTRADA);
    }
}
