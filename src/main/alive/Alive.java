package main.alive;

import main.disease.*;

public class Alive {

    private double resistance;
    private State etat;
    private Disease maladie;
    private double speed;

    public Alive(double res, double spe, State sta, Disease mal) {
        this.resistance = res * (1 + Math.random() * (0.2) - Math.random() * (0.2));
        this.speed = spe * (1 + Math.random() * (0.2) - Math.random() * (0.2));
        this.etat = sta;
        this.maladie = mal;
    }

    public Alive(double res, double spe) {
        this(res, spe, State.Healthy, new NullDisease());
    }

    public State getEtat() {
        return this.etat;
    }

    public double getSpeed() {
        return this.speed;
    }

    public double getResistance() {
        return this.resistance;
    }

    public Disease getMaladie() {
        return this.maladie;
    }

    public void setEtat(State e) {
        this.etat = e;
    }

    public void setSpeed(double s) {
        this.speed = s;
    }

    public void setResistance(double r) {
        this.resistance = r;
    }

    public void setMaladie(Disease mal) {
        this.maladie = mal;
    }

    public boolean equals(Alive alive) {
      if (this.resistance == alive.getResistance() && this.speed == alive.getSpeed()
              && this.maladie.equals(alive.getMaladie()) && this.etat.equals(alive.getEtat())) {return true;}
      else {return false;}
    }

}
