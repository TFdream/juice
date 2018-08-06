package juice.spi.impl;

import juice.spi.Car;

/**
 * @author Ricky Fung
 */
public class AudiCar implements Car {

    @Override
    public void run() {
        System.out.println("Audi run...");
    }
}
