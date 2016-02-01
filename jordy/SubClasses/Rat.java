package jordy.SubClasses;

import jordy.Entity;
import jordy.Enums.EntityType;

public class Rat extends Entity {
	public int fitness;

	public Rat(int x, int y, String name, EntityType type, int fitness) {
		super(x, y, name, type, true, true);
		this.fitness = fitness;
		// TODO Auto-generated constructor stub
	}
}