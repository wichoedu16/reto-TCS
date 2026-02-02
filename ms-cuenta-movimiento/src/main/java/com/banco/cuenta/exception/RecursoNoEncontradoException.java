package com.banco.cuenta.exception;

public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    public RecursoNoEncontradoException(String recurso, Long id) {
        super(String.format("%s con id %d no encontrado", recurso, id));
    }

    public RecursoNoEncontradoException(String recurso, String campo, String valor) {
        super(String.format("%s con %s '%s' no encontrado", recurso, campo, valor));
    }
}
