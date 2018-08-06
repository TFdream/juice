package juice.spi.impl;

import juice.spi.Car;

/**
 * @author Ricky Fung
 */
public class BenzCar implements Car {

    @Override
    public void run() {
        System.out.println("Benz run...");
    }
}
