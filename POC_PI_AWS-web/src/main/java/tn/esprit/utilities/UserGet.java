package tn.esprit.utilities;

import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tn.esprit.jsf_app.services.UserService;

@Path("user")

public class UserGet {
	private static String resetcode;
	UserService us = new UserService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("VerifyAccount/{id}")
	public Response VerifyAccountJee(@PathParam(value = "id") String id) throws URISyntaxException {
		if (us.VerifyAccount(id)) {
			java.net.URI location = new java.net.URI("../Login/VerifyAccount.jsf");
			return Response.temporaryRedirect(location).build();

		}
		return null;
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("ResetPassword/{id}")
	public Response VerifyJee(@PathParam(value = "id") String id) throws URISyntaxException {
		if (us.Verify(id)) {
			resetcode=id;
			java.net.URI location = new java.net.URI("../Login/ResetPassword.jsf");
			return Response.temporaryRedirect(location).build();

		}
		return null;
	}
	public static String getResetcode() {
		return resetcode;
	}
	public static void setResetcode(String resetcode) {
		UserGet.resetcode = resetcode;
	}

	
}
