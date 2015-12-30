package main.simulator;

import java.awt.Color;

import main.map.*;
import main.alive.*;
import main.disease.*;

/**
 * A graphical view of the simulation grid. This interface defines all possible
 * different views.
 * 
 * @author Michael Kölling, David J. Barnes, Axel Aiello and Antoine Steyer
 * @version 2015.12.28
 */
public interface SimulatorView {
    /**
     * Define a color to be used for a given class of alive.
     * 
     * @param aliveClass
     *            The animal's Class object.
     * @param color
     *            The color to be used for the given class.
     */
    void setColor(Class aliveClass, Color color);

    /**
     * Determine whether the simulation should continue to run.
     * 
     * @return true If there is more than one species alive.
     */
    boolean isViable(Field field);

    /**
     * Show the current status of the field.
     * 
     * @param step
     *            Which iteration step it is.
     * @param field
     *            The field whose status is to be displayed.
     */
    void showStatus(int step, Field field);

    /**
     * Prepare for a new run.
     */
    void reset();
}