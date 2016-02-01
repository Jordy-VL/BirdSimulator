package jordy;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import jordy.Enums.*;

import jordy.SubClasses.*;

public class MainFunctionality {

	/**
	 * The following booleans check for input success. They require a specific
	 * identifier, and when given, return true. After each call they are reset
	 * again to false.
	 * 
	 * @param randomCreation
	 *            this boolean governs if the world is created with random or 
	 *            pre-defined entities (and variables)
	 * 
	 */
	private static boolean entitySuccess; // Resets to false
	private static boolean intSuccess; // Resets to false
	private static boolean stringSuccess; // Resets to false
	private boolean randomCreation = true; 

	/**
	 * Here we define a general scanner for user input
	 */
	private static final Scanner scan = new Scanner(System.in);

	/**
	 * Here we define a global integer variable for turn(-tracking)
	 * This variable will keep score of all asked turns + will be used to build a header
	 */
	private int turn = 1;

	/**
	 * Here we define a variable taking care of the amount of turns a user want
	 * to see It needs to be reset to the default 0 at the end of a loop.
	 */
	private int turnsAmount; // Resets to 0

	/**
	 * Here we define a variable keeping track of the user input. More
	 * specifically, it keeps track of how many times a user has entered info in
	 * the console menu.
	 */
	private static int userInputSteps;

	/**
	 * Here we initially define the size variables for the aviary in question
	 */
	private static int aviaryHeight = 10;
	private static int aviaryWidth = 10;

	/**
	 * This step is highly necessary: Here we create a specific world and store
	 * it in a variable.
	 */
	private static Aviary cage = new Aviary(aviaryWidth, aviaryHeight);

	/*********************************************
	 * 	   \ START of Simulation in console/ 	 *	
	 *********************************************/

	/**
	 * Here we ask the user how many turns they would like to "see" in the
	 * future. This sets the amount of turns for further actions.
	 */
	public void amountOfTurns() {
		turnsAmount = 0;
		intSuccess = false;

		while (intSuccess == false) {
			try {
				turnsAmount = Integer.parseInt(scan.nextLine());
				if (turnsAmount <= 0) {
					throw new NumberFormatException();
				} else {
					turnsAmount++;
					intSuccess = true;
					System.out.print("\n");
				}
			} catch (NumberFormatException error) {
				System.err.println(
						"Invalid input!\n" + "Please enter a valid integer " + "The integer should be higher than 0.");
			}
		}
	}

	/**
	 * Asks a user if they want to get back to the original menu.
	 */
	public void backToMainMenu() {
		stringSuccess = false;

		System.out.print("\n Do you want to return to the main screen? (y/n)");

		while (stringSuccess == false) {
			try {
				String yesNo = scan.nextLine();
				/*
				 * Regular Expression: ^ asserts a "starts with" position (?i)
				 * asserts a case-insensitive match of the next pattern(s) 1st
				 * possible match: y(?:es)? y asserts a literal match for the
				 * string "y" (?:es)? Non-capturing group, optional
				 * string-elaboration Operator: ? Between zero and one time es
				 * asserts a literal match for the string "es" 2nd possible
				 * match: no?$ n asserts a literal match for the string "n" o?
				 * asserts a literal match for the string "o", but optional
				 * Operator: ? Between zero and one time $ asserts a "ends with"
				 * position
				 */
				if (yesNo.matches("^(?i)y(?:es)?|no?$")) {
					stringSuccess = true;
					System.out.print("\n");

					if (yesNo.equalsIgnoreCase("y") || yesNo.equalsIgnoreCase("yes")) {
						initialMenu();
					} else {
						System.err.println("You are exiting the BirdSimulator, " + "Enjoy your day!");
						System.exit(0);
					}
				} else {
					throw new InputMismatchException();
				}
			} catch (InputMismatchException error) {
				System.err.println("This is not a valid command. " + "Please anwer with \"yes\" or \"no\".");
			}
		}
	}

	/**
	 * Shows the user an overview of all entities in the aviary.
	 * Different types are distinguished.
	 */
	public void getEntityOverview() {
		for (Entity entity : cage.getEntities()) {
			if (entity instanceof Bird) {
				System.out.println("The bird, " + ((Bird) entity).getName() + ", is currently at the coordinates "
						+ entity.getX() + "," + entity.getY());
			} else if (entity instanceof Mouse) {
				System.out.println(
						"The mouse" + " is currently at the coordinates " + entity.getX() + "," + entity.getY());
			} else if (entity instanceof Rat) {
				System.out
				.println("The rat" + " is currently at the coordinates " + entity.getX() + "," + entity.getY());
			} else {
				System.out.println("The non_living entity " + entity.getName() + " is still at the coordinates "
						+ entity.getX() + "," + entity.getY());
			}
		}
		backToMainMenu();
	}

	/*
	 * Additional printfunction
	 * Print all entities 
	 * 1. Focus on tiles, and its contents 
	 * 2. Focus on entities, and their coordinates
	 */
	public void overviewPrint() {
		System.out.println("Looping the tiles");
		// Loop all coordinates (x and y)
		for (int x = 0; x < cage.getWidth() + 1; x++) {
			for (int y = 0; y < cage.getHeight() + 1; y++) {
				// Put entities on this tile in variable to make things easier
				List<Entity> entitiesOnTile = cage.getOnCoordinates(x, y);

				// If a tile is NOT empty:
				if (entitiesOnTile.size() != 0) {
					System.out.print("The element(s) on tile ");
					System.out.print(x + "," + y);

					// Just a check to print a comma when there are more than 1
					// entities
					if (entitiesOnTile.size() > 1) {

						System.out.print(" are");

						for (int i = 0; i < entitiesOnTile.size(); i++) {
							if (i > 0) {
								System.out.print(",");
							}
							System.out.print(" " + entitiesOnTile.get(i).getName());
							if (i == entitiesOnTile.size() - 1) {
								System.out.println();
							}
						}
					} else {
						System.out.print(" is");
						System.out.println(" " + entitiesOnTile.get(0).getName());
					}
				}
			}
		}

		System.out.println("\nLooping the entities");

		// Loop all entities
		for (Entity entity : cage.getEntities()) {
			System.out.print(entity.getName());
			System.out.print(" is on coordinates ");
			System.out.println(entity.getX() + "," + entity.getY());
		}
	}

	/**
	 * These constructors create entities. They are automatically added to the world.
	 * The user has the choice between a pre-defined or a random setup.
	 */
	public void initialSetup(boolean random) {

		if (!random) {

			// Birds to create + variables
			Bird bird1 = new Bird(4, 2, "Birdie1", true, EntityType.BIRD, true, BirdGender.MALE, 10, Color.GREEN, 4,
					LifeGoals.FOOD, 6, null);
			Bird bird2 = new Bird(5, 5, "Birdie2", true, EntityType.BIRD, true, BirdGender.FEMALE, 6, Color.BLUE, 2,
					LifeGoals.MANY_CHILDREN, 5, null);
			Bird bird3 = new Bird(4, 5, "Birdie3", true, EntityType.BIRD, true, BirdGender.MALE, 8, Color.YELLOW, 4,
					LifeGoals.FOOD, 3, null);
			Bird bird4 = new Bird(2, 4, "Birdie4", true, EntityType.BIRD, true, BirdGender.FEMALE, 15, Color.GREY, 6,
					LifeGoals.MANY_PARTNERS, 2, null);
			Bird bird5 = new Bird(3, 6, "Birdie5", true, EntityType.BIRD, true, BirdGender.MALE, 11, Color.GREEN, 4,
					LifeGoals.MANY_PARTNERS, 8, null);
			Bird bird6 = new Bird(6, 3, "Birdie6", true, EntityType.BIRD, true, BirdGender.FEMALE, 9, Color.BLUE, 3,
					LifeGoals.MANY_CHILDREN, 11, null);

			// Rodents to create + variables
			Rat theRat = new Rat(5, 0, "the fat rat", EntityType.RAT, 10);
			Mouse theMouse = new Mouse(0, 0, "the ill mousie", EntityType.MOUSE);

			// Non_Living entities to create + variables
			Bird_Bath theBath = new Bird_Bath(5, 3, "a bird bath", EntityType.BIRD_BATH);
			Rotten_Branch rot1 = new Rotten_Branch(1, 4, "a rotten branch", EntityType.ROTTEN_BRANCH);
			Rotten_Branch rot2 = new Rotten_Branch(6, 6, "a rotten branch", EntityType.ROTTEN_BRANCH);
			Rotten_Branch rot3 = new Rotten_Branch(0, 6, "a rotten branch", EntityType.ROTTEN_BRANCH);
			Food_Dispenser mmm1 = new Food_Dispenser(3, 3, "a Bird feeder", EntityType.FOOD_DISPENSER);
			Food_Dispenser mmm2 = new Food_Dispenser(0, 3, "a Bird feeder", EntityType.FOOD_DISPENSER);

			cage.addEntity(bird1);
			cage.addEntity(bird2);
			cage.addEntity(bird3);
			cage.addEntity(bird4);
			cage.addEntity(bird5);
			cage.addEntity(bird6);
			cage.addEntity(theRat);
			cage.addEntity(theMouse);
			cage.addEntity(theBath);
			cage.addEntity(rot1);
			cage.addEntity(rot2);
			cage.addEntity(rot3);
			cage.addEntity(mmm1);
			cage.addEntity(mmm2);

		} else {

			for (int i = 0; i < Randomizer.randomBetween(6, 10); ++i) {
				Bird bird = new Bird(Randomizer.randomBetween(0, cage.getWidth() - 1),
						Randomizer.randomBetween(0, cage.getHeight() - 1), "Birdie" + i, true, EntityType.BIRD, true,
						BirdGender.getRandom(), Randomizer.randomBetween(1, 10), Color.getRandom(),
						Randomizer.randomBetween(2, 10), LifeGoals.getRandom(), Randomizer.randomBetween(2, 10), null);
				cage.addEntity(bird);
			}

			for (int i = 0; i < Randomizer.randomBetween(1, 2); ++i) {
				Rat rat = new Rat(Randomizer.randomBetween(0, cage.getWidth() - 1),
						Randomizer.randomBetween(0, cage.getHeight() - 1), "the fat rat" + i, EntityType.RAT,
						Randomizer.randomBetween(10, 20));
				cage.addEntity(rat);
			}
			for (int i = 0; i < Randomizer.randomBetween(1, 1); ++i) {
				Mouse mouse = new Mouse(Randomizer.randomBetween(0, cage.getWidth() - 1),
						Randomizer.randomBetween(0, cage.getHeight() - 1), "the ill mouse", EntityType.MOUSE);
				cage.addEntity(mouse);
			}
			for (int i = 0; i < Randomizer.randomBetween(1, 2); ++i) {
				Bird_Bath bath = new Bird_Bath(Randomizer.randomBetween(0, cage.getWidth() - 1),
						Randomizer.randomBetween(0, cage.getHeight() - 1), "bird bath" + i,
						EntityType.BIRD_BATH);
				cage.addEntity(bath);
			}
			for (int i = 0; i < Randomizer.randomBetween(2, 3); ++i) {
				Rotten_Branch rot = new Rotten_Branch(Randomizer.randomBetween(0, cage.getWidth() - 1),
						Randomizer.randomBetween(0, cage.getHeight() - 1), "rotten branch" + i,
						EntityType.ROTTEN_BRANCH);
				cage.addEntity(rot);
			}
			for (int i = 0; i < Randomizer.randomBetween(2, 3); ++i) {

				Food_Dispenser mmm = new Food_Dispenser(Randomizer.randomBetween(0, cage.getWidth() - 1),
						Randomizer.randomBetween(0, cage.getHeight() - 1), "grain dispenser" + i,
						EntityType.FOOD_DISPENSER);
				cage.addEntity(mmm);
			}
		}
	}

	/**
	 * The main menu: gives a user an overview of the options he/she can take.
	 * 
	 * Current options are:
	 * 1. Show overview of all entities.
	 * 2. Run the world for X turns + turn-based actions. 
	 * 3. Get the history and properties of a bird, given its already simulated turns (cf. 2)
	 * 4. Print a 2D grid for the current state of the aviary
	 * 5. Reset the Aviary to the initial state
	 */
	public void initialMenu() {
		if (userInputSteps == 0) {
			System.out.println("Welcome to your BirdSimulator");
			System.out.print("\n");
			System.out.print("This simulator allows you to create an aviary "
					+ "in which birds will do what birds do best: eat, reproduce, breed, " + "wash their feathers...,"
					+ "\n" + "whilst also facing the dangers of birdlife: "
					+ "an invading rat, stalking around the food, and a little ill mouse, " + "\n"
					+ "which will make every bird sick when it comes in contact with it.");
			System.out.print("\n");
			System.out.print("\n");
			System.out.println("Let us see how it all will pan out this time.");
			System.out.print("\n");
			System.out.println("Please choose one of the options below: ");
		} else {
			System.out.print("Welcome back to your BirdSimulator! ");
			System.out.println("Would you like to give it another try? " + "Please choose another option below:");
		}
		// 1. Show overview of all entities.
		System.out.println("\t1. See an overview of all (living) entities in the aviary.");

		// 2. Run the aviary for X turns.
		System.out.println("\t2. Let the simulator run for x turns, " + "and see what happens each turn.");

		// 3. View specs of a specific bird.
		System.out.println("\t3. Zoom in on one specific bird and see how it " + "has spent its time in the aviary. ");

		// 4. Give a more graphical representation for the current state of the aviary.
		System.out.println("\t4. Print a tab-separated grid for the resulting state of the aviary.");

		// 5. Reset the aviary to its original state.
		System.out.println("\t5. Reset the aviary to its initial state.");

		/**
		 * Keep track of how many times a user has accessed the initial menu
		 */
		userInputSteps++;

		System.out.println("\nPlease type the number of the option you're interested in: ");

		/**
		 * The user has to choose an option between 0 and 6
		 * The switch below will manage which methods need to be called 
		 * for the appropriately chosen method.
		 */
		int choice = 0;
		intSuccess = false;

		while (intSuccess == false) {
			try {
				choice = Integer.parseInt(scan.nextLine());
				if (0 < choice && choice < 6) {
					intSuccess = true;
					System.out.print("\n");

					switch (choice) {
					case 1:
						getEntityOverview();
						//other alternative: overviewPrint(); 
						break;
					case 2:
						moveAllForTurns();
						break;
					case 3:
						inspectEntity();
						break;
					case 4:
						System.out.println(cage);
						backToMainMenu();
						break;
					case 5:
						restartAviary();
						break;
					}
				} else {
					throw new NumberFormatException();
				}
			} catch (NumberFormatException error) {
				System.err.println("Invalid integer!\n Please try again. " + "Enter a number between 1 and 5");
			}
		}
	}

	/**
	 * The main method offers the general execution functionality to the
	 * BirdSimulator. We will here call all methods that need to be executed.
	 * 
	 * 1: We set up the world with the initialSetup() method. 
	 * ==> Entities are created (random/pre-defined) and added to the world.
	 * 
	 * 2: We set up the main menu for the player with the initialMenu() method.
	 * ==> The text-based interface for simulator actions
	 */

	public static void main(String[] args) {

		/**
		 * An instance of MainFunctionality is created to be able to call upon
		 * non-static methods within the (always) static main method.
		 */
		MainFunctionality mainFunc = new MainFunctionality();

		// Create aviary and add entities inside
		mainFunc.initialSetup(true);

		// Start "Welcome screen" and show simulator options
		mainFunc.initialMenu();
	}

	/**
	 * This method simulates one step in the aviary
	 * It also builds a turn header which will appear for every step.
	 * 
	 * Each step consists of 2 parts: 
	 * 	1. Move all entities 
	 * 	2. Call all appropriate interaction methods
	 * 		@see InteractionManager 
	 */
	private void step() {
		String turnHeader = "TURN " + turn + "=";

		// Again use StringBuilder to save memory.
		// Normally Java stores each string in memory.
		StringBuilder turnHeaderBuilder = new StringBuilder();
		for (int i = 0; i < turnHeader.length(); i++) {
			turnHeaderBuilder.append("=");
		}

		turnHeader = "\n" + turnHeader + "\n" + turnHeaderBuilder.toString();

		System.out.println(turnHeader);

		// Do movement before all other events (interactions etc.)
		cage.moveInAviary();

		// ***** MOVEMENT ***** //
		System.out.println("---Movement---\n");
		// How has each entity moved?
		for (Entity entity : cage.getEntities()) {
			if (entity.getType() == EntityType.BIRD) {
				((Bird) entity).historyAdd(turnHeader);
			}
			StringBuilder movementBuilder = new StringBuilder(entity.getName());

			// Figure out if entity has moved, or not
			if (entity.getHasMoved()) {
				movementBuilder.append(" moved to ");
			} else {
				movementBuilder.append(" stayed at ");
			}
			movementBuilder.append(entity.getX() + "," + entity.getY());

			if (cage.getEntities().indexOf(entity) == (cage.getEntities().size() - 1)) {
				movementBuilder.append("\n");
			}
			System.out.println(movementBuilder.toString());
			
			if (entity.getType() == EntityType.BIRD) {
			((Bird) entity).historyAdd(movementBuilder.toString());
			}
		} // END for-loop for the movement of entities

		// **** CONTACT ***** //
		System.out.println("-- Contact --\n-------------");
		cage.checkInteraction();
		cage.incrementAge();
		cage.checkIllness();
	}

	/**
	 * Moves all entities for X turns. Asks a user how many turns they want to
	 * move forward and then "runs" the world for given turns. Nothing is
	 * returned, but information such as movement, encounters, and events are
	 * printed out.
	 * <p>
	 * NOTE: movement takes place before each turn.
	 *
	 * @see Aviary.moveInAviary()
	 */
	private void moveAllForTurns() {
		System.out.println("Splendid choice!\n");

		System.out.println("Each turn will give an overview of " + "the moves entities have made and any "
				+ "events that may have taken place.\n" + "The first turn will show the initial state of "
				+ "the aviary.");

		/**
		 * Ask for user's input: how many turns do you want to advance the
		 * aviary into the future?
		 */
		System.out.println("How many turns do you wish to advance");
		amountOfTurns();

		/*
		 * @author Jordy
		 * First we print out the initial state of the aviary.
		 * I use a StringBuilder in order to save memory.
		 * Externally, it behaves similarly as a String Object
		 * but with the added advantage that it can be modified.
		 * Internally, it behaves as a variable-length array.
		 * ==> Constructs a string builder with no characters in it and an initial
		 *  capacity of 16 characters.
		 */
		/**
		 * Firstly, an initial overview of all current entities and location is given
		 */
		System.out.println("---INITIALIZE---");
		for (Entity entity : cage.getEntities()) {
			StringBuilder initPosBuilder = new StringBuilder(entity.getName());

			initPosBuilder.append(" at " + entity.getX() + "," + entity.getY());

			// The indexOf() method returns the position of the first occurrence
			// of a specified value in a string.
			if (cage.getEntities().indexOf(entity) == (cage.getEntities().size() - 1)) {
				initPosBuilder.append("\n");
			}
			System.out.println(initPosBuilder.toString());
		}

		/**
		 *  Run the loop for i given turns
		 *  For every step, the int variable is increased
		 *  Yet it may never be higher than the asked amount of turns.
		 *  The turn-variable is increased with every step,
		 *  and will keep score of all turns asked for. 
		 *  Even if the user has for instance asked 2 times for 10 turns
		 *  It will add them up; e.g.: 20
		 */
		// 
		// 
		for (int i = 1; i < turnsAmount; i++) {
			step();
			turn++;
		}
		backToMainMenu();
	}

	/**
	 * Zoom in on one given bird.
	 * The method returns the history and properties of a (living) bird.
	 * A pre-condition is that in the main menu, option 2 has already been chosen.
	 * This will guarantee that the bird has done something interesting in the aviary
	 */
	private void inspectEntity() {
		// Here both the boolean and the Entity object are reset
		entitySuccess = false;
		Entity entity = null;

		System.out.println("\n");

		System.out.println("Each turn will give an overview of " + "the moves the entity has made and any "
				+ "interactions that may have taken place.\n" + "The first turn will show the initial state of "
				+ "the entity.");

		/**
		 * Here we only allow for zooming in on still living birds.
		 * Seeing that they are the only really "interesting" entities in the aviary.
		 * 
		 * We will ask for a name, which is easiest to ask for. 
		 * It consists of "Birdie" followed by an integer.
		 */
		//

		System.out.println("Which bird do you wish to follow in the aviary?");

		while (entitySuccess == false) {
			try {
				String birdName = scan.nextLine();
				if (birdName.matches("(?i)\\w+") && cage.isBird(birdName)) {
					entity = Aviary.getEntityWithName(birdName);
					for (String ev : ((Bird) entity).getHistory()) {
						System.out.println(ev);
					}
					System.out.println(((Bird) entity));
					entitySuccess = true;
					System.out.print("\n");
				} else {
					throw new InputMismatchException();
				}
			} catch (InputMismatchException error) {
				System.err.println("Invalid name!\n" + "Unfortunately, there is no bird with that name "
						+ ", or you entered an invalid name.\n" + "Please enter another name");
			}

		}
		backToMainMenu();
	}

	public void restartAviary() {
		System.out.println("Would you like to restart the aviary with random entities and values?");
		System.out
		.println("Or would you like to restart the aviary " + "with a pre-defined set of entities and values?");
		System.out.println("Answer \"yes\" for a randomized restart or \"no\" for a pre-defined restart");
		stringSuccess = false;
		while (stringSuccess == false) {
			try {
				String yesNo = scan.nextLine();
				/**
				 * For regular expression definition: 
				 * @see: backToMainMenu();
				 */
				if (yesNo.matches("^(?i)y(?:es)?|no?$")) {
					stringSuccess = true;
					System.out.print("\n");

					if (yesNo.equalsIgnoreCase("y") || yesNo.equalsIgnoreCase("yes")) {
						cage.restart();
						// randomCreation = true;
						initialSetup(true);
						System.out.println("The aviary has returned to its initial state");
						backToMainMenu();
					} else {
						cage.restart();
						// randomCreation = false;
						initialSetup(false);
						System.out.println("The aviary has returned to its initial state");
						backToMainMenu();
					}
				} else {
					throw new InputMismatchException();
				}
			} catch (InputMismatchException error) {
				System.err.println("This is not a valid command. " + "Please anwer with \"yes\" or \"no\".");
			}
		}
	}
}
