package jordy;

import java.util.*;
import jordy.Enums.Bound;
import jordy.Enums.EntityType;

/**
 * The Aviary class is very important: It holds all tiles (coordinate-pairs)
 * which form up the world It will also function as a manager of many arraylists
 * These contain either tiles, entities or specific entities
 * 
 * @author Jordy
 *
 */
public class Aviary {
	private static Tile[][] coordinates;
	private static List<Entity> entities = new ArrayList<>();
	private static List<Bird> birdsInAviary = new ArrayList<>();
	private List<Tile> tiles = new ArrayList<>();
	private InteractionManager interactManager = new InteractionManager(this);
	private static final int MAX_BIRDSINAVIARY = 20;
	private int width;
	private int height;

	/**
	 * The following constructor will build up a world of a certain
	 * @param width
	 * and
	 * @param height
	 */
	public Aviary(int width, int height) {
		this.width = width;
		this.height = height;

		// `coordinates` is a 2D array containing tiles
		// Actually, this *is* the bird cage: it holds all tiles,
		// which in turn hold all entities
		coordinates = new Tile[width][height];

		// Fills the coordinates with tiles
		// For now no bounds are assigned 
		// These will be used later to make sure no movement will result
		// in entities moving out of the bounds of the cage.
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				coordinates[i][j] = new Tile(i, j, Bound.NEUTRAL);
				tiles.add(coordinates[i][j]);
			}
		}
	}


	/**
	 * We add the entity to a tile, and each tile is a collection of entities.
	 * Remember that `coordinates` is a collection of tiles at given coordinates. 
	 * coordinates[0][0] is the tile in the upper left corner; the first tile.
	 *
	 * When we create an entity, we immediately also add it to the world. 
	 * In other words: it isn't "abstract" any more but it's inside the cage now.
	 *  Furthermore, this also makes sure that we add the entity to the tile that corresponds with its coordinates.
	 *  
	 * @param entity
	 *            An entity is being added to the world
	 *            
	 * @param birdsInAviary
	 * 			  This will form an extra, but separate list in which only the birds are saved
	 * 			  It will be used later to set governing bounds on the maximum of birds
	 */

	public void addEntity(Entity entity) {
		int x = entity.getX();
		int y = entity.getY();
		try {
			coordinates[x][y].addEntityToTile(entity);
			entities.add(entity);

			if (entity instanceof Bird) {
				birdsInAviary.add((Bird) entity);
			}
		} catch (IndexOutOfBoundsException e) {
			System.err.println("The entity " + entity.getName() + "cannot be added to the aviary, because the inputted"
					+ "coordinates exceed the aviary's boundaries." + "\nThe following error has occured: " + e);
		}
	}

	/**
	 * -- GETTERS --
	 * 
	 * The methods that follow involve getting objects and variables
	 * either out of one of the arraylists in aviary 
	 * or out of the aviary itself
	 */

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}


	public int getMaxBirdsinaviary() {
		return MAX_BIRDSINAVIARY;
	}

	/**
	 * This method returns a Bird object with a specific name.
	 * 
	 * @param name
	 *            The name of the object that will be returned.
	 * @return Birds
	 * @see MainFunctionality
	 */
	public static Entity getEntityWithName(String name) {
		for (Entity entity : entities) {
			if (entity.getName().equalsIgnoreCase(name)) {
				return entity;
			}
		}
		return null;
	}

	/**
	 * This method checks for certain if an entity is a bird.
	 * 
	 * @author Jordy
	 * = instanceof: to react differently based upon an object's type at runtime.
	 * @param name
	 * @return boolean
	 */

	public boolean isBird(String name) {
		for (Entity entity : entities) {
			if (entity instanceof Bird) {
				if (((Bird) entity).getName().equalsIgnoreCase(name)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method to retrieve all entities in the world
	 */
	public List<Entity> getEntities() {
		return entities;
	}

	/**
	 * Method to retrieve all birds in the world
	 */
	public List<Bird> getBirdsInAviary() {
		return birdsInAviary;
	}

	/**
	 *  Method to retrieve entities on specific coordinates
	 * @param x
	 * @param y
	 * @return all entities on specific coordinates
	 */
	public List<Entity> getOnCoordinates(int x, int y) {
		return coordinates[x][y].getEntitiesOnTile();
	}

	/**
	 *  Method to retrieve by which sides a tile 
	 *  && by which sides an entity's movement is bound.
	 *  
	 * @param x
	 * @param y
	 * @return For a certain tile (2D coordinate arraylist): bounded edges
	 */
	public Bound getTileEdgeOnLocation(int x, int y) {
		if (x >= 0 && y >= 0 && x < width && y < height) {
			return coordinates[x][y].getTileEdge();
		}
		return null;
	}


	// -- World-Based actions -- //

	/**
	 * The moveInAviary method is very important for movement in the world. It
	 * functions as a wrapper, controlling all movement in a loop. This loop
	 * allows all movable entities to make a move in the aviary.
	 */
	public void moveInAviary() {
		for (Entity entity : entities) {
			if (entity.getMovable()) {
				// As in copy commands, first remove from current tile
				coordinates[entity.getX()][entity.getY()].removeEntityFromTile(entity);
				// Add entity to new tile
				int[] position = entity.move(this.width, this.height, this);
				coordinates[position[0]][position[1]].addEntityToTile(entity);
			}
		}
	}

	/**
	 * fallDown allows for an entity to fall down from a rotten branch
	 * 
	 * @param x
	 * @param y
	 * @param entity
	 */
	public void fallDown(int x, int y, Entity entity) {
		if (entity.getMovable()) {
			// As in copy commands, first remove from current tile
			coordinates[entity.getX()][entity.getY()].removeEntityFromTile(entity);
			// Add entity to new tile
			coordinates[x][y].addEntityToTile(entity);
			entity.setXPosition(x);
			entity.setYPosition(y);
			System.out.println(((Bird) entity).historyAdd(entity.getName() + " fell down from the rotten branch to " + "the coordinates " + x + "," + y));
		}
	}

	/**
	 * This method removes an entity from a tile
	 * --> because it has died 
	 */
	public void removeEntityFromAviary(Entity entity) {
		coordinates[entity.getX()][entity.getY()].removeEntityFromTile(entity);
		entities.remove(entity);
	}


	/**
	 * The checkOccupance method will check for the amount of entities on the
	 * tiles. It is necessary for checking contact and interactions between
	 * entities. This includes reproduction and inter-entities interactions.
	 */

	public void checkInteraction() {
		boolean interaction = false;
		for (Tile tile : tiles) {
			List<Entity> interactTile = tile.getEntitiesOnTile();
			if (tile.getEntitiesOnTile().size() > 1) {
				interaction = true;
				System.out.print("Tile " + tiles.indexOf(tile) + " at " + tile.getXPosition() + ","
						+ tile.getYPosition() + " currently has " + tile.getEntitiesOnTile().size()
						+ " entities occupying it" + "\n");

				/**
				 * This method is vital for the checking of interactions
				 * It checks all possible combinations (inter-entitywise and orderwise)
				 * if there are multiple entities on the same tile.
				 * 
				 * More concrete:
				 * If occupance of a tile = higher than 1 ==> Check which entities occupy
				 * the tile, and on that basis call the appropriate interaction
				 * methods.
				 * 
				 */
				for (int i = 0; i < interactTile.size(); i++) { // one
					for (int j = 0; j < interactTile.size(); j++) { // two
						if (i != j) {
							interactManager.checkInteraction(interactTile.get(i), interactTile.get(j));
						}
					}
				}
			}
		}
		if (!interaction) {
			System.out.println("There have been no interactions this turn");
		}
	}

	/**
	 * Most generally, this method will reset the world and all entities in it
	 * @see MainFunctionality
	 */
	public void restart() {
		tiles.clear();
		for (int i = 0; i < coordinates.length; i++) {
			Arrays.fill(coordinates[i], null);
		}
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				coordinates[i][j] = new Tile(i, j, Bound.NEUTRAL);
				tiles.add(coordinates[i][j]);
			}
		}
		entities.clear();
		birdsInAviary.clear();
		
	}

	/**
	 * This method will make sure that entities age every turn
	 * Moreover, it will also make sure they are removed
	 * when their age has reached a maximum 
	 * 
	 * @see Bird.incrementAge() 
	 */
	public void incrementAge() {
		Iterator<Entity> i = entities.iterator();
		while (i.hasNext()) {
			Entity ent = i.next();
			if (ent.getType() == EntityType.BIRD) {
				((Bird) ent).incrementAge();
				if (!ent.isLives()) {
					coordinates[ent.getX()][ent.getY()].removeEntityFromTile(ent);
					i.remove();
					System.out.println(((Bird) ent).historyAdd(ent.getName() + " has died from old age"));
				}
			}
		}
	}

	/**
	 * This method will check if an entity is ill
	 * Moreover, if an entity is ill it will have decreasing fitness each turn
	 * If it dies from illness, it is also removed 
	 */
	public void checkIllness() {
		Iterator<Entity> i = entities.iterator();
		while (i.hasNext()) {
			Entity ent = i.next();
			if (ent.getType() == EntityType.BIRD && ((Bird) ent).isIll()) {
				((Bird) ent).decreaseFitness();
				if (!ent.isLives()) {
					coordinates[ent.getX()][ent.getY()].removeEntityFromTile(ent);
					i.remove();
					System.out.println(((Bird) ent).historyAdd(ent.getName() + " has died from illness"));
				}
			}
		}
	}

	/**
	 * This toString method will allow us to create a more graphical representation
	 * of the grid and entities inhabiting it.
	 * We do this by looping over all tiles and their coordinates
	 */
	@Override
	public String toString() {
		StringBuilder grid = new StringBuilder();

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {

				Tile tile = coordinates[j][i];
				if (tile.getEntitiesOnTile().size() > 0) {
					for (Entity entity : tile.getEntitiesOnTile()) {
						grid.append(String.format("%20s", entity.getName()));
					}
				} else {
					grid.append(String.format("%20s", " "));
				}
				grid.append(" ** ");
			}
			grid.append("\n");
		}
		return grid.toString();
	}
}
