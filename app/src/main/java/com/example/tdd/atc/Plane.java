package com.example.tdd.atc;

import android.graphics.Path;

/**
 * Created by Pavan.VijayaNar on 9/3/2017.
 */

public interface Plane {
    void getName();
    void landOn(Path runwayPath);
    Properties getProperties();
    interface Properties {
        int fuelLevel();
    }
}
