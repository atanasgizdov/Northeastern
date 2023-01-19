package weather;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import weather.StevensonReading;
import weather.WeatherReading;

/**
 * Tests various scenarios for a Stevenson Reading.
 */
public class WeatherReadingTest {

  private WeatherReading reading;

  /**
   * Create a test object.
   */
  @Before
  public void setUp() {
    reading = createReading(30, 20, 15, 1);
  }


  /**
   * Creates a new Reading object.
   * 
   * @param airTempCelcius the air temperature in Celsius
   * @param dewPointCelcius the dew point temperature in Celsius
   * @param windSpeedMph the non-negative wind speed in miles per hour
   * @param totalRainReceived the non-negative total rain received in the last 24 hours
   * @return a StevensonReading object
   */
  protected WeatherReading createReading(
      double airTempCelcius, 
      double dewPointCelcius, 
      double windSpeedMph, 
      double totalRainReceived) {
    return new StevensonReading(airTempCelcius, dewPointCelcius, windSpeedMph, totalRainReceived);
  }

  /**
   * Tests getTemperature output.
   */
  @Test
  public void getTemperatureTest() {
    assertEquals(30, reading.getTemperature());
  }
  
  /**
   * Tests getDewPoint output.
   */
  @Test
  public void getDewPointTest() {
    assertEquals(20, reading.getDewPoint());
  }
  
  /**
   * Tests getWindSpeed output.
   */
  @Test
  public void getWindSpeedTest() {
    assertEquals(15, reading.getWindSpeed());
  }
  
  /**
   * Tests total rain output.
   */
  @Test
  public void getTotalRainTest() {
    assertEquals(1, reading.getTotalRain());
  }
  
  
  /**
   * Tests if the basic output is working as expected.
   */
  @Test
  public void testToString() {
    String expected = "Reading: T = 30, D = 20, v = 15, rain = 1";
    assertEquals(expected, reading.toString());
  }
  
  /**
   * Validate the equals method.
   */
  @Test
  public void testEquals() {
    assertTrue(reading.equals(reading));
    assertFalse(reading.equals(createReading(10, 5, 3, 1)));
  }

  /**
   * Validate equality based on hash.
   */
  @Test
  public void testHashEquality() {
    assertEquals(reading.hashCode(), createReading(30, 20, 15, 1).hashCode());
  }
  
  /**
   * Validate the Relative Humidity calculation method.
   */
  @Test
  public void testRelativeHumidity() {
    reading = createReading(72.729961, 57.282306, 22.651818, 65);
    assertEquals(83, reading.getRelativeHumidity());
  }

  /**
   * Validate the relative Humidity method.
   */
  @Test
  public void testHeatIndex() {
    reading = createReading(20, 10, 15, 25);
    assertEquals(19, reading.getHeatIndex());
  }

  /**
   * Validate the wind chill method.
   */
  @Test
  public void testWindChill() {
    reading = createReading(99.596363, 98.579070, 7.347576, 59);
    assertEquals(117, reading.getWindChill());
  }
  
  
  /**
   * Testing valid input into the app when instantiating an object.
   * 
   * Test what happens when the temp is lower than the dew point
   * Passes with an IllegalArgumentException when instantiating the object
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDewPointTemp() {
    createReading(10, 20, 15, 1);
  }

  /**
   * Tests that the wind speed is greater than zero.
   * Passes with an IllegalArgumentException when instantiating the object
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWindSpeed() {
    createReading(30, 20, -5, 1);
  }

  /**
   * Tests that the rain is not negative.
   * Passes with an IllegalArgumentException when instantiating the object
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTotalRain() {
    createReading(30, 20, 5, -1);
  }

  /**
   * Tests that the humidity is not lower than zero or higher than 100.
   * Passes with an IllegalArgumentException when calculating humidity
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRelativeHumidity() {
    
    reading = createReading(47, 100, 1, 28);
    reading.getRelativeHumidity();
    
  }
}