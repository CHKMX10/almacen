package com.jesus.almacen.repositories;

import com.jesus.almacen.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
public interface ProductoRepository extends JpaRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {//VA LA CLASE U OBJETOS Y EL ID

}
