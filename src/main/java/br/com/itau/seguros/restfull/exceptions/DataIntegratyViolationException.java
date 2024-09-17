package br.com.itau.seguros.restfull.exceptions;

public class DataIntegratyViolationException extends  RuntimeException{

    public DataIntegratyViolationException(String message) {
        super(message);

    }
}
