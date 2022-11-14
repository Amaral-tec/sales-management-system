package br.com.amaral.exceptions;

/**
 * @author leandro.amaral
 *
 */
public class MoreThanOneRecordException extends Exception {

    private static final long serialVersionUID = -7509649433607067138L;

    public MoreThanOneRecordException(String msg) {
        super(msg);
    }
}