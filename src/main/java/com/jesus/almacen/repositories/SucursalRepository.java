package com.jesus.almacen.repositories;

import com.jesus.almacen.entities.Sucursales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/*
 * Hablar con Oracle
 * PERMITE OCUPAR JPA
 *
 * FINDBYID, SAVE, CONSULTAS
 *
 * NO SE PONE LOGICA DE NEGOCIO
 * */
@Repository
public interface SucursalRepository extends JpaRepository<Sucursales, Long> {

    //CONSULTA PARA CONSULTAR SI EXISTE OTRO REGISTRO CON LA LLAVE PRIMARIA
    //SELECT COUNT(*) FROM SUCURSALES WHERE LOWER(NOMBRE) = LOWER(:NOMBRE);
    boolean existsByNombreIgnoreCase(String nombre); //
    //ignorecase es para que no importe si es exactamente y no se interese en mayusculas o minusculas

    //CONSULTA PARA CONSULTAR SI EXIXTE OTRO REGISTRO CON EL NOMBRE, EN CASO RECHAZAR EL REGISTRO
    //SELECT COUNT(*) FROM SUCURSALES WHERE LOWER(NOMBRE) = LOWER(:NOMBRE) AND ID_SUCURSAL <> :ID;
    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id); //

}
