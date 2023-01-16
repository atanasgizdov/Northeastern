package person;

/**
 * This class represents a person The person has a first name, last name and an year of birth.
 */
public class Person {
  private String firstName;
  private String lastName;
  private int yearOfBirth;

  /**
   * Constructs a Person object and initializes it to the given first name, last name and year of
   * birth.
   *
   * @param pfirstName   the first name of this person
   * @param plastName    the last name of this person
   * @param pyearOfBirth the year of birth of this person
   */

  public Person(String pfirstName, String plastName, int pyearOfBirth) {
    this.firstName = pfirstName;
    this.lastName = plastName;
    this.yearOfBirth = pyearOfBirth;
  }

  /**
   * Get the first name of this person.
   *
   * @return the first name of this person
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * Return the last name of this person.
   *
   * @return the last name of this person
   */

  public String getLastName() {
    return this.lastName;
  }

  /**
   * Return the year of birth of this person.
   *
   * @return the year of birth of this person
   */
  public int getYearOfBirth() {
    return this.yearOfBirth;
  }
}

