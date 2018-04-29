package elliottkatz.burnout.player;


public enum Player {

    ROM,
    KAT,
    ;

    public static Player getByName(String name) {
        if (name.equals("ROM")) {
            return ROM;
        } else if (name.equals("KAT")) {
            return KAT;
        }
        else {
            throw new IllegalArgumentException();
        }
    }
    
    public static Player getOtherPlayer(String currentPlayer) {
    	switch (getByName(currentPlayer)) {
    	case ROM: 
    		return KAT;
    	case KAT: 
    	default:
    		return ROM;
    	}
    }

}
