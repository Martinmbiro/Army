package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*This class holds the list of Heroes to be accessed from whatever class
 * Made abstract so that it can never be instantiated, but rather accessed
 * Referring to the MVC pattern, this would be the MODEL*/
public abstract class ArmyData {
	//Pre-populate with one Hero object for debugging
	public static ObservableList<Hero> mArmyData = FXCollections.observableArrayList(new Hero("Merlin","Mage",34,57,2));
	//new Hero("Merlin","Mage",34,57,2)
}
