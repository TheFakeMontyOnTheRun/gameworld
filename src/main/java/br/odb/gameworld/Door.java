package br.odb.gameworld;

import br.odb.gameworld.exceptions.DoorActionException;

public class Door implements Updatable {

	int timeoutForClosing;
	boolean open;
	
	public String getJSONState() {
		String toReturn = "'door' : {";
		toReturn += "'timeoutForClosing': '" + timeoutForClosing;
		toReturn += "','open': '" + open;
		toReturn += "'}";
		return toReturn;
	}

	public boolean isOpen() {
		return open;
	}

	public void doClose() {
		open = false;
	}

	public void doOpen() {
		open = true;
	}

	public Door() {
		open = false;

	}

	public boolean willOpenFor(CharacterActor character) {
		return true;
	}

	public void openFor(CharacterActor character) throws DoorActionException {

		doOpen();
	}

	@Override
	public void update(long milisseconds) {
		//TODO check for the idea of implementing the auto-closing mechanism here
	}

	
}
