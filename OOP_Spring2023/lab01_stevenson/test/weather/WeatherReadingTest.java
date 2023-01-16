package weather;

import weather.WeatherReading;
import weather.StevensonReading;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Tests variouis scenarios for a Stevenson Reading
 */
public class WeatherReadingTest {

  private WeatherReading reading;

  /**
   * Create a test object
   */
  @Before
  public void setUp() {
    reading = createReading(30, 20, 15, 1);
  }


  /**
   * Creates a new Reading object
   * 
   * @param air_temp_celcius the air temperature in Celsius
   * @param dew_point_celcius the dew point temperature in Celsius
   * @param wind_speed_mph the non-negative wind speed in miles per hour
   * @param total_rain_received_24h_mm the non-negative total rain received in the last 24 hours in millimeters
   * @return a StevensonReading object
   */
  protected WeatherReading createReading(double air_temp_celcius, double dew_point_celcius, double wind_speed_mph, double total_rain_received_24h_mm) {
    return new StevensonReading(air_temp_celcius, dew_point_celcius, wind_speed_mph, total_rain_received_24h_mm);
  }

  /**
   * Tests if the basic output is working as expected
   */
  @Test
  public void testToString() {
    String expected = "Reading: T = 30, D = 20, v = 15, rain = 1";
    assertEquals(expected, reading.toString());
  }
  
  /**
   * Validate the equals method
   */
  @Test
  public void testEquals() {
    assertTrue(reading.equals(reading));
    assertFalse(reading.equals(createReading(10,5,3,1)));
  }

  /**
   * Validate equality based on hash
   */
  @Test
  public void testHashEquality() {
    assertEquals(reading.hashCode(), createReading(30, 20, 15, 1).hashCode());
  }
  
  /**
   * Validate the Relative Humidity calculation method
   */
  @Test
  public void testRelativeHumidity() {
    reading = createReading(47.178686, 38.409985, 15.453376, 28);
    assertEquals(51, reading.getRelativeHumidity());
  }

  /**
   * Validate the relative Humidity method
   */
  @Test
  public void testHeatIndex() {
    assertEquals(31, reading.getHeatIndex());
  }

  /**
   * Validate the wind chill method
   */
  @Test
  public void testWindChill() {
    assertEquals(90, reading.getWindChill());
  }
  
  
  /**
   * Testing valid input into the app when instantiating an object
   * 
   * Test what happens when the temp is lower than the dew point
   * Passes with an IllegalArgumentException when instantiating the object
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDewPointTemp() {
    createReading(10, 20, 15, 1);
  }

  /**
   * Tests that the wind speed is greater than zero
   * Passes with an IllegalArgumentException when instantiating the object
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWindSpeed() {
    createReading(30, 20, -5, 1);
  }

  /**
   * Tests that the rain is not negative
   * Passes with an IllegalArgumentException when instantiating the object
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTotalRain() {
    createReading(30, 20, 5, -1);
  }

  /**
   * Tests that the humidity is not lower than zero or higher than 100
   * Passes with an IllegalArgumentException when calculating humidity
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRelativeHumidity() {
    
    reading = createReading(47, 100, 1, 28);
    reading.getRelativeHumidity();
    
  }
}