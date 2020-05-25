package tn.esprit.jsf_app.services;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.persistence.EntityManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import tn.esprit.jsf_app.DTO.Event;
import tn.esprit.jsf_app.interfaces.ClaimServiceRemote;

@Stateful
@LocalBean
public class FeedBackService implements FeedBackServiceRemote {
	public String GlobalEndPoint = "localhost:4640";
	EntityManager em;

	@Override
	public List<FeedBack> GetAll() {
		// Client client = ClientBuilder.newClient();
		// WebTarget target = client.target("http://localhost:31618/api/PubWebApi/");
		// WebTarget hello =target.path("");
		// Response response =hello.request().get();

		// String result=response.readEntity(String.class);

		// PublicationDTO[] pubs = response.readEntity(PublicationDTO[].class);

		// response.close();
		// return pubs;
		List<FeedBack> lasp = new ArrayList<Event>();
		Client client = ClientBuilder.newClient();

		WebTarget web = client.target("http://" + GlobalEndPoint + "/api/FeedBack");

		Response response = web.request().get();

		String result = response.readEntity(String.class);

		// System.out.println(result);
		JsonReader jsonReader = Json.createReader(new StringReader(result));
		JsonArray object = jsonReader.readArray();

		for (int i = 0; i < object.size(); i++) {

			Event m = new Event();
			// String dateString;
			m.setComplaintId(object.getJsonObject(i).getInt("FeedBackId"));
			m.setDescription(object.getJsonObject(i).getString("Description"));
			m.setClaimDate(object.getJsonObject(i).getString("FeedBackDate"));
			m.setClaimType(object.getJsonObject(i).getString("sentiment"));

			lasp.add(m);
		}

		return lasp;
	}

	@Override
	public void Create(FeedBack p) {

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://" + GlobalEndPoint + "/api/FeedPost");
		WebTarget hello = target.path("");

		Response response = hello.request().post(Entity.entity(p, MediaType.APPLICATION_JSON));

		String result = response.readEntity(String.class);
		System.out.println(result);

		response.close();
	}

	@Override
	public void Update(int id, FeedBack p) {
		// TODO Auto-generated method stub
		FeedBack e = new FeedBack();
		e.setComplaintId(p.FeedBackId);
		e.setDescription(p.Description);
		e.setClaimDate(p.FeedBackDate);
		e.setClaimType(p.sentiment);

		System.out.println("iddddddddd" + id);
		System.out.println("OK");

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://" + GlobalEndPoint + "/api/FeedBackPut?id=" + id);
		Response response = target.request().put(Entity.entity(e, MediaType.APPLICATION_JSON));
		System.out.println(response);
	}

	@Override
	public void Delete(Claim eve) {
		Client cl = ClientBuilder.newClient();
		WebTarget target = cl.target("http://" + GlobalEndPoint + "/api/FeedBackApi?id=" + eve.getEventId());
		WebTarget hello = target.path("");
		Response res = (Response) hello.request().delete();
	}
}
