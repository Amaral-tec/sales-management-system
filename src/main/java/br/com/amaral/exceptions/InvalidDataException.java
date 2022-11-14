package br.com.amaral.exceptions;

/**
 * @author leandro.amaral
 *
 */
public class InvalidDataException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidDataException(String msg) {
		this(msg, null);
	}

	public InvalidDataException(String msg, Throwable e) {
		super(msg, e);
	}
}
