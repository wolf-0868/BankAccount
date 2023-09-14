package com.example.bankaccount.exceptions;

public class BankAccountException extends Exception {

    private static final long serialVersionUID = 9134530464394453905L;

    public BankAccountException() {
        super();
    }

    public BankAccountException(String aMessage, Throwable aCause) {
        super(aMessage, aCause);
    }

    public BankAccountException(String aMessage) {
        super(aMessage);
    }

    public BankAccountException(String aFormat, Object... aArgs) {
        super(String.format(aFormat, aArgs));
    }

    public BankAccountException(Throwable aCause) {
        super(aCause);
    }

}
