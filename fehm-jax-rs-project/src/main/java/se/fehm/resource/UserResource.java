package se.fehm.resource;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.springframework.stereotype.Component;

import se.fehm.model.User;
import se.fehm.service.ServiceException;
import se.fehm.service.UserService;

@Component
@Path("/users/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class UserResource {

	private final UserService service;

	public UserResource(UserService service) {
		this.service = service;
	}

	@Context
	private UriInfo uriInfo;

	@POST
	public Response addUser(User user) throws ServiceException {
		user = new User(user.getNumber(), user.getUserName(), user.getFirstName(), user.getLastName(), user.isActive());
		service.addUser(user);
		URI location = uriInfo.getAbsolutePathBuilder().path(user.getNumber()).build();
		return Response.created(location).build();
	}

	@GET
	@Path("number/{number}")
	public Response getUserByNumber(@PathParam("number") String number) throws ServiceException {
		User user = service.getUserByNo(number);
		return user == null ? Response.status(Status.NOT_FOUND).build() : Response.ok(user).build();
	}

	@GET
	public Response getUsersByNames(@DefaultValue("%") @QueryParam("username") String username,
			@DefaultValue("%") @QueryParam("firstName") String firstName,
			@DefaultValue("%") @QueryParam("lastName") String lastName) throws ServiceException {
		Collection<User> users = service.getUsersByName(username, firstName, lastName);
		return users == null ? Response.status(Status.NOT_FOUND).build() : Response.ok(users).build();
	}
	
	@GET
	@Path("team/{teamId}")
	public Response getUsersFromTeam(@PathParam("teamId") Long teamId) throws ServiceException {
		Collection<User> users = service.getUsersFromTeam(teamId);
		return users == null? Response.status(Status.NOT_FOUND).build() : Response.ok(users).build();
	}
	
	@PUT
	@Path("changeActivity/{number}")
	public Response inactivateUser(@PathParam("number") String number, boolean active) throws ServiceException {
		service.inactivateUser(active, number);
		return number == null ? Response.status(Status.NOT_FOUND).build() : Response.noContent().build();
	}
	
	@PUT
	@Path("update/{number}")
	public Response updateUser(@PathParam("number") String number, User user) throws ServiceException {
		service.updateUserByNumber(user);
		return user == null ? Response.status(Status.NOT_FOUND).build() : Response.noContent().build();
	}

}
