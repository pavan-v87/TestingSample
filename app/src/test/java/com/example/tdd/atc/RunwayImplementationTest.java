package com.example.tdd.atc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.verify;

/**
 * Created by Pavan.VijayaNar on 9/3/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class RunwayImplementationTest {

    private RunwayImplementation runway = new RunwayImplementation();
    @Mock Plane mockedPlane;

    @Test public void testLand() {
        runway.allowLanding(mockedPlane);
        verify(mockedPlane).landOn(runway);
    }

}