package main.simulator;

import main.utils.Randomizer;
import main.alive.*;
import main.map.Field;
import main.map.Location;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A simple predator-prey simulator, based on a rectangular field containing
 * rabbits and foxes.
 * 
 * @author David J. Barnes, Michael Kölling, Axel Aiello and Antoine Steyer
 * @version 2015.12.28
 */
public class Simulator {
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 200;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 200;
    // The probability that a human will be created in any given grid position.
    private static final double HUMAN_CREATION_PROBABILITY = 0.20;
    // The probability that a duck will be created in any given grid position.
    private static final double DUCK_CREATION_PROBABILITY = 0.02;
    // The probability that a pig will be created in any given grid position.
    private static final double PIG_CREATION_PROBABILITY = 0.02;
    // The probability that a chicken will be created in any given grid position.
    private static final double CHICKEN_CREATION_PROBABILITY = 0.02;

    // List of animals in the field.
    private List<Alive> alives;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private List<SimulatorView> views;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * 
     * @param depth
     *            Depth of the field. Must be greater than zero.
     * @param width
     *            Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        if (width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        alives = new ArrayList<>();
        field = new Field(depth, width);

        views = new ArrayList<>();

        SimulatorView view = new GridView(depth, width);
        view.setColor(Human.class, Color.BLACK);
        view.setColor(Pig.class, Color.BLUE);
        view.setColor(Chicken.class, Color.RED);
        view.setColor(Duck.class, Color.GREEN);
        views.add(view);

        view = new GraphView(500, 150, 500);
        view.setColor(Human.class, Color.BLACK);
        view.setColor(Pig.class, Color.BLUE);
        view.setColor(Chicken.class, Color.RED);
        view.setColor(Duck.class, Color.GREEN);
        views.add(view);

        // Setup a valid starting point.
        reset();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation() {
        simulate(4000);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * 
     * @param numSteps
     *            The number of steps to run for.
     */
    public void simulate(int numSteps) {
        for (int step = 1; step <= numSteps && views.get(0).isViable(field); step++) {
            simulateOneStep();
        }
    }

    /**
     * Run the simulation from its current state for a single step. Iterate over
     * the whole field updating the state of each fox and rabbit.
     */
    public void simulateOneStep() {
        step++;

        // Provide space for newborn animals.
        List<Alive> newAlives = new ArrayList<>();
        // Let all rabbits act.
        for (Iterator<Alive> it = alives.iterator(); it.hasNext();) {
            Alive alive = it.next();
            alive.act(newAlives);
            if (!alive.isAlive()) {
                it.remove();
            }
        }

        // Add the newly born foxes and rabbits to the main lists.
        alives.addAll(newAlives);

        updateViews();
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        step = 0;
        alives.clear();
        for (SimulatorView view : views) {
            view.reset();
        }

        populate();
        updateViews();
    }

    /**
     * Update all existing views.
     */
    private void updateViews() {
        for (SimulatorView view : views) {
            view.showStatus(step, field);
        }
    }

    /**
     * Randomly populate the field with foxes and rabbits.
     */
    private void populate() {
        Random rand = Randomizer.getRandom();
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                if (rand.nextDouble() <= HUMAN_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Human human = new Human(field, location);
                    alives.add(human);
                } else if (rand.nextDouble() <= PIG_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Pig pig = new Pig(field, location);
                    alives.add(pig);
                }  else if (rand.nextDouble() <= CHICKEN_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Chicken chicken = new Chicken(field, location);
                    alives.add(chicken);
                }  else if (rand.nextDouble() <= DUCK_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Duck duck = new Duck(field, location);
                    alives.add(duck);
                }
                // else leave the location empty.
            }
        }
    }
}