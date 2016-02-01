package jordy.SubClasses;

import jordy.Entity;
import jordy.Enums.EntityType;

public class Mouse extends Entity {

	public Mouse(int x, int y, String name, EntityType type) {
		super(x, y, name, type, true, true);
	}
}