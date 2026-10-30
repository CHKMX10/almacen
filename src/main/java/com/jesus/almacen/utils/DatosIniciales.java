package com.jesus.almacen.utils;

import com.jesus.almacen.entities.Producto;
import com.jesus.almacen.entities.Sucursales;
import com.jesus.almacen.entities.Venta;
import com.jesus.almacen.enums.Categoria;
import com.jesus.almacen.repositories.ProductoRepository;
import com.jesus.almacen.repositories.SucursalRepository;
import com.jesus.almacen.repositories.VentasRepository;
import com.jesus.almacen.service.ventas.VentaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DatosIniciales implements CommandLineRunner {

    private final ProductoRepository productoRepository;

    private final SucursalRepository sucursalRepository;

    private final VentasRepository ventasRepository;

    private final VentaService ventaService;

    @Override
    public void run(String... args) throws Exception {
        if (productoRepository.count() == 0){

            productoRepository.saveAll(List.of(
                    new Producto (
                            null,
                            "Game Sir",
                            Categoria.ELECTRONICA,
                            BigDecimal.valueOf(31), 12) ,
                    new Producto (
                            null,
                            "Xbox",
                            Categoria.ELECTRONICA,
                            BigDecimal.valueOf(31), 12) ,
                    new Producto (
                        null,
                                "Shampoo",
                                Categoria.HIGIENE,
                                BigDecimal.valueOf(31), 12)



            ));
            log.info("Productos de prueba cargados correctamente");
        }

        if(sucursalRepository.count()==0){
            sucursalRepository.saveAll(List.of(
                    new Sucursales(null,
                            "Sucursal Central",
                            "Calle Norte 456"
                    ),

                    new Sucursales(null,
                            "Sucursal Norte",
                            "Calle Norte 456"
                    ),

                    new Sucursales(null,
                            "Sucursal Sur",
                            "Calle Norte 456"
                    )

            ));
            log.info("Sucursales de prueba cargadas correctamente");
        }
    }
}