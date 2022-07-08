package com.sesu8642.feudaltactics.gamelogic.gamestate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.badlogic.gdx.utils.reflect.ClassReflection;

/** Group of connected tiles that belong to the same player. **/
public class Kingdom {

	// need a list to have consistent iteration order; LinkedHashSet doesn't work
	// because the tiles can change
	private List<HexTile> tiles = new ArrayList<>();
	private Player player;
	private int savings = 0;
	// only used by ai
	private boolean doneMoving = false;
	// for displaying a hint when the player forgets the kingdom
	private boolean wasActiveInCurrentTurn = false;

	public Kingdom() {
	}

	public Kingdom(Player player) {
		this.player = player;
	}

	/**
	 * Calculates the income of the kingdom.
	 * 
	 * @return income
	 */
	public int getIncome() {
		// number of tiles - trees
		return tiles.size() - (int) tiles.stream().filter(tile -> tile.getContent() != null
				&& ClassReflection.isAssignableFrom(Tree.class, tile.getContent().getClass())).count();
	}

	/**
	 * Calculates the salaries this kingdom has to pay every turn.
	 * 
	 * @return salaries
	 */
	public int getSalaries() {
		// sum of the salaries of all the units
		return tiles.stream()
				.filter(tile -> tile.getContent() != null
						&& ClassReflection.isAssignableFrom(Unit.class, tile.getContent().getClass()))
				.mapToInt(tile -> ((Unit) tile.getContent()).getUnitType().salary()).sum();
	}

	public List<HexTile> getTiles() {
		return tiles;
	}

	public void setTiles(List<HexTile> tiles) {
		this.tiles = tiles;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getSavings() {
		return savings;
	}

	public void setSavings(int savings) {
		this.savings = savings;
	}

	public boolean isDoneMoving() {
		return doneMoving;
	}

	public void setDoneMoving(boolean doneMoving) {
		this.doneMoving = doneMoving;
	}

	public boolean isWasActiveInCurrentTurn() {
		return wasActiveInCurrentTurn;
	}

	public void setWasActiveInCurrentTurn(boolean wasActiveInCurrentTurn) {
		this.wasActiveInCurrentTurn = wasActiveInCurrentTurn;
	}

	@Override
	public String toString() {
		return super.toString() + "; savings: " + getSavings() + ", income: " + getIncome() + ", salaries: "
				+ getSalaries();
	}

	@Override
	public int hashCode() {
		return Objects.hash(doneMoving, player, savings, tiles, wasActiveInCurrentTurn);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Kingdom other = (Kingdom) obj;
		return doneMoving == other.doneMoving && Objects.equals(player, other.player) && savings == other.savings
				&& Objects.equals(tiles, other.tiles) && wasActiveInCurrentTurn == other.wasActiveInCurrentTurn;
	}

}
