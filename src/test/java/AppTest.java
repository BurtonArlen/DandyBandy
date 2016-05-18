import org.sql2o.*;
import org.junit.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.junit.Assert.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();


  @Test
  public void venueIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Venues"));
    click("a", withText("Add new Venue"));
    fill("#venue_name").with("ThunderDome");
    submit(".btn");
    assertThat(pageSource()).contains("ThunderDome");
  }

  @Test
  public void bandIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Bands"));
    click("a", withText("Add new Band"));
    fill("#band_name").with("Italian Crust Funk");
    submit(".btn");
    assertThat(pageSource()).contains("Italian Crust Funk");
  }

  @Test
  public void venueShowPageDisplaysName() {
    Venue testVenue = new Venue("ThunderDome");
    testVenue.save();
    String url = String.format("http://localhost:4567/venues/%d", testVenue.getId());
    goTo(url);
    assertThat(pageSource()).contains("ThunderDome");
  }

  @Test
  public void bandShowPageDisplaysDescription() {
    Band testBand = new Band("Italian Crust Funk");
    testBand.save();
    String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(url);
    assertThat(pageSource()).contains("Italian Crust Funk");
  }

  @Test
  public void bandIsAddedToVenue() {
    Venue testVenue = new Venue("ThunderDome");
    testVenue.save();
    Band testBand = new Band("Italian Crust Funk");
    testBand.save();
    String url = String.format("http://localhost:4567/venues/%d", testVenue.getId());
    goTo(url);
    fillSelect("#band_id").withText("Italian Crust Funk");
    submit(".btn");
    assertThat(pageSource()).contains("<li>");
    assertThat(pageSource()).contains("Italian Crust Funk");
  }

  @Test
  public void venueIsAddedToBand() {
    Venue testVenue = new Venue("ThunderDome");
    testVenue.save();
    Band testBand = new Band("Italian Crust Funk");
    testBand.save();
    String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(url);
    fillSelect("#venue_id").withText("ThunderDome");
    submit(".btn");
    assertThat(pageSource()).contains("<li>");
    assertThat(pageSource()).contains("ThunderDome");
  }
}
