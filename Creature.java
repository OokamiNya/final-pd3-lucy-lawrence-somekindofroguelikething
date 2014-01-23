import java.awt.Color;

public class Creature {
    private Tile curtile;
    protected char displayChar = 'X';
    protected Color displayColor = Color.WHITE;

    public Creature() {
	
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
    
    public char getDispChar() {return displayChar;}
    public Color getDispColor() {return displayColor;}

    public void setTile(Tile t) {curtile=t;}
}
