import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Band_instantiatesCorrectly_true() {
    Band myBand = new Band("MOFRO");
    assertEquals(true, myBand instanceof Band);
  }

  @Test
  public void getName_taskInstantiatesWithName_String() {
    Band myBand = new Band("MOFRO");
    assertEquals("MOFRO", myBand.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Band.all().size(), 0);
    }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Band firstBand = new Band("MOFRO");
    Band secondBand = new Band("MOFRO");
    assertTrue(firstBand.equals(secondBand));
  }

  @Test
  public void save_returnsTrueIfNamesAretheSame() {
    Band myBand = new Band("MOFRO");
    myBand.save();
    assertTrue(Band.all().get(0).equals(myBand));
  }
  @Test
  public void save_assignsIdToObject() {
    Band myBand = new Band("MOFRO");
    myBand.save();
    Band savedBand = Band.all().get(0);
    assertEquals(myBand.getId(), savedBand.getId());
  }
  @Test
  public void find_findsBandInDatabase_true() {
    Band myBand = new Band("MOFRO");
    myBand.save();
    Band savedBand = Band.find(myBand.getId());
    assertTrue(myBand.equals(savedBand));
  }

  @Test
  public void update_updatesBandName_true() {
    Band myBand = new Band("MOFRO");
    myBand.save();
    myBand.update("Denny and the Dennysingers");
    assertEquals("Denny and the Dennysingers", Band.find(myBand.getId()).getName());
  }

  @Test
  public void delete_deletesBand_true() {
    Band myBand = new Band("MOFRO");
    myBand.save();
    int myBandId = myBand.getId();
    myBand.delete();
    assertEquals(null, Band.find(myBandId));
  }

  @Test
  public void addVenue_addsVenueToBand() {
    Venue myVenue = new Venue("ThunderDome");
    myVenue.save();
    Band myBand = new Band("MOFRO");
    myBand.save();
    myBand.addVenue(myVenue);
    Venue savedVenue = myBand.getVenues().get(0);
    assertTrue(myVenue.equals(savedVenue));
  }

  @Test
  public void getVenues_returnsAllVenues_List() {
    Venue myVenue = new Venue("ThunderDome");
    myVenue.save();
    Band myBand = new Band("MOFRO");
    myBand.save();
    myBand.addVenue(myVenue);
    List savedVenues = myBand.getVenues();
    assertEquals(1, savedVenues.size());
  }

  @Test
  public void delete_deletesAllBandsAndVenuesAssociations() {
    Venue myVenue = new Venue("ThunderDome");
    myVenue.save();
    Band myBand = new Band("MOFRO");
    myBand.save();
    myBand.addVenue(myVenue);
    myBand.delete();
    assertEquals(0, myVenue.getBands().size());
  }


}
