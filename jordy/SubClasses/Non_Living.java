package jordy.SubClasses;

import jordy.Entity;
import jordy.Enums.EntityType;

public abstract class Non_Living extends Entity {

	/**
	 * @param x
	 * @param y
	 * @param name
	 * @param lives
	 *            By default = false
	 * @param movable
	 *            By default = false
	 * @param type
	 *            Inherited by superclass Entity
	 */

	public Non_Living(int x, int y, String name, EntityType type) {
		super(x, y, name, type, false, false);
	}
}
