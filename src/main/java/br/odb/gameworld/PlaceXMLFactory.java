/**
 * 
 */
package br.odb.gameworld;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.odb.gameworld.exceptions.InvalidLocationException;
import br.odb.utils.Direction;

/**
 * @author monty
 * 
 */
//TODO implement properly this clusterfuck.
public class PlaceXMLFactory {

	private PlaceXMLFactory() {

	}

	public static Place buildPlaceFromXML(InputStream is, Place place) {

		Place toReturn;

		if (place == null) {
			toReturn = new Place();
		} else {
			toReturn = place;
		}

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(is);
			doc.getDocumentElement().normalize();
			NodeList nodeLst;
			nodeLst = doc.getElementsByTagName("*");

			for (int s = 0; s < nodeLst.getLength(); s++) {

				Node fstNode = nodeLst.item(s);

				if (fstNode.getNodeType() == Node.ELEMENT_NODE
						&& fstNode.getNodeName().equalsIgnoreCase("place")) {

					Element fstElmnt = (Element) fstNode;
					toReturn = parsePlace(fstElmnt, toReturn);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return toReturn;
	}

	private static Place parsePlace(Element fstElmnt, Place place) {

		String id = null;
		Place toReturn = place;

		NamedNodeMap properties = fstElmnt.getAttributes();

		for (int j = 0; j < properties.getLength(); j++) {

			Node property = properties.item(j);
			String name = property.getNodeName();

			if (name.equalsIgnoreCase("id")) {
				id = property.getNodeValue();
			}
		}

		NodeList nodeLst = fstElmnt.getChildNodes();

		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode.getNodeType() == Node.ELEMENT_NODE
					&& fstNode.getNodeName().equalsIgnoreCase("location")) {

				id = fstNode.getAttributes().getNamedItem("id")
						.getTextContent();

				Location location = place.addNewLocation(id);
				toReturn.addLocation(location);
			}
		}

		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode.getNodeType() == Node.ELEMENT_NODE
					&& fstNode.getNodeName().equalsIgnoreCase("location")) {

				id = fstNode.getAttributes().getNamedItem("id")
						.getTextContent();

				try {
					parseLocation(fstNode, toReturn.getLocation(id), toReturn);
				} catch (InvalidLocationException e) {
					e.printStackTrace();
				}
			}
		}
		return toReturn;
	}

	private static Location parseLocation(Node node, Location location,
			Place place) {

		String direction = null;
		Location otherLocation;

		// NamedNodeMap properties = node.getAttributes();
		//
		// for (int j = 0; j < properties.getLength(); j++) {
		//
		// Node property = properties.item(j);
		// String name = property.getNodeName();
		// }

		NodeList nodeLst = node.getChildNodes();

		for (int s = 0; s < nodeLst.getLength(); s++) {

			Node fstNode = nodeLst.item(s);

			if (fstNode.getNodeType() == Node.ELEMENT_NODE
					&& fstNode.getNodeName().equalsIgnoreCase("description")) {

				location.setDescription(fstNode.getTextContent());
			}

			// if (fstNode.getNodeType() == Node.ELEMENT_NODE
			// && fstNode.getNodeName().equalsIgnoreCase("actor")) {
			//
			// String className = "CharacterActor";
			//
			// if ( fstNode.getAttributes().getNamedItem( "class" ) != null ) {
			// className = fstNode.getAttributes().getNamedItem( "class"
			// ).getTextContent();
			// }
			//
			// try {
			//
			// Class classz;
			// classz = Class.forName( className );
			// CharacterActor actor = (CharacterActor)classz.newInstance();
			//
			// if ( fstNode.getAttributes().getNamedItem( "name" ) != null ) {
			// actor.name = fstNode.getAttributes().getNamedItem( "name"
			// ).getTextContent();
			// }
			//
			// location.addCharacter( actor );
			//
			// } catch (ClassNotFoundException e) {
			// // 
			// e.printStackTrace();
			// } catch (InstantiationException e) {
			// // 
			// e.printStackTrace();
			// } catch (IllegalAccessException e) {
			// // 
			// e.printStackTrace();
			// }
			// }
			//
			// if (fstNode.getNodeType() == Node.ELEMENT_NODE
			// && fstNode.getNodeName().equalsIgnoreCase("item")) {
			//
			// String className = "Item";
			//
			// if ( fstNode.getAttributes().getNamedItem( "class" ) != null ) {
			// className = fstNode.getAttributes().getNamedItem( "class"
			// ).getTextContent();
			// }
			//
			// try {
			//
			// Class classz;
			// classz = Class.forName( className );
			// CharacterActor actor = (CharacterActor)classz.newInstance();
			//
			// if ( fstNode.getAttributes().getNamedItem( "name" ) != null ) {
			// actor.name = fstNode.getAttributes().getNamedItem( "name"
			// ).getTextContent();
			// }
			//
			// location.addCharacter( actor );
			//
			// } catch (ClassNotFoundException e) {
			// // 
			// e.printStackTrace();
			// } catch (InstantiationException e) {
			// // 
			// e.printStackTrace();
			// } catch (IllegalAccessException e) {
			// // 
			// e.printStackTrace();
			// }
			// }

			if (fstNode.getNodeType() == Node.ELEMENT_NODE
					&& fstNode.getNodeName().equalsIgnoreCase("link")) {

				try {
					otherLocation = place.getLocation(fstNode.getAttributes()
							.getNamedItem("location").getTextContent());
					direction = fstNode.getAttributes()
							.getNamedItem("direction").getTextContent();

					location.setConnected(Direction.valueOf(direction),
							otherLocation);

					otherLocation.setConnected(Direction
							.getOpositeFor(Direction.valueOf(direction)),
							location);

				} catch (DOMException e) {
					// 
					e.printStackTrace();
				} catch (InvalidLocationException e) {
					// 
					e.printStackTrace();
				}
			}
		}

		return location;
	}
}
