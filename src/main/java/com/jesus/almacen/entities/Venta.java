package com.jesus.almacen.entities;

import com.jesus.almacen.enums.EstadoVenta;
import com.jesus.almacen.exceptions.ConflictoException;
import com.jesus.almacen.exceptions.DatoInvalidoException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
@Table(name = "VENTAS")
@NoArgsConstructor //CONSTRUCTOR VACIO
@AllArgsConstructor //GENERA TODOS LOS CONSTRUCTORES
@Getter @Builder
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VENTA")
    private Long id;

    @Column(name = "ESTADO" , nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoVenta estadoVenta;

    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;

    @ManyToOne(fetch = FetchType.LAZY)//decir que tiene relacion de muchos a uno
    @JoinColumn(name = "ID_SUCURSAL" , nullable = false)//propiedad de las relaciones
    private Sucursales sucursal;

    @Builder.Default//construye y si no tiene, le mete unaLISTA DE DETALLES LISTA NUEVA
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalleVentas = new ArrayList<>();

    //  METODOS PARA VALIDAR LOS DATOS, CRUD, ENTRE OTROS

    public void agregarDetalleVenta(DetalleVenta detalleVenta){

        if(detalleVenta == null)
            throw new DatoInvalidoException("El detalle de la venta es requerido");

        this.detalleVentas.add(detalleVenta);
        detalleVenta.asignarVenta(this);
    }

    public void cancelar(){
        if(this.estadoVenta == EstadoVenta.CANCELADA)
            throw new ConflictoException("La venta ya esta cancelada");

        this.estadoVenta = EstadoVenta.CANCELADA;
    }

    public BigDecimal calcularTotal(){
        return detalleVentas.stream()
                .map(DetalleVenta::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
