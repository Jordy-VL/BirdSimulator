package jordy;

import java.util.concurrent.ThreadLocalRandom;

public class Randomizer {

	/**
	 * Constructor for objects of class Randomizer
	 */
	public Randomizer() {
	}

	/**
	 * Provide a random but bounded integer
	 * 
	 * @param min
	 *            lower bound for the int value
	 * @param max
	 *            upper bound for the int value
	 * @return random integer
	 */
	public static int randomBetween(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
}
