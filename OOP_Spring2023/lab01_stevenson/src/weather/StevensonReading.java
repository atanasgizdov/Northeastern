package weather;

import java.util.Objects;
import weather.WeatherReading;



/**
 * Readings from a weather station represent:
 *  the air temperature in Celsius.
 *  the dew point temperature in Celsius which cannot be greater than the air temperature.
 *  the non-negative wind speed in miles per hour.
 *  the non-negative total rain received in the last 24 hours in millimeters.
 *  The class also calculates Wind Chill, relative humidity and the heat index
 */
public final class StevensonReading implements WeatherReading {
  private final double airTempCelcius;
  private final double dewPointCelcius;
  private final double windSpeedMph;
  private final double totalRainReceived;
  private double relativeHumidity = 0;
  private double headIndex = 0;
  private double windChill = 0;

  /**
   * Constructs a weather reading from a station.
   *
   * @param airTempCelcius the air temperature in Celsius.
   * @param dewPointCelcius the dew point temperature in Celsius.
   * @param windSpeedMph the non-negative wind speed in miles per hour.
   * @param totalRainReceived the non-negative total rain received in the last 24 hours
   * @throws IllegalArgumentException if any argument is negative or greater than it should be.
   */
  public StevensonReading(
      double airTempCelcius, 
      double dewPointCelcius, 
      double windSpeedMph, 
      double totalRainReceived)
      
      throws IllegalArgumentException {
    
    if ((windSpeedMph < 0) || (totalRainReceived < 0)) {
      throw new IllegalArgumentException(
          "Negative durations are not supported");
   
    } else if ((dewPointCelcius > airTempCelcius)) {
      throw new IllegalArgumentException(
          "The Dew point cannot be larger than the Air temp");
    }
    
    this.airTempCelcius = airTempCelcius;
    this.dewPointCelcius = dewPointCelcius;
    this.windSpeedMph = windSpeedMph;
    this.totalRainReceived = totalRainReceived;
    
  }
  
  @Override
  public int getTemperature() {
    return (int) Math.round(airTempCelcius);
  }
  
  @Override
  public int getDewPoint() {
    return (int) Math.round(dewPointCelcius);
  }
  
  @Override
  public int getWindSpeed() {
    return (int) Math.round(windSpeedMph);
  }
  
  @Override
  public int getTotalRain() {
    return (int) Math.round(totalRainReceived);
  }
  
  /**
   * Calculate vapor pressure.
   * 
   * Below is removed code, given the requirements change for checking humidity range
   * if (relativeHumidity < 0 || relativeHumidity > 100) {
   *   throw new IllegalArgumentException(
   *       "Humidity cannot be higher than 100% or lower than 0%");
   * }
   * 
   */
  
  @Override
  public int getRelativeHumidity() {
    
    double saturatedVaporPressure = 
        6.11 * 10.00 * ((7.5 * this.airTempCelcius) / (237.3 + this.airTempCelcius));
    double actualVaporPressure = 
        6.11 * 10.00 * ((7.5 * this.dewPointCelcius) / (237.3 + this.dewPointCelcius));
    
    relativeHumidity = (actualVaporPressure / saturatedVaporPressure) * 100;

    
    return (int) Math.round(relativeHumidity);
  }
  
  /**
   * Calculates the Heat Index based on the formula provided in Lab 1.
   * 
   */
  @Override
  public int getHeatIndex() {
    
    headIndex = 
        -8.78469475556 + 1.61139411 * this.airTempCelcius 
        + 2.33854883889 * getRelativeHumidity() + -0.14611605 
        * this.airTempCelcius * getRelativeHumidity()
        + -0.012308094 * (Math.pow(this.airTempCelcius, 
            2)) + -0.0164248277778 * (Math.pow(getRelativeHumidity(), 2))
        + 0.002211732 * Math.pow(this.airTempCelcius, 2) * getRelativeHumidity() 
        + 0.00072546 * this.airTempCelcius * Math.pow(getRelativeHumidity(), 2)
        + -0.000003582 * Math.pow(this.airTempCelcius, 2) * Math.pow(getRelativeHumidity(), 2);

    
    return (int) headIndex;
  }
  
  
  /**
   * Calculates the Wind Chill based on the formula provided in Lab 1.
   * 
   */
  
  @Override
  public int getWindChill() {
    
    windChill = 
        35.74 
        + 0.6215 * celciusToFarenheit(this.airTempCelcius) 
        - 35.75 * Math.pow(this.windSpeedMph, 0.16)
        + 0.4275 * celciusToFarenheit(this.airTempCelcius) * Math.pow(this.windSpeedMph, 0.16);

    windChill = (windChill - 32) * (5.0 / 9.0);
    
    return (int) Math.round(windChill);
  }
  
  
  /**
   * Converts Celcius temps to Farenheit.
   * @param temperature temp in C
   * @return Farenheit Temperature
   */
  private static double celciusToFarenheit(double temperature) {
    
    double farenheitTemperature = temperature * 1.8 + 32;
    
    return farenheitTemperature;
  }

  
  @Override
  public String toString() {
    return "Reading: " + "T = " + (int) Math.round(this.airTempCelcius)
            + ", D = " + (int) Math.round(this.dewPointCelcius)
            + ", v = " + (int) Math.round(this.windSpeedMph)
            + ", rain = " + (int) Math.round(this.totalRainReceived);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StevensonReading that = (StevensonReading) o;
    
    return Double.compare(that.airTempCelcius, this.airTempCelcius) == 0 
        && Double.compare(that.dewPointCelcius, this.dewPointCelcius) == 0
        && Double.compare(that.windSpeedMph, this.windSpeedMph) == 0 
        && this.totalRainReceived == that.totalRainReceived;
  }


  @Override
  public int hashCode() {
    return Objects.hash(
        this.airTempCelcius, 
        this.dewPointCelcius, 
        this.windSpeedMph, 
        this.totalRainReceived);
  }
  
}

