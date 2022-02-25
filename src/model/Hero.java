package model;

//This class represents a single Hero Object with attributes and methods
public class Hero {
	private String name;
	private String type;
	private int strength;
	private int charisma;
	private int damage;
	
	/**
	 * 
	 * @param _name Name
	 * @param _type	Type (Mage, Zombie, Fighter or Unicorn)
	 * @param _strength Strength
	 * @param _charisma Charisma
	 * @param _damage Damage
	 */
	public Hero(String _name, String _type, int _strength, int _charisma, int _damage) {
		this.name = _name;
		this.type = _type;
		this.strength = _strength;
		this.charisma = _charisma;
		this.damage = _damage;
	}
	
	public Hero() {
		this("?", "?", 0, 0, 0);
	}
	
	//Getters for this class:
	public String getName() {
		return name;
	}


	public int getStrength() {
		return strength;
	}


	public int getCharisma() {
		return charisma;
	}

	public double getDamage() {
		return damage;
	}
	
	@Override
	public String toString() {
		return String.format("Hero Name: %s\t\t\t(%s)\nStrength: %d\tCharisma: %d\tDamage: %d\n\n", this.name, this.type, this.strength, this.charisma, this.damage);		
	}
}
