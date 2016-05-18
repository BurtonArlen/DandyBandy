import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.List;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("venues", Venue.all());
      model.put("template", "templates/venues.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("template", "templates/bands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/new-venue/form", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/Venue-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/new-band/form", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/Band-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/venues", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("venue_name");
      Venue newVenue = new Venue(name);
      newVenue.save();
      newVenue.deleteLogic();
      response.redirect("/venues");
      return null;
    });

    post("/venues/addband", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int venue_id = Integer.parseInt(request.queryParams("venue_id"));
      int band_id = Integer.parseInt(request.queryParams("band_id"));
      Venue venue = Venue.find(venue_id);
      Band band = Band.find(band_id);
      venue.addBand(band);
      response.redirect("/venues/" + venue_id);
      return null;
    });

    post("/bands", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("band_name");
      Band newBand = new Band(name);
      newBand.save();
      newBand.deleteLogic();
      response.redirect("/bands");
      return null;
    });

    post("/bands/addvenue", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      int venue_id = Integer.parseInt(request.queryParams("venue_id"));
      int band_id = Integer.parseInt(request.queryParams("band_id"));
      Venue venue = Venue.find(venue_id);
      Band band = Band.find(band_id);
      band.addVenue(venue);
      response.redirect("/bands/" + band_id);
      return null;
    });

    get("/bands/:id", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params("id")));
      model.put("band", band);
      model.put("venues", Venue.all());
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/venues/:id", (request,response) ->{
      HashMap<String, Object> model = new HashMap<String, Object>();
      Venue venue = Venue.find(Integer.parseInt(request.params("id")));
      model.put("venue", venue);
      model.put("bands", Band.all());
      model.put("template", "templates/venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/band/:id/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params(":id")));
      model.put("band", band);
      model.put("template", "templates/band-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/band-update", (request, response) -> {
      int bandId = Integer.parseInt(request.queryParams("band_id"));
      String name = request.queryParams("update-name");
      Band updateBand = Band.find(bandId);
      updateBand.update(name);
      response.redirect("/bands/" + bandId);
      return null;
    });

    get("/band/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params("id")));
      model.put("band", band);
      model.put("template", "templates/band-delete.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/band/delete", (request, response) -> {
      int bandId = Integer.parseInt(request.queryParams("band_id"));
      Band deleteBand = Band.find(bandId);
      deleteBand.delete();
      response.redirect("/bands");
      return null;
    });

  }
}
