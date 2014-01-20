import java.util.*;

public class Driver {
    public static void main(String[] args) {
	TreeSet<Position> h = new TreeSet<Position>();
	Position p1 = new Position(0,0);
	Position p2 = new Position(1,0);
	h.add(p1);
	System.out.println(p1.equals(p2));
	System.out.println(h.contains(p1));
	System.out.println(h.contains(p2));

	TreeSet<Integer> h2 = new TreeSet<Integer>();
	Integer x = new Integer(0);
	Integer y = new Integer(0);
	h2.add(x);
	System.out.println(x.equals(y));
	System.out.println(h2.contains(x));
	System.out.println(h2.contains(y));
	
	Game g = new Game();

	//g.paneTest();
	g.refreshMap();
	
    }
}
