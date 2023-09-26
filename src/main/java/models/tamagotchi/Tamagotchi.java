package models.tamagotchi;

public abstract class Tamagotchi {
    private final float MAX_HEALTH_POINTS = 100;
    private final float MAX_ENERGY = 100;

    protected float currentHealth;
    protected float currentEnergy;

    public Tamagotchi() {
        this.currentHealth = MAX_HEALTH_POINTS;
        this.currentEnergy = MAX_ENERGY;
    }

    public float GetMaxHealth() {
        return this.MAX_HEALTH_POINTS;
    }
}
