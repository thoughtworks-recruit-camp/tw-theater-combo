package repository;

import java.util.Random;

public class Receipt {
    private final String carNumber;
    private final int duration;
    private final int cost;

    public Receipt(String carNumber) {
        this.carNumber = carNumber;
        this.duration = new Random().nextInt(24) + 1;
        this.cost = getCost(this.duration);
    }

    public String getCarNumber() {
        return carNumber;
    }

    public int getDuration() {
        return duration;
    }

    public int getCost() {
        return cost;
    }

    private int getCost(int duration) {
        if (duration <= 2) {
            return 0;
        } else if (duration <= 5) {
            return (duration - 2) * 5;
        } else {
            return 15 + (duration - 5) * 10;
        }
    }
}
