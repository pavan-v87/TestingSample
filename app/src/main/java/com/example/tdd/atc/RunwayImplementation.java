package com.example.tdd.atc;

import android.graphics.Path;

/**
 * Created by Pavan.VijayaNar on 9/3/2017.
 */

public class RunwayImplementation extends Path implements Runway {

    private boolean free = true;

    private RunwayImplementation(){}

    Runway newInstance(Path runwayPath) {
        set(runwayPath);
        return this;
    }

    @Override
    public void allowLanding(Plane plane) {
        if (plane != null) {
            free = false;
            plane.landOn(this);
            free = true;
        }
    }

    @Override
    public boolean isFree() {
        return free;
    }
}
