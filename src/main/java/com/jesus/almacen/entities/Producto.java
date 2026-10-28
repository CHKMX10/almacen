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

@Entity//Esta es el encapsulamiento, tiene que asegurar la integridad de los datos
@Table(name = "PRODUCTOS")
@NoArgsConstructor //CONSTRUCTOR VACIO
@AllArgsConstructor //GENERA TODOS LOS CONSTRUCTORES
@Getter @Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRODUCTO")
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 30)
    private String nombre;

    @Column(name = "CATEGORIA", nullable = false)
    @Enumerated(EnumType.STRING)//devuelve el nombre tal cual y no la posicion
    private Categoria categoria;

    @Column(name = "PRECIO", nullable = false)
    private BigDecimal precio;

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    private static void validarDatos(String nombre, Categoria categoria,
                                     BigDecimal precio, Integer cantidad) {

        StringCustomUtils.validarTamanio(nombre,5, 30, "El nombre es requerido y debe tener entre 5 y 30 caracteres");//validamos los datos en solitario sin delegar responsabilidades

        if (categoria == null)
            throw new DatoInvalidoException("La categoria es requerida");

        ValoresNumericosUtils.validarEnteroPositivo(cantidad,
                "El precio es requerido y debe ser positivo");

        ValoresNumericosUtils.validarBigDecimalPositivo(precio,
                "La cantidad es requerida y debe ser positivo");
    }

    public void actualizar(
            String nombre, Categoria categoria,
            BigDecimal precio, Integer cantidad){

        validarDatos(nombre, categoria, precio, cantidad);

        this.nombre = nombre.trim();
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public void aumentarCantidad (int cantidad){
        ValoresNumericosUtils.validarEnteroPositivo(cantidad,
                "La cantidad debe ser positiva");
        this.cantidad += cantidad;
    }

    public void descontarCantidad (int cantidad){

        ValoresNumericosUtils.validarEnteroPositivo(cantidad,
                "La cantidad debe ser positiva");

        if ( cantidad > this.cantidad)
            throw new DatoInvalidoException(
                    "La cantidad debe de ser menor o " +
                            "igual a la cantidad actual");

        this.cantidad -= cantidad;
    }

    public static Producto crear(String nombre, Categoria categoria,
                                 BigDecimal precio,Integer cantidad) {

        validarDatos(nombre, categoria, precio, cantidad);

        return Producto.builder()
                .nombre(nombre.trim())
                .categoria(categoria)
                .cantidad(cantidad)
                .precio(precio)
                .build();

        //return new Producto(null, nombre, categoria, precio, cantidad);
    }
}
