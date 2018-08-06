package juice.spi;

import org.junit.Test;

/**
 * @author Ricky Fung
 */
public class ExtensionLoaderTest {

    @Test
    public void testSPI() {

        ExtensionLoader<Car> loader = ExtensionLoader.getExtensionLoader(Car.class);
        Car car = loader.getDefaultExtension();
        car.run();
    }
}
