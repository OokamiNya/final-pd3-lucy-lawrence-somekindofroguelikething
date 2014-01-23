import java.awt.Color;
import java.awt.event.*;
import java.util.*;

public class Game {
    private Gui gui = new Gui(this);
    private Level[] levels;
    private Level curlevel;
    private Player p1 = new Player(); 
    private Random rng = new Random();
    private int turncount = 0;
    
    public Game() {
	levels = new Level[10];
	levels[0] = new Level();
	curlevel = levels[0];
	initGame();
	getPane().refresh();
    }

    public void refreshMap() {
	Tile curtile;
	for (int y=0; y<curlevel.getTiles()[0].length; y++) {
	    for (int x=0; x<curlevel.getTiles().length; x++) {
		curtile = curlevel.getTiles()[x][y];
		getPane().putChar(curtile.getChar(),
				  x+1,y+1,
				  curtile.getFGCol(),curtile.getBGCol());
	    }
	}
    }

    public void refreshStats() {
	getPane().putString("yolo",42,1);
	getPane().putString("Turn: "+turncount,42,2);
    }

    public void initGame() {
        spawnMob(p1);
	getPane().putRectangle(' ',0,0,42,22,Color.WHITE,Color.WHITE);
	getPane().putRectangle(' ',0,21,80,4,Color.WHITE,Color.WHITE);
	getPane().putRectangle(' ',41,0,39,22,Color.WHITE,Color.WHITE);
	refreshEverything();
    }

    public void gameLoop(KeyEvent e) {
	Command.keyEventToCommand(e);
        if(doCommand(Command.keyEventToCommand(e))) {
	    stuff();
	}
	refreshEverything();
    }
    
    public void refreshEverything() {
	refreshMap();
	refreshStats();
	getPane().refresh();
    }

    public void stuff() {
	turncount++;
    }

    public boolean doCommand(Command c) {
	switch (c) {
	case MOVE_UP: return p1.move(Direction.UP);
	case MOVE_DOWN: return p1.move(Direction.DOWN);
	case MOVE_LEFT: return p1.move(Direction.LEFT);
	case MOVE_RIGHT: return p1.move(Direction.RIGHT);
	case MOVE_UPLEFT: return p1.move(Direction.UPLEFT);
	case MOVE_UPRIGHT: return p1.move(Direction.UPRIGHT);
	case MOVE_DOWNLEFT: return p1.move(Direction.DOWNLEFT);
	case MOVE_DOWNRIGHT: return p1.move(Direction.DOWNRIGHT);
	case WAIT: return p1.move(Direction.NONE);
	case INVALID_COMMAND: System.out.println("mfw"); return false;
	default: System.out.println("whatever"); return false;
	}
    }

    public void spawnMob(Creature c) {
	Tile t = curlevel.getRoomTiles()[rng.nextInt(curlevel.getRoomTiles().length)];
        t.setBaka(c);
	c.setTile(t);
    }

    public Gui getGui() {return gui;}
    public SwingPane getPane() {return gui.getPane();}
    public Level[] getLevels() {return levels;}
    public Level getCurLevel() {return curlevel;}
    
}
