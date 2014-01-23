import java.awt.Color;
import java.util.*;

public class Creature {
    private int maxhp, hp;
    private Tile curtile;
    private ArrayList<Item> inventory = new ArrayList<Item>();
    protected char displayChar = 'X';
    protected Color displayColor = Color.WHITE;

    public Creature() {
	maxhp=10;
	hp=maxhp;
    }

    public boolean move(Direction d) {
	if(d==Direction.NONE){return true;}
	if(curtile.getTileAt(d)!=null) {
	    Tile targettile = curtile.getTileAt(d);
	    if(targettile.isWalkable() && targettile.getBaka()==null) {
		targettile.setBaka(this);
		curtile.setBaka(null);
		curtile=targettile;
		return true;
	    }
	}
	return false;
    }

    public boolean pickUpItem() {
	if(curtile.getItems().size()==0) {return false;}
	Item i=curtile.getItems().get(0);
	inventory.add(i);
	curtile.removeItem(i);
	return true;
    }

    public boolean dropItem() {
	System.out.println("oh man, dropping items is hard");
	return false;
    }
    
    public char getDispChar() {return displayChar;}
    public Color getDispColor() {return displayColor;}
    public Tile getTile() {return curtile;}
    public int getMaxHP() {return maxhp;}
    public int getHP() {return hp;}
    public ArrayList<Item> getInventory() {return inventory;}

    public void setTile(Tile t) {curtile=t;}
    public void setHP(int n) {hp=n;}
    public void hurt(int n) {hp-=n;}
}
