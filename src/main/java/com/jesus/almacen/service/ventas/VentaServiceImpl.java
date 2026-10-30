package com.jesus.almacen.service.ventas;

import com.jesus.almacen.dto.productos.ProductoResponse;
import com.jesus.almacen.dto.ventas.DetalleVentaRequest;
import com.jesus.almacen.dto.ventas.VentaRequest;
import com.jesus.almacen.dto.ventas.VentaResponse;
import com.jesus.almacen.entities.DetalleVenta;
import com.jesus.almacen.entities.Producto;
import com.jesus.almacen.entities.Sucursales;
import com.jesus.almacen.entities.Venta;
import com.jesus.almacen.enums.EstadoVenta;
import com.jesus.almacen.exceptions.DatoInvalidoException;
import com.jesus.almacen.exceptions.RecursoNoEncontradoException;
import com.jesus.almacen.mappers.VentaMapper;
import com.jesus.almacen.repositories.ProductoRepository;
import com.jesus.almacen.repositories.SucursalRepository;
import com.jesus.almacen.repositories.VentasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VentaServiceImpl implements VentaService{

    private final ProductoRepository productoRepository;

    private final SucursalRepository sucursalRepository;

    private final VentasRepository ventasRepository;

    private final VentaMapper ventaMapper;

    @Override
    public List<VentaResponse> listar() {

        return ventasRepository.findAllByEstadoVenta(EstadoVenta.REGISTRADA)
                .stream()
                .map(ventaMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listarCanceladas() {
        return ventasRepository.findAllByEstadoVenta(EstadoVenta.CANCELADA)
                .stream()
                .map(ventaMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public VentaResponse obtenerPorIdActiva(Long id) {

        log.info("Buscando venta activa con id: {}", id);

        Venta venta = ventasRepository.findByIdAndEstadoVenta(id, EstadoVenta.REGISTRADA)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Venta activa no encontrada con id: " + id));

        return ventaMapper.entidadAResponse(venta);    }

    @Override
    public VentaResponse registrar(VentaRequest request) {
        log.info("Registrando venta para la sucursal {}", request.idSucursal());

        Sucursales sucursal = sucursalRepository.findById(request.idSucursal())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Sucursal no encontrada con id: " + request.idSucursal()));

        Venta venta = Venta.builder()
                .sucursal(sucursal)
                .fecha(LocalDate.now())
                .estadoVenta(EstadoVenta.REGISTRADA)
                .build();

        Set<Long> productosVistos = new HashSet<>();

        for (DetalleVentaRequest detalleRequest : request.productos()) {

            if (!productosVistos.add(detalleRequest.idProducto())) {
                throw new DatoInvalidoException(
                        "El producto " + detalleRequest.idProducto() + " está repetido en la venta");
            }

            Producto producto = productoRepository.findById(detalleRequest.idProducto())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "Producto no encontrado con id: " + detalleRequest.idProducto()));

            DetalleVenta detalle = DetalleVenta.crear(producto, detalleRequest.cantidadProducto());
            producto.descontarCantidad(detalleRequest.cantidadProducto());
            venta.agregarDetalleVenta(detalle);
        }

        Venta guardada = ventasRepository.save(venta);
        log.info("Venta {} registrada. Total: {}", guardada.getId(), guardada.calcularTotal());

        return ventaMapper.entidadAResponse(guardada);
    }

    @Override
    @Transactional
    public void cancelar(Long id) {
        log.info("Cancelando venta con id: {}", id);

        Venta venta = ventasRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Venta no encontrada con id" + id
                ));
        venta.cancelar();

        for(DetalleVenta detalleVenta : venta.getDetalleVentas()){
            detalleVenta.getProducto().aumentarCantidad(detalleVenta.getCantidadProducto());
        }
        log.info("Venta {} cancelada y stock devuelto", id);
    }

}
