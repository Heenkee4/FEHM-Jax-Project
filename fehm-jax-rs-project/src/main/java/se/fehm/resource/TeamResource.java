package se.fehm.resource;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.springframework.stereotype.Component;

import se.fehm.model.Team;
import se.fehm.service.ServiceException;
import se.fehm.service.TeamService;

@Component
@Path("/teams/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TeamResource {

	private final TeamService service;

	public TeamResource(TeamService service) {
		this.service = service;
	}

	@Context
	private UriInfo uriInfo;

	@POST
	public Response addTeam(Team team) throws ServiceException {
		team = new Team(team.getNumber(), team.getName(), team.isActive());
		service.addTeam(team);
		URI location = uriInfo.getAbsolutePathBuilder().path(team.getNumber()).build();
		return Response.created(location).build();
	}

	@GET
	public Response getAllTeams() throws ServiceException {
		Collection<Team> teams = service.getAllTeams();
		return teams == null ? Response.status(Status.NOT_FOUND).build() : Response.ok(teams).build();
	}

	@PUT
	@Path("update/{number}")
	public Response updateTeam(@PathParam("number") String number, Team team) throws ServiceException {
		service.updateTeam(team);
		return team == null ? Response.status(Status.NOT_FOUND).build() : Response.noContent().build();
	}

	@PUT
	@Path("changeActivity/{number}")
	public Response changeActivity(@PathParam("number") String number, Team team) throws ServiceException {
		service.inactivateTeam(team);
		return team == null ? Response.status(Status.NOT_FOUND).build() : Response.noContent().build();
	}

	@PUT
	@Path("user/{number}")
	public Response addUserToTeam(@PathParam("number") String number, Long teamId) throws ServiceException {
		service.addUserToTeam(teamId, number);
		return number == null ? Response.status(Status.NOT_FOUND).build() : Response.noContent().build();
	}

}
