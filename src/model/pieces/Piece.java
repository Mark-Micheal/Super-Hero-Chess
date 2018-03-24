package model.pieces;

import exceptions.OccupiedCellException;
import model.game.Direction;
import model.game.Game;
import model.game.Player;
import model.pieces.heroes.Armored;
import model.pieces.sidekicks.SideKick;

public abstract class Piece implements Movable {

	private String name;
	private Player owner;
	private Game game;
	private int posI;
	private int posJ;

	public Piece(Player p, Game g, String name) {
		this.owner = p;
		this.game = g;
		this.name = name;
	}

	public void attack(Piece target) {
		Player attacked = target.getOwner();
		Player attacker = game.getCurrentPlayer();
		boolean wasArmored = false;
		if (target instanceof SideKick) {
			attacker.setSideKilled(attacker.getSideKilled() + 1);
			if (attacker.getSideKilled() % 2 == 0)
				attacker.setPayloadPos(attacker.getPayloadPos() + 1);
			game.getCellAt(target.getPosI(), target.getPosJ()).setPiece(null);
		} else if (target instanceof Armored && ((Armored) target).isArmorUp()) {
			((Armored) target).setArmorUp(false);
			wasArmored = true;
		} else {
			attacked.getDeadCharacters().add(target);
			attacker.setPayloadPos(attacker.getPayloadPos() + 1);
			game.getCellAt(target.getPosI(), target.getPosJ()).setPiece(null);
		}
		if(!wasArmored)
			game.checkWinner();
	}

	private void helperMove(int oldI, int oldJ, int i, int j, Direction r) throws OccupiedCellException {
		if (game.getCellAt(i, j).getPiece() == null) {
			game.getCellAt(i, j).setPiece(this);
			game.getCellAt(oldI, oldJ).setPiece(null);
			this.setPosI(i);
			this.setPosJ(j);
		} else if (game.getCellAt(i, j).getPiece().getOwner() == this.getOwner()) {
			throw new OccupiedCellException("This an Occupied Cell by a friendly", this, r);
		} else {
			attack(game.getCellAt(i, j).getPiece());
			if (game.getCellAt(i, j).getPiece() == null) {
				game.getCellAt(i, j).setPiece(this);
				game.getCellAt(oldI, oldJ).setPiece(null);
				this.setPosI(i);
				this.setPosJ(j);
			}
		}
		game.switchTurns();
	}

	public void moveDown() throws OccupiedCellException {
		int oldI = getPosI();
		int oldJ = getPosJ();
		int i = getPosI();
		int j = getPosJ();
		i++;
		if (i == 7)
			i = 0;
		helperMove(oldI, oldJ, i, j, Direction.DOWN);
	}

	public void moveDownLeft() throws OccupiedCellException {
		int oldI = getPosI();
		int oldJ = getPosJ();
		int i = getPosI();
		int j = getPosJ();
		i++;
		j--;
		if (i == 7)
			i = 0;
		if (j == -1)
			j = 5;
		helperMove(oldI, oldJ, i, j, Direction.DOWNLEFT);
	}

	public void moveDownRight() throws OccupiedCellException {
		int oldI = getPosI();
		int oldJ = getPosJ();
		int i = getPosI();
		int j = getPosJ();
		i++;
		j++;
		if (i == 7)
			i = 0;
		if (j == 6)
			j = 0;
		helperMove(oldI, oldJ, i, j, Direction.DOWNRIGHT);
	}

	public void moveLeft() throws OccupiedCellException {
		int oldI = getPosI();
		int oldJ = getPosJ();
		int i = getPosI();
		int j = getPosJ();
		j--;
		if (j == -1)
			j = 5;
		helperMove(oldI, oldJ, i, j, Direction.LEFT);
	}
	
	public void moveRight() throws OccupiedCellException {
		int oldI = getPosI();
		int oldJ = getPosJ();
		int i = getPosI();
		int j = getPosJ();
		j++;
		if (j == 6)
			j = 0;
		helperMove(oldI, oldJ, i, j, Direction.RIGHT);
	}
	
	public void moveUp() throws OccupiedCellException {
		int oldI = getPosI();
		int oldJ = getPosJ();
		int i = getPosI();
		int j = getPosJ();
		i--;
		if (i == -1)
			i = 6;
		helperMove(oldI, oldJ, i, j, Direction.UP);
	}
	
	public void moveUpLeft() throws OccupiedCellException {
		int oldI = getPosI();
		int oldJ = getPosJ();
		int i = getPosI();
		int j = getPosJ();
		i--;
		j--;
		if (i == -1)
			i = 6;
		if (j == -1)
			j = 5;
		helperMove(oldI, oldJ, i, j, Direction.UPLEFT);
	}
	
	public void moveUpRight() throws OccupiedCellException {
		int oldI = getPosI();
		int oldJ = getPosJ();
		int i = getPosI();
		int j = getPosJ();
		i--;
		j++;
		if (i == -1)
			i = 6;
		if (j == 6)
			j = 0;
		helperMove(oldI, oldJ, i, j, Direction.UPRIGHT);
	}
	
	public String getName() {
		return name;
	}

	public int getPosI() {
		return posI;
	}

	public void setPosI(int i) {
		posI = i;
	}

	public int getPosJ() {
		return posJ;
	}

	public void setPosJ(int j) {
		posJ = j;
	}

	public Game getGame() {
		return game;
	}

	public Player getOwner() {
		return owner;
	}

}
