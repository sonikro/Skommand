package br.com.sonikro.exceptions;

public class SkommandException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SkommandException(String message)
	{
		super(message);
	}
	
	public SkommandException(Exception e)
	{
		super(e);
	}
}
