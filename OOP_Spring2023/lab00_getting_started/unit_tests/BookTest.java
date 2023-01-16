import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import person.Book;
import person.Person;

public class BookTest {

  private Person testPerson;
  private Book myBook;

  @Before
  public void setUp() {
    testPerson = new Person("John", "Doe", 1945);
    myBook = new Book("The Greatest Book", testPerson, (float) 20.99);
  }

  @Test
  public void testTitle() {
    assertEquals("The Greatest Book", myBook.getTitle());

  }

  @Test
  public void testAuthor() {
    assertEquals("Doe", myBook.getAuthor().getLastName());

  }

  @Test
  public void testPrice() {
    Assert.assertEquals(20.99f, myBook.getPrice(), 0.01);

  }

}
