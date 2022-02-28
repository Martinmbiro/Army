package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*This class holds the list of Heroes to be accessed from whatever class
 * Made abstract so that it can never be instantiated, but rather accessed
 * Going by the MVC pattern, this would be the MODEL*/
public abstract class ArmyData {
	//Pre-populate with 5 Hero objects for debugging
	public static ObservableList<Hero> mArmyData = FXCollections.observableArrayList(
															new Hero("Merlin","Mage",96,3,50),
															new Hero("Drew","Fighter",86,12,51),
															new Hero("Asha","Unicorn",85,13,58),
															new Hero("Bryson","Zombie",10,88,51),
															new Hero("Morgana","Mage",90,2,82));
}
