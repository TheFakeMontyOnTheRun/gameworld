package br.odb.gameworld;

import br.odb.utils.Utils;

public enum Direction {

	N("N", "North"), E("E", "East"), S("S", "South"), W("W", "West"), FLOOR(
			"D", "Down"), CEILING("U", "Up");

	final public String prettyName;
	final public String simpleName;

	private Direction(String simpleName, String name) {
		prettyName = name;
		this.simpleName = simpleName;
	}

	@Override
	public String toString() {
		return prettyName;
	}
	
	public static Direction getOppositeDirection( Direction d) {
		
		return Direction.values()[ Utils.getOppositeDirection( d.ordinal() ) ];
	}
	
	

	public static Direction getDirectionForPrettyName(String prettyName) {

		for (Direction d : Direction.values()) {
			if (d.prettyName.toUpperCase().equals(prettyName.toUpperCase())) {
				return d;
			}
		}

		return null;
	}

	public static Direction getDirectionForSimpleName(String simpleName) {

		for (Direction d : Direction.values()) {
			if (d.simpleName.equals(simpleName.toUpperCase())) {
				return d;
			}
		}

		return null;
	}

	public static Direction getOpositeFor(Direction d) {
		return Direction.values()[Utils.getOppositeDirection(d.ordinal())];
	}
}