public class Level {
    private Tile[][] tiles;
    
    public Level() {
	tiles = new Tile[40][20];
	for (int y=0; y<tiles[0].length; y++) {
	    for (int x=0; x<tiles.length; x++) {
	        tiles[x][y] = new Tile();
	    }
	}
    }

    public Tile[][] getTiles() {return tiles;}
}
