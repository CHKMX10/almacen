package com.jesus.almacen.entities;

import com.jesus.almacen.enums.Categoria;
import com.jesus.almacen.exceptions.DatoInvalidoException;
import com.jesus.almacen.utils.StringCustomUtils;
import com.jesus.almacen.utils.ValoresNumericosUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
/*
* REPRESENTA LA TABLA Y SUS REGLAS
* ES LA CLASE POR LA QUE SE EMPIEZA
*
* @ENTITY, COLUMNAS, VALIDACIONES PROPIAS:
*       CREAR()
*       descontar()
*       validar()
*       verificar()
*
*
* */

@Entity//Esta es el encapsulamiento, tiene que asegurar la integridad de los datos
@Table(name = "DETALLES_VENTAS")
@NoArgsConstructor //COSSTRUCTOR VACIO
@AllArgsConstructor //GENERA TODOS LOS CONSTRUCTORES
@Getter
@Builder
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DETALLE_VENTA")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_VENTA")
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PRODUCTO")
    private Producto producto;

    @Column(name = "CANTIDAD_PRODUCTO", nullable = false)
    //directamente la columna y su nombre, al igual que la condicion de que no es nulleable
    private Integer cantidadProducto;

    @Column(name = "PRECIO_PRODUCTO", nullable = false)
    private BigDecimal precioProducto;

    //  METODOS PARA VALIDAR LOS DATOS, CRUD, ENTRE OTROS

    public static DetalleVenta crear(Producto producto, Integer cantidadVendida){
        if(producto == null){
            throw new DatoInvalidoException("El producto no puede ser nulo");
        }
        ValoresNumericosUtils.validarEnteroPositivo(cantidadVendida,
                "La cantidad del prosucto debe ser positiva");

    return DetalleVenta.builder()
            .producto(producto)
            .cantidadProducto(cantidadVendida)
            .precioProducto(producto.getPrecio())
            .build();
    }

    public BigDecimal calcularSubtotal() {
        return precioProducto.multiply(BigDecimal.valueOf(cantidadProducto));
    }

    public void asignarVenta(Venta venta){

        if(venta == null )
            throw new DatoInvalidoException("La venta es requerida");

    this.venta = venta;
    }

}
