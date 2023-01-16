package weather;

import weather.WeatherReading;
import weather.StevensonReading;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Tests all the different possibilities for a Stevenson Reading.
 */
public class WeatherReadingTest {

  private WeatherReading reading;

  /**
   * Setting up the object used for the test.
   */
  @Before
  public void setUp() {
    reading = createReading(25, 19, 34, 9);
  }


  /**
   * Creates a new StevensonReading object.
   * @param airT - Air Temperature in Celsius.
   * @param dewT - Dew Point Temperature in Celsius.
   * @param v - Wind Speed in miles/hours.
   * @param rain - Total Amount of rain in the area in mm.
   * @return a new StevensonReading object.
   */
  protected WeatherReading createReading(int airT, int dewT, int v, int rain) {
    return new StevensonReading(airT, dewT, v, rain);
  }


  /**
   * Tests the response for invalid Dew point Temperature.
   * True Condition: DewPointTemperature should be less than AirTemperature.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDewPointTemp() {
    createReading(19, 20, 35, 10);
  }

  /**
   * Tests the response for invalid Wind Speed.
   * True Condition: WindSpeed is greater than or equal to 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWindSpeed() {
    createReading(20, 18, -100, 11);
  }

  /**
   * Tests the response for invalid total amount of rain.
   * True Condition: TotalRain is greater than or equal to 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTotalRain() {
    createReading(21, 10, 2, -10);
  }

  /**
   * Tests the response for invalid RelativeHumidity.
   * True Condition: RelativeHumidity is between 0 and 100.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidR() {
    createReading(55, -10, 30, 12);
  }

  /**
   * Tests if the RelativeHumidity is calculated correctly according to the given formula.
   */
  @Test
  public void testR() {
    assertEquals(71, reading.getRelativeHumidity());
  }

  /**
   * Tests if the HeatIndex is calculated correctly according to the given formula.
   */
  @Test
  public void testHI() {
    assertEquals(26, reading.getHeatIndex());
  }

  /**
   * Tests if the WindChill is calculated correctly according to the given formula.
   */
  @Test
  public void testWindChill() {
    assertEquals(80, reading.getWindChill());
  }

  /**
   * Tests if the overridden equals() is working as expected.
   */
  @Test
  public void testEquals() {
    assertTrue(reading.equals(reading));
    assertFalse(reading.equals(createReading(19, 10, 35, 10)));
  }

  /**
   * Tests if the overridden hashCode() is working as expected.
   */
  @Test
  public void testHashCode() {
    assertEquals(reading.hashCode(), createReading(25, 19, 34, 9).hashCode());
  }

  /**
   * Tests if the overridden toString() is working as expected.
   */
  @Test
  public void testToString() {
    String expected = "Reading: T = 25, D = 20, v = 34, rain = 9";
    assertEquals(expected, reading.toString());
  }
}