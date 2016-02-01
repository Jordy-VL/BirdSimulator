package jordy;

import jordy.Enums.BirdDirection;
import jordy.Enums.Bound;
import jordy.Enums.EntityType;

import java.util.*;
import java.util.Random;

public abstract class Entity {
	private List<Bound> boundList = new LinkedList<Bound>(Arrays.asList(Bound.values()));
	private int x, y;
	private String name;
	private EntityType type;
	private boolean lives;
	private boolean movable;
	private boolean hasMoved = false;

	/**
	 * The following creates an entity with its x- and y-coordinates, if it is
	 * alive or not, and if it can move or not.
	 * 
	 * @param boundlist
	 *            A list containing the edges for a tile
	 * @param x
	 *            X coordinate within the aviary
	 * @param y
	 *            Y coordinate within the aviary
	 * @param name
	 *            Name of an entity
	 * @param type
	 *            Type of the entity in question
	 * @param lives
	 *            Boolean defining if an entity is alive or not
	 * @param movable
	 *            Boolean defining if an entity can move or not
	 * @param hasMoved
	 *            Boolean necessary for turn-based movement
	 */

	public Entity(int x, int y, String name, EntityType type, boolean lives, boolean movable) {
		this.x = x;
		this.y = y;
		this.name = name;
		this.type = type;
		this.lives = lives;
		this.movable = movable;

	}

	/**
	 * The following methods will allow an entity to move ad random. One
	 * prerequisite is that the canMove boolean variable is set to true. Here we
	 * will deal with the movement of movable entities. In terms of direction
	 * this entails: UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT,
	 * UP_LEFT. At the end, the coordinates are returned to which an entity has
	 * moved.
	 *
	 * @param aviaryWidth
	 *            The width of the aviary.
	 * @param aviaryHeight
	 *            The height of the current aviary.
	 * @return int[] The coordinates that the element moved to .array
	 * 			It is needed for determining absolute position!
	 * @see Aviary#moveInAviary
	 */

	public int[] move(int aviaryWidth, int aviaryHeight, Aviary cage) {
		List<BirdDirection> BirdDirectionOptions = new ArrayList<BirdDirection>(
				Arrays.asList(BirdDirection.UP, BirdDirection.RIGHT, BirdDirection.DOWN, BirdDirection.LEFT));

		/**
		 * The following conditions will make sure the entity will not move out of bounds
		 */

		Bound tileUpEdge = cage.getTileEdgeOnLocation(this.x, this.y - 1);
		Bound tileRightEdge = cage.getTileEdgeOnLocation(this.x + 1, this.y);
		Bound tileDownEdge = cage.getTileEdgeOnLocation(this.x, this.y + 1);
		Bound tileLeftEdge = cage.getTileEdgeOnLocation(this.x - 1, this.y);

		/*
		 * Entity is in top row
		 */
		if (!boundList.contains(tileUpEdge)) {

			BirdDirectionOptions.remove(BirdDirection.UP);
		}

		/*
		 * Entity is in bottom row
		 */

		else if (!boundList.contains(tileDownEdge)) {

			BirdDirectionOptions.remove(BirdDirection.DOWN);
		}

		/*
		 * Entity is in left column
		 */
		if (!boundList.contains(tileLeftEdge)) {

			BirdDirectionOptions.remove(BirdDirection.LEFT);
		}

		/*
		 * Entity is in right column
		 */
		else if (!boundList.contains(tileRightEdge)) {
			BirdDirectionOptions.remove(BirdDirection.RIGHT);
		}
		
		// This random-block will choose a random move out of the remaining directions
		// For an entity as well as for the tile it is standing on
		Random randomInt = new Random();
		Enum<?> randomMove = BirdDirectionOptions.get(randomInt.nextInt(BirdDirectionOptions.size()));

		switch (randomMove.toString().toLowerCase()) {
		// 1: Move up
		case "up":
			this.y = this.y - 1;
			hasMoved = true;
			break;
			// 2: Move right
		case "right":
			this.x = this.x + 1;
			hasMoved = true;
			break;
			// 3: Move down
		case "down":
			this.y = this.y + 1;
			hasMoved = true;
			break;
			// 4: Move left
		case "left":
			this.x = this.x - 1;
			hasMoved = true;
			break;
		}
		return new int[] { this.x, this.y };
	}

	/* Getters for the variables of each entity */

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getName() {
		return name;
	}

	public boolean isLives() {
		return lives;
	}

	public boolean getMovable() {
		return movable;
	}

	public boolean getHasMoved() {
		return hasMoved;
	}

	public EntityType getType() {
		return type;
	}

	/* Setters for the variables of each entity */
	
	// These setters again related to the movement of each entity
	public void setXPosition(int x) {
		this.x = x;
	}

	public void setYPosition(int y) {
		this.y = y;
	}
	
	// This setter will be used to trigger death
	public void setLives(boolean lives) {
		this.lives = lives;
	}

}
