package br.odb.gameworld;

import java.util.ArrayList;
import java.util.HashMap;

import br.odb.gameworld.exceptions.CharacterIsNotMovableException;
import br.odb.gameworld.exceptions.DoorActionException;
import br.odb.gameworld.exceptions.InvalidCharacterHandlingException;
import br.odb.gameworld.exceptions.InvalidLocationException;
import br.odb.gameworld.exceptions.InvalidSlotException;
import br.odb.gameworld.exceptions.ItemNotFoundException;

public class Place implements Updatable {

	final private HashMap<String, Location> locations;
	final private HashMap<String, CharacterActor> characters;

	public Place() {
		
		locations = new HashMap<String, Location>();
		characters = new HashMap<String, CharacterActor>();
	}
	
	public Location[] getLocations() {

		return this.locations.values().toArray(
				new Location[this.locations.values().size()]);
	}

	public CharacterActor[] getCharacters() {

		return this.characters.values().toArray(
				new CharacterActor[this.characters.values().size()]);
	}

	public Location addLocation(Location location) {
		return addLocation(location.getName(), location);
	}

	public void moveCharacter(String charName, String placeName)
			throws CharacterIsNotMovableException, InvalidLocationException,
			InvalidSlotException, DoorActionException {

		Direction d;
		CharacterActor character = getCharacter(charName);

		if (!character.isMovable()) {
			throw new CharacterIsNotMovableException();
		}

		Location origin = character.getLocation();
		Location destination = getLocation(placeName);

		if (origin == null || destination == null) {
			throw new InvalidLocationException();
		}

		d = origin.getConnectionDirectionForLocation(destination);

		if (d == null) {
			throw new InvalidSlotException();
		}

		Door door = origin.getDoor(d);
		door.openFor(character);

		origin.removeCharacter(character);
		destination.addCharacter(character);
	}

	Location addLocation(String name, Location location) {
		locations.put(name, location);
		return location;
	}

	protected CharacterActor addCharacter(String key,
			CharacterActor charInstance)
			throws InvalidCharacterHandlingException {

		if (characters.containsKey(key)) {
			throw new InvalidCharacterHandlingException();
		}

		characters.put(key, charInstance);

		return charInstance;
	}

	public CharacterActor addNewCharacter(String name, String kind)
			throws InvalidCharacterHandlingException {
		return addCharacter(name, new CharacterActor(name, new Kind(kind)));
	}

	public Location addNewLocation(String name) {
		Location location = new Location(name);
		addLocation(name, location);
		return location;
	}

	public Location getLocation(String name) throws InvalidLocationException {

		if (name == null || !locations.containsKey(name)) {
			throw new InvalidLocationException();
		}

		return locations.get(name);
	}

	public CharacterActor getCharacter(String name) {
		return characters.get(name);
	}

	public void moveCharacter(String name, Direction d)
			throws CharacterIsNotMovableException, InvalidLocationException,
			InvalidSlotException, DoorActionException {
		CharacterActor character = getCharacter(name);

		if (character.getLocation().getConnections()[d.ordinal()] == null) {
			throw new InvalidSlotException();
		}

		moveCharacter(name,
				character.getLocation().getConnections()[d.ordinal()].getName());
	}

	@Override
	public void update(long milisseconds) {

		for (Location l : locations.values()) {
			l.update(milisseconds);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((characters == null) ? 0 : characters.hashCode());
		result = prime * result
				+ ((locations == null) ? 0 : locations.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Place)) {
			return false;
		}
		Place other = (Place) obj;

		if (locations == null) {
			if (other.locations != null) {
				return false;
			}
		} else {

			for (Location l : locations.values()) {
				if (l != null && !other.locations.containsKey(l.getName())) {
					return false;
				}
			}

			for (Location l : other.locations.values()) {
				if (!locations.containsKey(l.getName())) {
					return false;
				}
			}

			for (Location l : locations.values()) {
				for (Location o : l.getConnections()) {
					if (o != null) {

						ArrayList<String> others = new ArrayList<String>();

						try {
							for (Location l2 : other.getLocation(l.getName()).getConnections()) {
								if (l2 != null) {
									others.add(l2.getName());
								}
							}
						} catch (InvalidLocationException impossible){ 
						}

						if (!others.contains(o.getName())) {

							return false;
						}
					}
				}
			}
		}

		return true;
	}

	public Item getItem(String name) throws ItemNotFoundException {

		for (Location l : locations.values()) {
			try {
				return l.getItem(name);
			} catch (ItemNotFoundException e) {
				// do nothing
			}
		}

		for (CharacterActor ca : characters.values()) {
			try {
				return ca.getItem(name);
			} catch (ItemNotFoundException e) {
				// do nothing
			}
		}
		throw new ItemNotFoundException();
	}

	public void makeConnection(Location place1, Direction d, Location place2) {
		
		place1.setConnected(d, place2);
		place2.setConnected( Direction.getOppositeDirection(d), place1);
		
	}
}
