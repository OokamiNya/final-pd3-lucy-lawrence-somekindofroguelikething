import java.awt.Color;

public class Tile {
    private int type;
    private Color fgcol, bgcol;
    
    public Tile() {
	type = 0;
	fgcol = Color.LIGHT_GRAY;
	bgcol = Color.BLACK;
    }

    public char getChar() {
	switch (type) {
	case 0: 
	    return '.';
	case 1: 
	    return '#';
	default:
	    return '#';
	}
    }

    public Color getFGCol() {return fgcol;}
    public Color getBGCol() {return bgcol;}
}
