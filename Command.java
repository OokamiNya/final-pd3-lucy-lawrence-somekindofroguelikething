import java.awt.event.*;

public enum Command {
    MOVE_UP,
    MOVE_DOWN,
    MOVE_LEFT,
    MOVE_RIGHT,
    MOVE_UPLEFT,
    MOVE_UPRIGHT,
    MOVE_DOWNLEFT,
    MOVE_DOWNRIGHT,
    WAIT,
    INVALID_COMMAND
    ;
    
    public static Command keyEventToCommand(KeyEvent e) {
	if (e==null) {return INVALID_COMMAND;}
	switch (e.getKeyChar()) {
	case '1': return MOVE_DOWNLEFT;
	case '2': return MOVE_DOWN;
	case '3': return MOVE_DOWNRIGHT;
	case '4': return MOVE_LEFT;
	case '5': return WAIT;
	case '6': return MOVE_RIGHT;
	case '7': return MOVE_UPLEFT;
	case '8': return MOVE_UP;
	case '9': return MOVE_UPRIGHT;
	default:  return INVALID_COMMAND;
	}
    }
}
