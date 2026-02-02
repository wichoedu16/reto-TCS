package com.banco.cuenta.exception;

public class RecursoDuplicadoException extends RuntimeException {

    public RecursoDuplicadoException(String mensaje) {
        super(mensaje);
    }

    public RecursoDuplicadoException(String recurso, String campo, String valor) {
        super(String.format("%s con %s '%s' ya existe", recurso, campo, valor));
    }
}
