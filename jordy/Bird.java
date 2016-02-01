package jordy;

import jordy.Enums.*;
import java.util.ArrayList;
import java.util.List;


public class Bird extends Entity {

	//shared attributes of all birds

	private static final int BREEDING_AGE = 5;
	private static final int MAX_AGE = 50;

	// individual attributes of birds

	private BirdGender gender;
	private int age;
	private Color color;
	private int fitness;
	private LifeGoals lifegoal;
	private int sexAppeal;
	private List<String> parentsList= new ArrayList<>();
	private List<String> chicksList= new ArrayList<>();
	private List<String> history = new ArrayList<>();
	private boolean ill = false;

	/**
	 * Creates a specific kind of entity
	 * 
	 * Inherited variables:
	 * @param x					X coordinate within the aviary
	 * @param y					Y coordinate within the aviary
	 * @param name				Name of the Bird
	 * @param type				Type of Entity, in this instance is BIRD
	 * @param lives				Boolean defining if an entity is alive or not
	 * 
	 * Class-specific variables:
	 * @param gender			Male or female bird
	 * @param age				Integer defining the age of the bird
	 * @param color				Color of the bird
	 * @param fitness			Fitness level of the bird
	 * @param lifegoal			The bird-specific lifegoal
	 * @param sexAppeal			How attractive a bird is
	 * @param parentsList 		Which partner a bird is breeding with
	 * @param chicksList		How many chicks a bird has had during its life
	 * 
	 */	

	public Bird(int x, int y, String name, boolean lives, EntityType type, boolean movable, BirdGender gender, int age,
			Color color, int fitness, LifeGoals lifegoal, int sexAppeal, List<String> parentsList) {

		super(x, y, name, type, true, true);

		this.gender = gender;
		this.age = age;
		this.color = color;
		this.fitness = fitness;
		this.lifegoal = lifegoal;
		this.sexAppeal = sexAppeal;
		this.parentsList = parentsList;
		this.chicksList = chicksList;
	}

	// -- CHECKERS and INCREASE/DECREASE -- // 

	/**
	 * Checks if a bird is mature enough to breed
	 */
	boolean canBreed() {
		if (age >= BREEDING_AGE && getFitness() >= 2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Increments a bird's age with every call of the method
	 * Also checks for birds that exceed the maximum age
	 */
	public void incrementAge() {
		age++;
		if (age > MAX_AGE) {
			die();
		}
	}

	/**
	 * This method makes birds become stronger
	 * It will be called when a bird is on a food dispenser tile
	 * @param b		this boolean is linked to a bird's lifegoal
	 */
	public void incrementFitness(boolean b) {
		if (b) {
			fitness += 2;
			System.out.println(
					historyAdd(getName() + " has eaten grain and receives +2 fitness " + "for being a glutton"));
		} else {
			fitness++;
			System.out.println(historyAdd(getName() + " has eaten grain and received +1 fitness"));
		}
	}

	/**
	 * This method is the inverse of the previous
	 * + makes sure a bird's fitness cannot be under 0
	 */
	public void decreaseFitness() {
		if (fitness > 0) {
			fitness--;
			if (fitness == 0) {
				die();
			}
		}
	}

	/**
	 * This method makes birds become sexier
	 * @param b this boolean is linked to a bird's lifegoal
	 */
	public void incrementSexAppeal(boolean b) {
		if (b) {
			sexAppeal += 2;
			System.out.println(historyAdd(
					getName() + " has washed his/her feathers and receives +2 sex appeal " + "for being a swinger"));
		} else {
			sexAppeal++;
			System.out.println(historyAdd(getName() + " has washed his/her feathers and receives +1 sex appeal"));
		}
	}

	/**
	 * This method is the inverse of the previous
	 * + makes sure a bird's sex appeal cannot be under 0
	 */
	public void decreaseSexAppeal() {
		if (sexAppeal > 0) {
			sexAppeal--;
			System.out.println(historyAdd(getName() + " has consequently lost -1 sex appeal"));
		}
	}

	/**
	 * This general method makes an entity die variable-wise
	 * It is however not yet removed from the aviary
	 * @see Aviary.removeEntityFromAviary
	 */
	public void die() {
		setLives(false);
	}

	/**
	 * This method is vital for giving a textual representation of the actions
	 * of each bird given a simulated history
	 * 
	 * @param ev
	 * @return
	 */
	public String historyAdd(String ev) {
		history.add(ev);
		return ev;
	}

	/**
	 * The following toString method gives a textual representation of the properties
	 * of the inspected individual bird.
	 */
	@Override
	public String toString() {

		StringBuilder properties = new StringBuilder();
		properties.append("\n" +"Properties: \n");
		properties.append("Name: " + getName() + "\n");
		properties.append("Location: " + getX() +"," + getY() + "\n");
		properties.append("Alive: " + "very alive" +"\n");
		properties.append("Age: " + age + "\n");
		properties.append("Color: " +"a very bright " + color.toString().toLowerCase() +"\n");
		properties.append("Gender: " + gender.toString().toLowerCase() + "\n");
		properties.append("Fitness level: " + fitness + "\n");
		properties.append("sexAppeal: " + sexAppeal + "\n");
		properties.append("Lifegoal: " + lifegoal.toString().toLowerCase() + "\n");
		if(parentsList != null) { 
			properties.append("Parents: ");
			for(String parent: parentsList) {
				properties.append("-" + parent + "\t");
			}
		}
		if(chicksList != null) {
			properties.append("\n" +"Children: ");
			for(String chick: chicksList) {
				properties.append("-" + chick + "\n");
			}
		}
		return properties.toString();
	}

	/* Getters for the variables of each bird */

	public List<String> getHistory() {
		return history;
	}

	public BirdGender getGender() {
		return gender;
	}

	public Color getColor() {
		return color;
	}

	public boolean isIll() {
		return ill;
	}

	public int getAge() {
		return age;
	}

	public int getFitness() {
		return fitness;
	}

	public LifeGoals getLifegoal() {
		return lifegoal;
	}

	public int getSexAppeal() {
		return sexAppeal;
	}

	/* Setters for the variables of each entity */

	public List<String> getParentsList() {
		return parentsList;
	}

	public void setParentsList(List<String> parentsList) {
		this.parentsList = parentsList;
	}

	public List<String> getChicksList() {
		return chicksList;
	}

	public void setIll(boolean ill) {
		this.ill = ill;
	}
}
