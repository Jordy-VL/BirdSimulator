package jordy;

import java.util.Random;

/**
 * You should use enum types any time you need to represent a fixed set of
 * constants. An enum type is a special data type that enables for a variable to
 * be a set of predefined constants. The variable must be equal to one of the
 * values that have been predefined for it.
 * @author Jordy
 *
 */

public class Enums {

	public static enum BirdGender {
		FEMALE, MALE;

		public static BirdGender getRandom() {
			Random rand = new Random();
			return values()[rand.nextInt(values().length)];
		}
	}

	public static enum Color {
		GREEN, BLUE, YELLOW, GREY, WHITE;

		public static Color getRandom() {
			Random rand = new Random();
			return values()[rand.nextInt(values().length)];
		}

		/**
		 * @author Jordy
		 *  * question mark: shorthand for if/else
		 *  
		 * @param color1
		 *            color of the father
		 * @param color2
		 *            color of the mother
		 * @return mixed color
		 */
		public static Color getMix(Color color1, Color color2) {

			return (new Random().nextInt() < 0) ? color1 : color2;
		}
	}

	public static enum EntityType {
		BIRD, MOUSE, RAT, ROTTEN_BRANCH, BIRD_BATH, FOOD_DISPENSER
	}

	public static enum BirdDirection {
		UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT
	}

	public static enum Bound {
		UP_TILE, RIGHT_TILE, LEFT_TILE, DOWN_TILE, FREE, NEUTRAL
	}

	public static enum LifeGoals {
		FOOD, MANY_CHILDREN, MANY_PARTNERS;

		public static LifeGoals getRandom() {
			Random rand = new Random();
			return values()[rand.nextInt(values().length)];
		}
	}

}