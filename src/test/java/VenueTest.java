import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Venue_instantiatesCorrectly_true() {
    Venue myVenue = new Venue("CornDome");
    assertEquals(true, myVenue instanceof Venue);
  }

  @Test
  public void getName_categoryInstantiatesWithName_String() {
    Venue myVenue = new Venue("CornDome");
    assertEquals("CornDome", myVenue.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Venue.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Venue firstVenue = new Venue("CornDome");
    Venue secondVenue = new Venue("CornDome");
    assertTrue(firstVenue.equals(secondVenue));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Venue myVenue = new Venue("CornDome");
    myVenue.save();
    assertTrue(Venue.all().get(0).equals(myVenue));
  }

  @Test
  public void save_assignsIdToObject() {
    Venue myVenue = new Venue("CornDome");
    myVenue.save();
    Venue savedVenue = Venue.all().get(0);
    assertEquals(myVenue.getId(), savedVenue.getId());
  }

  @Test
  public void find_findVenueInDatabase_true() {
    Venue myVenue = new Venue("CornDome");
    myVenue.save();
    Venue savedVenue = Venue.find(myVenue.getId());
    assertTrue(myVenue.equals(savedVenue));
  }

  @Test
  public void addBand_addsBandToVenue_true() {
    Venue myVenue = new Venue("CornDome");
    myVenue.save();
    Band myBand = new Band("Egregious Philbin");
    myBand.save();
    myVenue.addBand(myBand);
    Band savedBand = myVenue.getBands().get(0);
    assertTrue(myBand.equals(savedBand));
  }

  @Test
  public void getBands_returnsAllBands_List() {
    Venue myVenue = new Venue("CornDome");
    myVenue.save();
    Band myBand = new Band("Egregious Philbin");
    myBand.save();
    myVenue.addBand(myBand);
    List savedBands = myVenue.getBands();
    assertEquals(1, savedBands.size());
  }

  @Test
  public void delete_deletesAllBandsAndVenuesAssociations() {
    Venue myVenue = new Venue("CornDome");
    myVenue.save();
    Band myBand = new Band("Egregious Philbin");
    myBand.save();
    myVenue.addBand(myBand);
    myVenue.delete();
    assertEquals(0, myBand.getVenues().size());
  }

}
