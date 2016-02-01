package jordy;

import java.util.ArrayList;
import java.util.List;
import jordy.Enums.Bound;

public class Tile {

	// A list per tile, with all entities on that tile
	private List<Entity> entitiesOnTile = new ArrayList<>();

	// Final because we do not want tiles to change: they have a fixed position
	private final int xPosition;
	private final int yPosition;

	/**
	 * Bound edge will determine if we are dealing with a tile at the edge of
	 * the world
	 * 
	 * @see Enums.Bound
	 */
	private final Bound edge;

	/**
	 * 
	 * @param xPosition		width-coordinate for a tile
	 * @param yPosition		height-coordinate for a tile
	 * @param edge			At which edge a tile is bound
	 */
	public Tile(int xPosition, int yPosition, Bound edge) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.edge = edge;
	}

	/**
	 * Adds entity to the bird cage, because we attach it to a tile
	 * 
	 * @param entity
	 */
	public void addEntityToTile(Entity entity) {
		getEntitiesOnTile().add(entity);
	}

	/**
	 * The following method removes an entity from a tile. This means that an
	 * entity will no longer belong to a tile It can be added to another tile or
	 * no longer have presence
	 * 
	 * @param entity
	 */
	public void removeEntityFromTile(Entity entity) {
		getEntitiesOnTile().remove(entity);
	}

	/* Getters for the variables of each tile */
	public List<Entity> getEntitiesOnTile() {
		return entitiesOnTile;
	}

	public int getXPosition() {
		return xPosition;
	}

	public int getYPosition() {
		return yPosition;
	}

	public Bound getTileEdge() {
		return edge;
	}
}