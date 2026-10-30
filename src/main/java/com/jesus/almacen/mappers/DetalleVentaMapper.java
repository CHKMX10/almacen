package com.jesus.almacen.mappers;

import com.jesus.almacen.dto.ventas.DetalleVentaResponse;
import com.jesus.almacen.entities.DetalleVenta;
import org.springframework.stereotype.Component;

@Component
public class DetalleVentaMapper {

    public DetalleVentaResponse entidadAResponse(DetalleVenta detalleVenta){
        return detalleVenta == null
                ? null
                : new DetalleVentaResponse(
                        detalleVenta.getProducto().getId(),
                        detalleVenta.getProducto().getNombre(),
                        detalleVenta.getCantidadProducto(),
                        detalleVenta.getPrecioProducto(),
                        detalleVenta.calcularSubtotal()
                );
    }


}
