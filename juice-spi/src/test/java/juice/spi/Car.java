package juice.spi;

import juice.spi.annotation.SPI;

/**
 * @author Ricky Fung
 */
@SPI("audi")
public interface Car {

    void run();
}
