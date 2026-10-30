package com.jesus.almacen.specifications;

import com.jesus.almacen.entities.Producto;
import com.jesus.almacen.enums.Categoria;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Locale;

public class ProductoSpecifications {

    private ProductoSpecifications(){

    }

    public static Specification<Producto> nombreParcial(String nombre){
        return (root, query, cb) ->
                (nombre == null || nombre.isBlank())
                        ? null
                        : cb.like(
                                cb.lower(root.get("nombre")), "%" + nombre.trim().toLowerCase() + "%"
                        );
    }

        public static Specification<Producto> categoriaIgual(Categoria categoria){
            return (root, query, cb) ->
                    categoria == null
                            ? null
                            : cb.equal(root.get("categoria"), categoria);
        }

        public static Specification<Producto> precioMinimo(BigDecimal precioMin){
        return (root, query, cb) ->
                precioMin == null
                ? null
                : cb.greaterThanOrEqualTo(root.<BigDecimal>get("precio"), precioMin);
        }

        public static Specification<Producto> precioMaximo(BigDecimal precioMax){
        return (root, query, cb) ->
                precioMax == null
                ? null
                : cb.lessThanOrEqualTo(root.<BigDecimal>get("precio"), precioMax);
        }
}
