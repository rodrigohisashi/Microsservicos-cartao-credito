package io.github.rodrigotakeuti.msavaliadorcredito.exceptions;

import lombok.Getter;

public class ErroComunicacaoMicrosservicesException extends Exception {

    @Getter
    private Integer status;
    public ErroComunicacaoMicrosservicesException(String msg, Integer status) {
        super(msg);
        this.status = status;
    }
}
