public class Animal {
    private final int MAX_HEALTH_POINTS = 100;
    private int currentHealth;
    private int currentHunger;
    private int currentThirsty;
    private int currentWeight;

    /**
     * 
     */
    public Animal(int _weight) {
        this.currentHealth = MAX_HEALTH_POINTS;
        this.currentHunger = 0;
        this.currentThirsty = 0;
        this.currentWeight = _weight;
    }
}