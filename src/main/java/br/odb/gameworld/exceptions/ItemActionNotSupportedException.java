/**
 * 
 */
package br.odb.gameworld.exceptions;

/**
 * @author monty
 * 
 */
public class ItemActionNotSupportedException extends Exception {

	private String message;



	public ItemActionNotSupportedException(String msg) {
		this.message = msg;
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Throwable#toString()
	 */
	@Override
	public String toString() {
		return this.message;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = -8145183890225876860L;

}
