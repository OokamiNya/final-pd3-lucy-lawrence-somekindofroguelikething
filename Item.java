import java.io.*;
import java.util.*;

public class Item {

    ArrayList<String> items = new ArrayList<String>();
    Random random = new Random();
    int i = random.nextInt(items.size());

    public void makeList() throws IOException {
        File file = new File("Items.txt");
	Scanner s = new Scanner(file);

	while (s.hasNext()) {
	    items.add(s.next());
	}
	s.close();
    }
   
    public char getChar() {
	for (i=i; i<=items.size(); i++) {
	    if (i !=0 && i % 3 == 0) {
		i = i;
		break;
	    }
	}
	
	String s = items.get(i);
	char c = s.charAt(0);
	return c;
    }
    
    public String getName() {
	for (i=i; i<=items.size(); i++) {
	    if (i == 0) {
		break;
	    }
	    if (i % 3 == 1) {
		i = i;
		break;
	    }
	}
	return items.get(i);
    }   	
}
