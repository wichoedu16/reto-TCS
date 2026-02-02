package com.banco.cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ╔═══════════════════════════════════════════════════════════════════════════╗
 * ║  CLASE PRINCIPAL - Punto de entrada del Microservicio                      ║
 * ╠═══════════════════════════════════════════════════════════════════════════╣
 * ║  @SpringBootApplication combina 3 anotaciones:                             ║
 * ║  - @Configuration: Indica que es una clase de configuración                ║
 * ║  - @EnableAutoConfiguration: Habilita configuración automática             ║
 * ║  - @ComponentScan: Escanea componentes en este paquete y subpaquetes       ║
 * ╚═══════════════════════════════════════════════════════════════════════════╝
 */
@SpringBootApplication
public class MsClientePersonaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsClientePersonaApplication.class, args);
    }
}