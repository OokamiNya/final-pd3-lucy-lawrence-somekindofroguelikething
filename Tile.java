import java.awt.Color;
import java.util.*;

public class Tile {
    private int x,y;
    private int type;
    private Level lev;
    private Color fgcol, bgcol;
    private Creature baka;
    private ArrayList<Item> items = new ArrayList<Item>();
    private char debugoverride=' ';
    
    public Tile(int x, int y, Level l) {
	this.x = x;
	this.y = y;
	lev = l;
	type = -1;
	fgcol = Color.LIGHT_GRAY;
	bgcol = Color.BLACK;
    }

    public void addItem(Item i) {
	items.add(i);
    }

    public void removeItem(Item i) {
	items.remove(i);
    }

    public char getChar() {
	if (debugoverride!=' ') {return debugoverride;}
	if (baka!=null) {
	    return baka.getDispChar();
	}
	if (items.size()>0) {
	    return items.get(0).getChar();
	}
	switch (type) {
	case 0: //room floor
	    return '.';
	case 1: //room wall
	    return '#';
	case 2: //debug case: room wall orthogonal
	    return 'O';
	case 3: //corridor floor
	    return '+';
	default: //tunnel wall
	    return ' ';
	}
    }

    public String getDescription() {
	String r = "";
	if (items.size()>0) {
	    r+="You see here: ";
	    for(int i=0; i<items.size(); i++) {
		r+=items.get(i).getName()+", ";
	    }
	}
	return r;
    }

    public int getx() {return x;}
    public int gety() {return y;}
    public int getType() {return type;}
    public Color getFGCol() {
	if (baka!=null) {
	    return baka.getDispColor();
	}
	return fgcol;
    }
    public Color getBGCol() {
	switch (type) {
	case 0: //room floor
	    return bgcol;
	case 1: //room wall
	    return Color.LIGHT_GRAY;
	case 2: //debug case: room wall orthogonal
	    return Color.LIGHT_GRAY;
	case 3: //corridor floor
	    return Color.DARK_GRAY;
	default: //tunnel wall
	    return bgcol;
	}
    }

    public Creature getBaka() {return baka;}
    public ArrayList<Item> getItems() {return items;}

    public void setType(int n) {type = n;}
    public void setBGCol(Color c) {bgcol = c;}
    public void setBaka(Creature c) {baka = c;}
    public void setDebugOverride(char c) {debugoverride = c;}

    public Tile getTileAt(Direction d) {return lev.getTile(x+d.getx(),y+d.gety());}
    public boolean isWalkable() {
	if (type==0 || type==3) {return true;}
	return false;
    }
}
