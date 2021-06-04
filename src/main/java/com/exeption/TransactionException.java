package com.exeption;

public class TransactionException extends RuntimeException{

    public TransactionException(Throwable cause) {
        super("Transaction was canceled", cause);
    }
}
