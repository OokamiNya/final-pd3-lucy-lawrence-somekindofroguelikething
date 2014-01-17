import java.util.*;

public class Level {
    final private int gridWidth = 40;
    final private int gridHeight = 20;
    private Tile[][] tiles = new Tile[gridWidth][gridHeight];
    private Room[] rooms;
    private boolean[][] valid = new boolean[gridWidth][gridHeight];
    private Random rng = new Random();
    
    private class Room {
	private Tile[] walls;
	private boolean connected = false;

	public Room(int w, int h, int x, int y) {
	    walls = new Tile[w+w+h+h];
	    int n=0;
	    for (int i=0; i<w; i++) {
		walls[n]=tiles[x+i][y-1];
		n++;
	    }
	    for (int i=0; i<w; i++) {
		walls[n]=tiles[x+i][y+h];
		n++;
	    }
	    for (int i=0; i<h; i++) {
		walls[n]=tiles[x-1][y+i];
		n++;
	    }
	    for (int i=0; i<h; i++) {
		walls[n]=tiles[x+w][y+i];
		n++;
	    }
	    /*for (int i=0; i<walls.length; i++) {
		walls[i].setType(2);
		}*/
	}
    }

    public Level() {
	for (int y=0; y<tiles[0].length; y++) {
	    for (int x=0; x<tiles.length; x++) {
	        tiles[x][y] = new Tile();
	        valid[x][y] = true;
	    }
	}
	rooms = new Room[rng.nextInt(5)+5];
	int curroom = 0;
	Room r;
	while(curroom < rooms.length) {
	    r=makeRoom(0);
	    if (r!=null) {
		rooms[curroom]=r;
		curroom++;
	    } else {
		break;
	    }
	}
    }

    public Room makeRoom(int fails) {
	int width = rng.nextInt(8)+2;
	int height = rng.nextInt(8)+2;
	
	int x = rng.nextInt(gridWidth-width-1)+1;
	int y = rng.nextInt(gridHeight-height-1)+1;
	
	for (int i=y-3; i<y+height+3; i++) {
	    for (int j=x-3; j<x+width+3; j++) {
		if (j>=0 && j<gridWidth && i>=0 && i<gridHeight) {
		    if (!valid[j][i]) {
			if (fails>100) {return null;}
			else {return makeRoom(fails+1);}
		    }
		}
	    }
	}

	for (int i=y; i<y+height; i++) {
	    for (int j=x; j<x+width; j++) {
		tiles[j][i].setType(0);
		valid[j][i] = false;
	    }
	}
        
	for (int i=y-1; i<y+height+1; i++) {
	    for (int j=x-1; j<x+width+1; j++) {
	        if (tiles[j][i].getType()!=0) 
		    tiles[j][i].setType(1);
	    }
	}
	return new Room(width,height,x,y);
    }

    public Tile[][] getTiles() {return tiles;}
    public Tile getTile(int x, int y) {return tiles[x][y];}
}
