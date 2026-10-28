package com.jesus.almacen.repositories;

import com.jesus.almacen.entities.Sucursales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursales, Long> {

    //SELECT COUNT(*) FROM SUCURSALES WHERE LOWER(NOMBRE) = LOWER(:NOMBRE);
    boolean existsByNombreIgnoreCase(String nombre); //
    //ignorecase es para que no importe si es exactamente y no se interese en mayusculas o minusculas

    //SELECT COUNT(*) FROM SUCURSALES WHERE LOWER(NOMBRE) = LOWER(:NOMBRE) AND ID_SUCURSAL <> :ID;
    boolean existsByNombreIgnoreCaseAndIdNot(String nombre, Long id); //

}
