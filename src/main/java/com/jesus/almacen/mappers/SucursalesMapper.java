package com.jesus.almacen.mappers;

import com.jesus.almacen.dto.sucursales.SucursalesRequest;
import com.jesus.almacen.dto.sucursales.SucursalesResponse;
import com.jesus.almacen.entities.Sucursales;
import org.springframework.stereotype.Component;


@Component
public class SucursalesMapper {

    public Sucursales requestAEntidad(SucursalesRequest request){

        return request == null
                ? null
                : Sucursales.crear(
                        request.nombre(),
                        request.direccion()
        );
    }

    public SucursalesResponse entidadAResponse(Sucursales sucursales){

        return sucursales == null
                ? null
                : new SucursalesResponse(
                    sucursales.getId(),
                    sucursales.getNombre(),
                    sucursales.getDireccion());

    }
}
