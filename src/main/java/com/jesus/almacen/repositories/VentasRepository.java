package com.jesus.almacen.repositories;

import com.jesus.almacen.dto.reportes.ReporteVentasSucursal;
import com.jesus.almacen.entities.Venta;
import com.jesus.almacen.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VentasRepository extends JpaRepository<Venta, Long> {

    List<Venta> findAllByEstadoVenta(EstadoVenta estadoVenta);

    Optional<Venta> findByIdAndEstadoVenta(Long id, EstadoVenta estadoVenta);

    @Query("""
        SELECT new com.jesus.almacen.dto.reportes.ReporteVentasSucursal(
            s.id,
            s.nombre,
            SUM(d.cantidadProducto * d.precioProducto),
            SUM(d.cantidadProducto))
        FROM Sucursales s
        LEFT JOIN Venta v ON v.sucursal = s AND v.estadoVenta = :estado
        LEFT JOIN v.detalleVentas d
        GROUP BY s.id, s.nombre
        ORDER BY s.id
        """)
    List<ReporteVentasSucursal> reportePorSucursal(
            @Param("estado") EstadoVenta estado);
}
