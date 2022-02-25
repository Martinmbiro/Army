package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
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
	//Total Values for ArmyPane initialized to 0
	private int totalCharisma = 0, totalStrength = 0, totalDamage = 0;
	
	//Observable StringProperty set to empty String at first
	private StringProperty observableString = new SimpleStringProperty("");

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		//Bind TextArea to observableString
		heroesTextArea.textProperty().bind(observableString);
		
		/*The next block of code runs Asynchronously with the help of the CompletableFuture API*/ 
		CompletableFuture.runAsync(new Runnable() {
			@Override
			public void run() {
				//Populate ComboBox and Set EventHandler
				typeCombo.setItems(FXCollections.observableArrayList("Mage","Zombie","Fighter","Unicorn"));
				typeCombo.setOnAction(new HeroTypeComboBoxHandler());
				
				/*If there's existing ArmyData, set its value to observableString field,
				  for it to reflect on the armyTextArea*/
				if(ArmyData.mArmyData.size() > 0) {
					String smallString = "";
					for (Hero hero : ArmyData.mArmyData) {
						smallString += hero.toString();
					}
					observableString.set(smallString);
				}								
			}
		});
		
		/*Set OnChangedLister to ArmyData.mArmyData list, 
		 * and define what happens whenever a new Hero object is added
		 */
		ArmyData.mArmyData.addListener(new ListChangeListener<Hero>() {
			@Override
			public void onChanged(Change<? extends Hero> change) {
				String smallString = "";
				// Get the new list, and make Hero info out of it
				for (Hero hero : change.getList()) {
					smallString += hero.toString();
				}
				observableString.set(smallString);
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

			// TODO: Loop through heroList to check for hero that has the same name; throw exception with
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
			
			/* TODO: Create new Hero object and add to the Model Asynchronously*/
			CompletableFuture.runAsync(new Runnable() {				
				@Override
				public void run() {
					ArmyData.mArmyData.add(new Hero(nameStr,selectedHeroType,strengthInt,charismaInt,damageInt));
				}
			});

			// TODO Set the Red Label to "Hero added successfully" and empty all TextFields
			infoLabel.setText("Hero added successfully!");
			nameField.clear();
			damageField.clear();
			charismaField.clear();
			strengthField.clear();

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
		//TODO Clear VBox
		armyVbox.getChildren().clear();
		
		//TODO set the totals to 0 and empty totalsLabel
		totalCharisma=0;
		totalStrength=0;
		totalDamage=0;
		totalsLabel.setText("");
		
		//TODO Loop through list of heroes
		for(Hero hero : ArmyData.mArmyData) {
			//Create a String and make CheckBox using the String
			CheckBox holder = new CheckBox(hero.toString());
			//Still in loop, bind the newly created CheckBox to CheckBoxHandler
			holder.setOnAction(new CheckBoxHandler(hero));
			//And add this CheckBox as a child of VBox
			armyVbox.getChildren().add(holder);
		}
	}
	
	//Getting Random Number ButtonAction
	public void genRandomNo() {
		//Generate Random Number in the range of 50...100 both inclusive
		int randomInt = (int)Math.floor(Math.random()*(100-50+1)+50);
		//Set this to damageField
		damageField.setText(""+randomInt);		
	}
	
	//Class to handle events on ComboBox
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
	
	//Class to handle events on CheckBoxes created
	private class CheckBoxHandler implements EventHandler<ActionEvent>{
		//As a field of this class:
		Hero hero;
		
		/*Make the Constructor of this class such that a Hero Object is passed, so that it can be accessed later*/
		private CheckBoxHandler(Hero hero) {
			this.hero = hero;
		}
		
		@Override
		public void handle(ActionEvent event) {
			// TODO: Use event.getSource() to get the CheckBox that triggered the event, cast it to CheckBox
			CheckBox holder = (CheckBox) event.getSource();

			// TODO: If the CheckBox was selected, add the current hero scores to totalStrength, 
			// 	totalCharisma, and totalDamge. Otherwise, subtract the current hero scores
			if(holder.isSelected()) {
				//System.out.println(this.hero);
				totalCharisma += this.hero.getCharisma();
				totalStrength += this.hero.getStrength();
				totalDamage += this.hero.getDamage();
			}else {
				//System.out.println("Unselected");
				totalCharisma -= this.hero.getCharisma();
				totalStrength -= this.hero.getStrength();
				totalDamage -= this.hero.getDamage();
			}

			// TODO: Set the totalsLabel to total Values of strength, charisma and damage
			totalsLabel.setText("Total Strength: " + totalStrength + "\t\tTotal Charisma: " 
								+ totalCharisma + "\t\tTotal Damage: " + totalDamage);
		}
		
	}
}
