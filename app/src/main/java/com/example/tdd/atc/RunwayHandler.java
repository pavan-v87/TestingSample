package com.example.tdd.atc;

/**
 * Created by Pavan.VijayaNar on 9/3/2017.
 */

public interface RunwayHandler {
    interface Request {
        void onRunwayBusy();
        void onRunwayAvailable(Runway runway);
    }
    boolean allowLanding(Plane plane);
}
