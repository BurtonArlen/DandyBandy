import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Band {
  private int id;
  private String band_name;

  public Band(String band_name) {
    this.band_name = band_name;
  }

  public String getName() {
    return band_name;
  }

  public int getId() {
    return id;
  }


  public static List<Band> all() {
    String sql = "SELECT id, band_name FROM bands";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  @Override
  public boolean equals(Object otherBand){
    if (!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return this.getName().equals(newBand.getName()) &&
             this.getId() == newBand.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands(band_name) VALUES (:band_name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("band_name", this.band_name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Band find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands where id=:id";
      Band band = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Band.class);
      return band;
    }
  }

  public void update(String newName) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE bands SET band_name = :band_name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("band_name", newName)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String deleteQuery = "DELETE FROM bands WHERE id = :id;";
        con.createQuery(deleteQuery)
          .addParameter("id", this.getId())
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM venues_bands WHERE band_id = :band_id";
        con.createQuery(joinDeleteQuery)
          .addParameter("band_id", this.getId())
          .executeUpdate();
    }
  }

  public void addVenue(Venue venue) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues_bands (venue_id, band_id) VALUES (:venue_id, :band_id)";
      con.createQuery(sql)
        .addParameter("venue_id", venue.getId())
        .addParameter("band_id", this.getId())
        .executeUpdate();
    }
  }

  public List<Venue> getVenues() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT venue_id FROM venues_bands WHERE band_id = :band_id";
      List<Integer> venue_ids = con.createQuery(joinQuery)
        .addParameter("band_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Venue> venues = new ArrayList<Venue>();

      for (Integer venue_id : venue_ids) {
        String bandQuery = "Select * From venues WHERE id = :venue_id";
        Venue venue = con.createQuery(bandQuery)
          .addParameter("venue_id", venue_id)
          .executeAndFetchFirst(Venue.class);
        venues.add(venue);
      }
      return venues;
    }
  }
}
