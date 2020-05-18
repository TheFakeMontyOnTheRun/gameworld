package br.odb.gameworld;

public class ActiveItem extends Item {

	boolean active;

	protected ActiveItem(String name) {
		super(name);

		active = false;
	}

	public boolean isActive() {
		return active;
	}

	public ActiveItem setActive(boolean newState) {
		active = newState;
		return this;
	}

	public ActiveItem activate() {
		active = true;
		return this;
	}

	public ActiveItem deactivate() {
		active = false;
		return this;
	}

	@Override
	public String toString() {
		return (active ? "(active)" : "(inactive)") + super.toString();
	}

	public ActiveItem toggle() {
		active = !active;
		return this;
	}
}
