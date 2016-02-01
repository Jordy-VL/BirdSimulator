package jordy;

import java.util.ArrayList;
import java.util.List;


import jordy.Enums.*;

public class InteractionManager {

	private static Aviary aviary;

	@SuppressWarnings("static-access")
	public InteractionManager(Aviary aviary) {
		this.aviary = aviary;
	}

	/**
	 * This class and its method checkInteraction are vital It "manages" all
	 * inter-entities interactions It takes the world (aviary) as its argument
	 * so it can call all available methods and arraylists from there.
	 * 
	 * @param entityOne
	 *            object which has a certain type and properties
	 * @param entityTwo
	 *            object which has a certain type and properties
	 * 
	 * It has if-statements to determine which types of entities engage in an interaction
	 * On that basis it calls the appropriate methods which need to be triggered
	 * 
	 * In short: 
	 * bird-bird = reproduction / contamination after mouse-interaction
	 * bird-food = eat / regain health after illness
	 * bird-bath = wash / regain health after illness
	 * bird-branch = fall down to ground 
	 * bird-rat = fight && get killed
	 * bird-mouse = get infected and die when fitness depletes
	 * 
	 */
	public void checkInteraction(Entity entityOne, Entity entityTwo) {
		if (entityOne.getType() == EntityType.BIRD && entityTwo.getType() == EntityType.BIRD) {
			// bird and bird
			if (((Bird) entityOne).canBreed() && !((Bird) entityOne).isIll() 
					&& ((Bird) entityTwo).canBreed() && !((Bird) entityTwo).isIll() 
					&& aviary.getBirdsInAviary().size() < aviary.getMaxBirdsinaviary()) {
				if (((Bird) entityOne).getGender() == BirdGender.MALE
						&& ((Bird) entityTwo).getGender() == BirdGender.FEMALE
						|| ((Bird) entityOne).getGender() == BirdGender.FEMALE
						&& ((Bird) entityTwo).getGender() == BirdGender.MALE) {

					for (int i = 0; i < Randomizer.randomBetween(0, 5); ++i) {
						Color color1 = ((Bird) entityOne).getColor();
						Color color2 = ((Bird) entityTwo).getColor();
						List<String> parentsList = new ArrayList<>();
						parentsList.add(entityOne.getName());
						parentsList.add(entityTwo.getName());
						Bird chick = new Bird(entityOne.getX(), entityOne.getY(),
								"chickie" + aviary.getBirdsInAviary().size(), true, EntityType.BIRD, true,
								BirdGender.getRandom(), 0, Color.getMix(color1, color2), 1, LifeGoals.getRandom(),
								Randomizer.randomBetween(1, 4), parentsList);
						aviary.addEntity(chick);
						((Bird) entityOne).getChicksList().add(chick.getName());
						((Bird) entityTwo).getChicksList().add(chick.getName());
						System.out.println("reproduction in progress");
						System.out.println(((Bird) chick).historyAdd(chick.getName() + " has hatched from its egg"));
					}
				}
			}

			if (((Bird) entityOne).isIll() && !((Bird) entityTwo).isIll()) {
				((Bird) entityTwo).setIll(true);
				System.out.println(
						((Bird) entityOne).historyAdd(entityOne.getName() + " has infected " + entityTwo.getName()));
				System.out.println(((Bird) entityTwo)
						.historyAdd(entityTwo.getName() + " has been infected by " + entityOne.getName()));
			}

		}

		if (entityOne.getType() == EntityType.BIRD && entityTwo.getType() == EntityType.FOOD_DISPENSER) {
			boolean b = ((Bird) entityOne).getLifegoal() == LifeGoals.FOOD ? true : false;
			((Bird) entityOne).incrementFitness(b);
			if (((Bird) entityOne).isIll()) {
				((Bird) entityOne).setIll(false);
				System.out.println(((Bird) entityOne).historyAdd(entityOne.getName()
						+ " has recovered from its illness " + "by eating the magical healing seed"));
			}
		}

		if (entityOne.getType() == EntityType.BIRD && entityTwo.getType() == EntityType.BIRD_BATH) {
			boolean b = ((Bird) entityOne).getLifegoal() == LifeGoals.MANY_PARTNERS ? true : false;
			((Bird) entityOne).incrementSexAppeal(b);
			if (((Bird) entityOne).isIll()) {
				((Bird) entityOne).setIll(false);
				System.out.println(((Bird) entityOne).historyAdd(entityOne.getName()
						+ " has recovered from its illness " + "by washing itself in the magical bird wash"));
			}
		}

		if (entityOne.getType() == EntityType.BIRD && entityTwo.getType() == EntityType.ROTTEN_BRANCH) {
			aviary.fallDown(entityOne.getX(), aviary.getHeight() - 1, entityOne);
			((Bird) entityOne).decreaseSexAppeal();
		}

		if (entityOne.getType() == EntityType.BIRD && entityTwo.getType() == EntityType.RAT) {
			System.out.println("The rat has killed " + entityOne.getName());
			entityOne.setLives(false);
			aviary.removeEntityFromAviary(entityOne);

		} if (entityOne.getType() == EntityType.BIRD && entityTwo.getType() == EntityType.MOUSE
				&& !((Bird) entityOne).isIll()) {
			System.out.println("The mouse has infected " + entityOne.getName());
			((Bird) entityOne).historyAdd( entityOne.getName() + " has been infected by the mouse");
			((Bird) entityOne).setIll(true);
		}
	}
}