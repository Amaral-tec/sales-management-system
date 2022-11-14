package br.com.amaral.exceptions;

/**
 *
 * @author leandro.amaral
 */
public class TypeElementNotKnownException extends Exception {

	private static final long serialVersionUID = -2268140970978666251L;

	public TypeElementNotKnownException(String msg) {
		this(msg, null);
	}

	public TypeElementNotKnownException(String msg, Throwable e) {
		super(msg, e);
	}
}
