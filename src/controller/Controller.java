package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.ArmyData;
import model.Hero;

public class Controller implements Initializable {
	//Add Hero Pane
	@FXML
	private TextField nameField;
	@FXML
	private TextField strengthField;
	@FXML
	private TextField charismaField;
	@FXML
	private TextField damageField;
	@FXML
	private ComboBox<String> typeCombo;
	@FXML
	private Label infoLabel;
	@FXML
	private ImageView typeImageView;
	@FXML
	private TextArea heroesTextArea;
	//Army Pane
	@FXML
	private Label totalsLabel;
	@FXML
	private VBox armyVbox;	
	//Type selected from ComboBox
	private String selectedHeroType;
	
	//Observable StringProperty
	private StringProperty bigString = new SimpleStringProperty("");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//Populate ComboBox and Set EventHandler
		typeCombo.setItems(FXCollections.observableArrayList("Mage","Zombie","Fighter","Unicorn"));
		typeCombo.setOnAction(new HeroTypeComboBoxHandler());
		
		//Bind TextArea to bigString
		heroesTextArea.textProperty().bind(bigString);
		
		//Set OnChangedLister on the list
		ArmyData.mArmyData.addListener(new ListChangeListener<Hero>() {
			@Override
			public void onChanged(Change<? extends Hero> change) {
				String smallString = "";
				// TODO Auto-generated method stub
				for (Hero hero : change.getList()) {
					smallString += hero.toString();
				}
				bigString.set(smallString);
			}
		});
	}
	
	//Submitting New Hero Button Action
	public void onAddNewHero() {
		// TODO: String Variables gotten from textFields
		String nameStr = nameField.getText().toString();
		String strengthStr = strengthField.getText().toString();
		String charismaStr = charismaField.getText().toString();
		String damageStr = damageField.getText().toString();
		
		// Sanitize user input
		try {

			// TODO: When the hero type is not selected
			if (selectedHeroType == null) {
				throw new Exception("Hero type is not yet selected");
			}

			/* TODO: If one of the TextFields is empty, throw exception with
				error message: "At least one of the text fields is empty"*/
			if((nameStr.equals(null) || strengthStr.equals(null)) || (charismaStr.equals(null) || damageStr.equals(null))) {
				throw new Exception("At least one of the text fields is empty");
			}

			// TODO: 3. c) Loop through heroList to check for hero that has the same name; throw exception with
			// error message: "Hero existed!"
			for (Hero hero : ArmyData.mArmyData) {
				if(hero.getName().equals(nameStr)) {
					throw new Exception("Heroes cannot have similar names!");
				}
			}


			// TODO: Parse Strength, Charisma, and Damage to integers
			// Create 3 integers and convert the Strings
			int strengthInt = Integer.parseInt(strengthStr);
			int charismaInt = Integer.parseInt(charismaStr);
			int damageInt = Integer.parseInt(damageStr);
			
			// TODO: Check if Strength or Charisma is a negative number
			// if so, throw exception with error message "Both Strength and Charisma must be positive numbers"
			if(strengthInt < 0 || charismaInt < 0) {
				throw new Exception("Both Strength and Charisma must be positive numbers");
			}

			// TODO: Check if the sum of Strength and Charisma exceeds 100. 
			// If so, throw exception with error message "The sum of strength and charisma must be less or equal to 100"
			if((strengthInt+charismaInt) > 100) {
				throw new Exception("The sum of Strength and Charisma must be less or equal to 100");
			}
			
			// TODO: Create new Hero object and add to list
			ArmyData.mArmyData.add(new Hero(nameStr,
					selectedHeroType,
					strengthInt,
					charismaInt,
					damageInt));

			// TODO Set the Red Label to "Hero added successfully" and empty all TextFields
			infoLabel.setText("Hero added successfully!");
			nameField.clear();
			damageField.clear();
			charismaField.clear();
			strengthField.clear();

			// TODO 4. b) Call updateTextArea() to update heroes list
			// vvvvvv 4. b) vvvvvv (1 line)

			// ^^^^^^ 4. b) ^^^^^^

		} catch (NumberFormatException exception) {
			// set RED LABEL to "At least one of the text fields is in the incorrect format"
			infoLabel.setText("At least one of the text fields is in the incorrect format");
		} catch (Exception e) {
			// TODO: Set the value of the RED LABEL to exception message passed 
			infoLabel.setText(e.getMessage().toString());
		}

	}
	
	//Loading Heroes ButtonAction
	public void onLoadHeroesButton() {
		//TODO
	}
	
	//Getting Random Number ButtonAction
	public void genRandomNo() {
		//Generate Random Number in the range of 50...100 both inclusive
		int randomInt = (int)Math.floor(Math.random()*(100-50+1)+50);
		//Set this to damageField
		damageField.setText(""+randomInt);		
	}
	
	//Inner Class to handle ComboBox Actions
	private class HeroTypeComboBoxHandler implements EventHandler<ActionEvent>{
		@Override
		public void handle(ActionEvent event) {
			selectedHeroType = typeCombo.getSelectionModel().getSelectedItem().toString();
			if(selectedHeroType.equals("Mage")) {
				Image image = new Image(getClass().getResourceAsStream("/application/mage.png"));
				typeImageView.setImage(image);
			}else if(selectedHeroType.equals("Zombie")) {
				Image image = new Image(getClass().getResourceAsStream("/application/zombie.png"));
				typeImageView.setImage(image);
			}else if(selectedHeroType.equals("Unicorn")) {
				Image image = new Image(getClass().getResourceAsStream("/application/unicorn.png"));
				typeImageView.setImage(image);
			}else if(selectedHeroType.equals("Fighter")) {
				Image image = new Image(getClass().getResourceAsStream("/application/fighter.png"));
				typeImageView.setImage(image);
			}else {
				typeImageView.setImage(null);
			}
		}
	}
	
}
