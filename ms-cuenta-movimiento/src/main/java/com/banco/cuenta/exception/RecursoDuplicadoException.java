package com.banco.cuenta.exception;

/**
 * ╔═══════════════════════════════════════════════════════════════════════════╗
 * ║  EXCEPCIÓN: Recurso Duplicado                                              ║
 * ╠═══════════════════════════════════════════════════════════════════════════╣
 * ║  Se lanza cuando se intenta crear un recurso que viola unicidad            ║
 * ║  Ejemplo: crear cliente con identificación que ya existe                   ║
 * ╚═══════════════════════════════════════════════════════════════════════════╝
 */
public class RecursoDuplicadoException extends RuntimeException {

    public RecursoDuplicadoException(String mensaje) {
        super(mensaje);
    }

    public RecursoDuplicadoException(String recurso, String campo, String valor) {
        super(String.format("%s con %s '%s' ya existe", recurso, campo, valor));
    }
}