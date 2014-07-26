package br.odb.gameworld;

public class Kind {
	final String name;

	public Kind(String name) {
		this.name = name;
	}

	public String getJSONState() {
		return "'kind': '" + name + "'";
	}
}
