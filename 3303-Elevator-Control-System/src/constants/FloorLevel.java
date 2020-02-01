package constants;

/**
 * enum representing the floor levels
 * 
 * @author John Warde
 */
public enum FloorLevel {
	
	Floor1 ("Floor 1"),
    Floor2 ("Floor 2"),
    Floor3 ("Floor 3"),
    Floor4 ("Floor 4"),
    Floor5 ("Floor 5"),
    Floor6 ("Floor 6");

    private final String floor;       

    private FloorLevel(String floor) {
        this.floor = floor;
    }

    public boolean equalsName(String otherName) {
        return floor.equals(otherName);
    }

    public String toString() {
       return this.floor;
    }

}
