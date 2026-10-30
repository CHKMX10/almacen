package com.jesus.almacen.controllers;

import com.jesus.almacen.dto.reportes.ReporteVentasSucursal;
import com.jesus.almacen.service.reportes.ReporteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
@Tag(name = "API reportes", description = "Reportes financieros")
public class ReporteController {

    private final ReporteService reporteService;

    @GetMapping("/ventas-por-sucursal")
    public List<ReporteVentasSucursal> ventasPorSucursal() {
        return reporteService.ventasPorSucursal();
    }
}

