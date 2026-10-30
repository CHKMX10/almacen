package com.jesus.almacen.entities;

import com.jesus.almacen.enums.Categoria;
import com.jesus.almacen.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

@Entity
@Table(name= "SUCURSALES")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Builder
public class Sucursales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SUCURSAL")
    private Long id;

    @Column(name = "NOMBRE", length = 50, nullable = false, unique = true)
    private String nombre;

    @Column(name = "DIRECCION", length = 150, nullable = false )
    private String direccion;


    //  METODOS PARA VALIDAR LOS DATOS, CRUD, ENTRE OTROS

    private static void validarDatos(String nombre, String direccion){

        StringCustomUtils.validarTamanio(nombre, 5, 50, "El nombre es requerido y debe contener entre 5 y 30 caracteres");
        StringCustomUtils.validarTamanio(direccion, 10, 150, "La direccion es requerida y debe contener entre 5 y 150 caracteres");
    }

    public void actualizar(
            String nombre,String direccion){

        validarDatos(nombre, direccion);

        this.nombre = nombre.trim();
        this.direccion = direccion.trim();
    }

    // METODO PARA CREAR EL "PRODUCTO" PARA JAVA, FABRICA ESTATICA

    public static Sucursales crear(String nombre, String direccion){
        validarDatos(nombre, direccion);

        return Sucursales.builder()
                .nombre(nombre.trim())
                .direccion(direccion.trim())
                .build();// DEVUELVE EL PRODUCTO DEL METODO CONSTRUIDO
    }



}
