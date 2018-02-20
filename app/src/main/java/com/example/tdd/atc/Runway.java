package com.example.tdd.atc;

/**
 * Created by Pavan.VijayaNar on 9/3/2017.
 */

public interface Runway {
    void allowLanding(Plane plane);
    boolean isFree();
}
