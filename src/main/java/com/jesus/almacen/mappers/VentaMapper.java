package com.jesus.almacen.mappers;


import com.jesus.almacen.dto.productos.ProductoResponse;
import com.jesus.almacen.dto.ventas.DetalleVentaResponse;
import com.jesus.almacen.dto.ventas.VentaRequest;
import com.jesus.almacen.dto.ventas.VentaResponse;
import com.jesus.almacen.entities.DetalleVenta;
import com.jesus.almacen.entities.Sucursales;
import com.jesus.almacen.mappers.SucursalesMapper;
import com.jesus.almacen.entities.Venta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VentaMapper {

    private final SucursalesMapper sucursalesMapper;

    public VentaResponse entidadAResponse(Venta venta) {
        if (venta == null) {
            return null;
        }

        List<DetalleVentaResponse> detalles = venta.getDetalleVentas()
                .stream()
                .map(this::detalleAResponse)
                .toList();

        return new VentaResponse(
                venta.getId(),
                venta.getFecha().toString(),
                venta.getEstadoVenta().name(),
                sucursalesMapper.entidadAResponse(venta.getSucursal()),
                detalles,
                venta.calcularTotal()
        );
    }

    private DetalleVentaResponse detalleAResponse(DetalleVenta detalle) {
        return new DetalleVentaResponse(
                detalle.getProducto().getId(),
                detalle.getProducto().getNombre(),
                detalle.getCantidadProducto(),
                detalle.getPrecioProducto(),
                detalle.calcularSubtotal()
        );
    }


}
