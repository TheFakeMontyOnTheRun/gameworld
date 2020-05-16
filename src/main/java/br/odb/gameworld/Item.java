package br.odb.gameworld;

import br.odb.gameworld.exceptions.ItemActionNotSupportedException;

public class Item implements Updatable {

	String description;
	private final String name;
	boolean depleted;
	private boolean pickable;
	public float weight;
	public Location location;
	protected CharacterActor carrier;
	
	public Item(String name) {
		this.name = name;
		pickable = true;
	}
	
	public String getDescription() {
		return description;
	}

	public String getUseItemSound() {
		return "click";
	}
	
	public String getDropSound() {
		return "drop";
	}
	
	public String getPickSound() {
		return "pick";
	}
	
	public String getUsedOnSound() {
		return "click";
	}
	
	@Override
	public String toString() {
		return getName() + " - " + description;
	}

	public Item setDescription(String description) {
		this.description = description;

		return this;
	}

	public void makeNoise() {

	}

	protected void setIsDepleted(boolean depleted) {
		this.depleted = depleted;
	}

	public String getName() {
		return name;
	}

	public void use(CharacterActor user) throws ItemActionNotSupportedException {
	}

	public boolean isDepleted() {
		return depleted;
	}

	public boolean isPickable() {
		return pickable;
	}

	public Item setPickable(boolean pickable) {
		this.pickable = pickable;
		return this;
	}

	public void wasUsedOn(Item item1) throws ItemActionNotSupportedException {
	}

	public void useWith(Item item1) throws ItemActionNotSupportedException {

		if (item1 == this) {
			throw new ItemActionNotSupportedException();
		}

		item1.wasUsedOn(this);
	}

	@Override
	public void update(long milisseconds) {
	}
}
