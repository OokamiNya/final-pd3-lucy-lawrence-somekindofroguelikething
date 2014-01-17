import java.awt.Color;

public class Tile {
    private int type;
    private Color fgcol, bgcol;
    private Creature baka;
    
    public Tile() {
	type = -1;
	fgcol = Color.LIGHT_GRAY;
	bgcol = Color.BLACK;
    }

    public char getChar() {
	if (baka!=null) {
	    return baka.getDispChar();
	}
	switch (type) {
	case 0: //solid floor
	    return '.';
	case 1: //room wall
	    return '#';
	case 2: //debug case: room wall orthogonal
	    return 'O';
	default: //tunnel wall
	    return '-';
	}
    }

    public int getType() {return type;}
    public Color getFGCol() {
	if (baka!=null) {
	    return baka.getDispColor();
	}
	return fgcol;
    }
    public Color getBGCol() {return bgcol;}

    public void setType(int n) {type = n;}
    public void setBaka(Creature c) {baka = c;}
}
