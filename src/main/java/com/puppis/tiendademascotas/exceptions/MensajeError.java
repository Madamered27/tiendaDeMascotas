package com.puppis.tiendademascotas.exceptions;


public class MensajeError {

    private final String error;
    private final String mensaje;
    private final Integer code;

    MensajeError(Exception exception, Integer code) {
        this.error = exception.getClass().getSimpleName();
        this.mensaje = exception.getMessage();
        this.code = code;
    }

}
