import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Venue {
  private int id;
  private String venue_name;
  private String hasNoName = "";


  public Venue(String venue_name) {
    this.venue_name = venue_name;
  }

  public String getName() {
    return venue_name;
  }

  public int getId() {
    return id;
  }

  public static List<Venue> all() {
    String sql = "SELECT id, venue_name FROM Venues";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }

  @Override
  public boolean equals(Object otherVenue) {
    if (!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return this.getName().equals(newVenue.getName()) &&
             this.getId() == newVenue.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO Venues(venue_name) VALUES (:venue_name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("venue_name", this.venue_name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Venue find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM Venues where id=:id";
      Venue venue = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Venue.class);
      return venue;
    }
  }

  public void addBand(Band band) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues_bands (venue_id, band_id) VALUES (:venue_id, :band_id)";
      con.createQuery(sql)
        .addParameter("venue_id", this.getId())
        .addParameter("band_id", band.getId())
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {

      String deleteQuery = "DELETE FROM venues WHERE id = :id";
        con.createQuery(deleteQuery)
          .addParameter("id", this.getId())
          .executeUpdate();

      String joinDeleteQuery = "DELETE FROM venues_bands WHERE venue_id = :venue_id";
        con.createQuery(joinDeleteQuery)
          .addParameter("venue_id", this.getId())
          .executeUpdate();
    }
  }

  // public void deleteLogic() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String deleteQuery = "DELETE * FROM venues WHERE venue_name = hasNoName;";
  //       con.createQuery(deleteQuery)
  //         .addParameter("venue_name", this.getName())
  //         .executeUpdate();
  //
  //     String joinDeleteQuery = "DELETE * FROM venues_bands WHERE venue_id = :venue_id";
  //       con.createQuery(joinDeleteQuery)
  //         .addParameter("venue_id", this.getId())
  //         .executeUpdate();
  //   }
  // }

  public void update(String newName) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE venues SET venue_name = :venue_name WHERE id = :id";
      con.createQuery(sql)
        .addParameter("venue_name", newName)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public List<Band> getBands() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT band_id FROM venues_bands WHERE venue_id = :venue_id";
      List<Integer> band_ids = con.createQuery(joinQuery)
        .addParameter("venue_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Band> bands = new ArrayList<Band>();

      for (Integer band_id : band_ids) {
        String bandQuery = "Select * From bands WHERE id = :band_id";
        Band band = con.createQuery(bandQuery)
          .addParameter("band_id", band_id)
          .executeAndFetchFirst(Band.class);
        bands.add(band);
      }
      return bands;
    }
  }
}
