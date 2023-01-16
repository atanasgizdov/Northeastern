package weather;

import weather.WeatherReading;
import java.util.Objects;


/**
 * Readings from a weather station represent:
 *  the air temperature in Celsius
 *  the dew point temperature in Celsius which cannot be greater than the air temperature
 *  the non-negative wind speed in miles per hour
 *  the non-negative total rain received in the last 24 hours in millimeters (where millimeters is the smallest unit the total rain is measured in
 */
public final class StevensonReading implements WeatherReading {
  private final int air_temp_celcius;
  private final int dew_point_celcius;
  private final int wind_speed_mph;
  private final int total_rain_received_24h_mm;
  private int relative_humidity = 0;
  private double heat_index = 0;
  private double wind_chill = 0;

  /**
   * Constructs a weather reading from a station
   *
   * @param air_temp_celcius the air temperature in Celsius
   * @param dew_point_celcius the dew point temperature in Celsius
   * @param wind_speed_mph the non-negative wind speed in miles per hour
   * @param total_rain_received_24h_mm the non-negative total rain received in the last 24 hours in millimeters
   * @throws IllegalArgumentException if any argument is negative or greater than it should be
   */
  public StevensonReading (int air_temp_celcius, int dew_point_celcius, int wind_speed_mph, int total_rain_received_24h_mm)
      
      throws IllegalArgumentException {
    
    if ((wind_speed_mph <= 0) || (total_rain_received_24h_mm <= 0)) {
      throw new IllegalArgumentException(
          "Negative durations are not supported");
    }
    
    else if ((dew_point_celcius > air_temp_celcius)) {
      throw new IllegalArgumentException(
          "The Dew point cannot be larger than the Air temp");
      }
    
  
    int air_temp = air_temp_celcius;
    int dew_point = dew_point_celcius;
    int wind_speed = wind_speed_mph;
    int total_rain =  total_rain_received_24h_mm;
    
    /**
     * Initialize Constructor variables
     */
    this.air_temp_celcius = air_temp;
    this.dew_point_celcius = dew_point;
    this.wind_speed_mph = wind_speed;
    this.total_rain_received_24h_mm = total_rain;
    
  }
  
  @Override
  public int getTemperature() {
    return air_temp_celcius;
  }
  
  @Override
  public int getDewPoint() {
    return dew_point_celcius;
  }
  
  @Override
  public int getWindSpeed() {
    return wind_speed_mph;
  }
  
  @Override
  public int getTotalRain() {
    return total_rain_received_24h_mm;
  }
  
  @Override
  public int getRelativeHumidity() {
    
    relative_humidity = 5 * (this.dew_point_celcius - this.air_temp_celcius) + 100;
    if (relative_humidity < 0 || relative_humidity > 100) {
      throw new IllegalArgumentException(
          "Humidity cannot be higher than 100% or lower than 0%");
    }

    
    return relative_humidity;
  }
  
  @Override
  public int getHeatIndex() {
    
    heat_index = -8.78469475556 + 1.61139411 * this.air_temp_celcius + 2.33854883889 * getRelativeHumidity() + -0.14611605 * this.air_temp_celcius * getRelativeHumidity()
        + -0.012308094 * (Math.pow(this.air_temp_celcius, 2)) + -0.0164248277778 * (Math.pow(getRelativeHumidity(), 2))
        + 0.002211732 * Math.pow(this.air_temp_celcius, 2) * getRelativeHumidity() + 0.00072546 * this.air_temp_celcius * Math.pow(getRelativeHumidity(), 2)
        + -0.000003582 * Math.pow(this.air_temp_celcius, 2) * Math.pow(getRelativeHumidity(), 2);

    
    return (int) heat_index;
  }
  
  @Override
  public int getWindChill() {
    
    wind_chill = 35.74 + 0.6215 * (Celcius_to_Farenheit(this.air_temp_celcius)) - 35.75 * (Math.pow(this.wind_speed_mph, 0.16))
        + 0.4275 * (Celcius_to_Farenheit(this.air_temp_celcius)) * (Math.pow(this.wind_speed_mph, 0.16));

    return (int) wind_chill;
  }
  
  private static int Celcius_to_Farenheit (int temperature) {
    
    int farenheint_temperature = (int) (temperature * 1.8) + 32;
    
    return farenheint_temperature;
  }

  
  @Override
  public String toString() {
    return "Reading: "
            + "T = " + (int) Math.round(this.air_temp_celcius)
            + ", D = " + (int) Math.round(this.dew_point_celcius)
            + ", v = " + (int) Math.round(this.wind_speed_mph)
            + ", rain = " + this.total_rain_received_24h_mm;
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
    
    return Double.compare(that.air_temp_celcius, this.air_temp_celcius) == 0 && Double.compare(that.dew_point_celcius, this.dew_point_celcius) == 0
            && Double.compare(that.wind_speed_mph, this.wind_speed_mph) == 0 && this.total_rain_received_24h_mm == that.total_rain_received_24h_mm;
  }


  @Override
  public int hashCode() {
    return Objects.hash(this.air_temp_celcius, this.dew_point_celcius, this.wind_speed_mph, this.total_rain_received_24h_mm);
  }
  
}

