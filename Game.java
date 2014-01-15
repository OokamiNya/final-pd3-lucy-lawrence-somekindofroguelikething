import java.awt.Color;

public class Game {
    private Gui gui = new Gui();
    private Level[] levels;
    private Level curlevel;
    private Tile curtile;
    
    public Game() {
	levels = new Level[10];
	levels[0] = new Level();
	curlevel = levels[0];
    }

    public void paneTest() {
	for (int y=0; y<25; y++) {
	    for (int x=0; x<80; x++) {
		if ((x+y)%2==1) {getPane().putChar(' ',x,y,Color.WHITE,Color.DARK_GRAY);}
	        else {getPane().putChar(' ',x,y,Color.WHITE,Color.BLACK);}
		if (x%5==4 || y%5 ==4) {getPane().putChar(' ',x,y,Color.WHITE,Color.LIGHT_GRAY);}
	    }
	}
	getPane().refresh();
    }

    public void refreshMap() {
	for (int y=0; y<curlevel.getTiles()[0].length; y++) {
	    for (int x=0; x<curlevel.getTiles().length; x++) {
		curtile = curlevel.getTiles()[x][y];
		getPane().putChar(curtile.getChar(),
				  x+1,y+1,
				  curtile.getFGCol(),curtile.getBGCol());
	    }
	}
	getPane().refresh();
    }

    public Gui getGui() {return gui;}
    public SwingPane getPane() {return gui.getPane();}
    public Level[] getLevels() {return levels;}
    public Level getCurLevel() {return curlevel;}

    
}
