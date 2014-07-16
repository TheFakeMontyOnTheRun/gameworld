/**
 * 
 */
package br.odb.gameworld.exceptions;

/**
 * @author monty
 * 
 */
public class InventoryManipulationException extends Exception {

	private String message;



	public InventoryManipulationException(String msg) {
		this.message = msg;
	}
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return message;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = -4841606782777561480L;

}
