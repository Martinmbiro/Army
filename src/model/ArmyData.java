package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class ArmyData {
	//Pre-populate with one Hero object for debugging
	public static ObservableList<Hero> mArmyData = FXCollections.observableArrayList(new Hero("Merlin","Mage",34,57,2));
}
