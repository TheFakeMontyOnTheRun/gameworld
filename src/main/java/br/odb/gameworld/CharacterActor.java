package br.odb.gameworld;

import java.util.HashMap;

import br.odb.gameworld.exceptions.InventoryManipulationException;
import br.odb.gameworld.exceptions.ItemActionNotSupportedException;
import br.odb.gameworld.exceptions.ItemNotFoundException;

public class CharacterActor implements Updatable {

	final HashMap<String, Item> items = new HashMap<String, Item>();
	String name;
	Kind kind;
	protected Location location;
	boolean alive = true;

	public void setIsAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isAlive() {
		return alive;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kind == null) ? 0 : kind.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CharacterActor)) {
			return false;
		}
		CharacterActor other = (CharacterActor) obj;
		if (kind == null) {
			if (other.kind != null) {
				return false;
			}
		} else if (!kind.equals(other.kind)) {
			return false;
		}
		if (location == null) {
			if (other.location != null) {
				return false;
			}
		} else if (!location.equals(other.location)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public void update(long milisseconds) {
		for (Item i : items.values()) {
			i.update(milisseconds);
		}
	}

	public Item[] getItems() {
		return items.values().toArray(new Item[items.size()]);
	}

	public CharacterActor(String name, Kind kind) {
		this.name = name;
		this.kind = kind;
	}

	public Location getLocation() {
		return location;
	}

	public String getName() {
		return name;
	}

	public void setLocation(Location l) {
		this.location = l;
	}

	public Item getItem(String itemName) throws ItemNotFoundException {

		if (!items.containsKey(itemName)) {
			throw new ItemNotFoundException();
		}

		return items.get(itemName);
	}

	public float getCargoWeight() {

		float sum = 0.0f;

		for (Item i : this.items.values()) {
			sum += i.weight;
		}

		return sum;
	}

	public void addItem(Item item) throws InventoryManipulationException {
		addItem(item.getName(), item);
	}

	public void addItem(String name, Item item) throws InventoryManipulationException {

		if (items.containsKey(name)) {
			throw new InventoryManipulationException();
		}

		item.carrier = this;
		items.put(name, item);
	}

	public void removeItem(Item item) {
		items.remove(item.getName());
	}

	public boolean isMovable() {
		return true;
	}

	public ActiveItem toggleItem(String name) throws ItemActionNotSupportedException, ItemNotFoundException {

		Item item = getItem(name);

		if (!(item instanceof ActiveItem)) {
			throw new ItemActionNotSupportedException();
		}

		((ActiveItem) item).toggle();

		return (ActiveItem) item;
	}

	public Item useItem(String entry) throws ItemNotFoundException, ItemActionNotSupportedException {

		Item item = getItem(entry);

		item.use(this);

		if (item.isDepleted()) {
			removeItem(item);
		}

		return item;
	}

	public boolean hasItem(String name) {
		return items.containsKey(name);
	}
}
