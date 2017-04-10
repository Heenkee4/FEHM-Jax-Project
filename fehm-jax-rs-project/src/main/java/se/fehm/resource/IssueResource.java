package se.fehm.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Component;

import se.fehm.model.Issue;
import se.fehm.model.WorkItem;
import se.fehm.service.IssueService;
import se.fehm.service.ServiceException;

@Component
@Path("/issues/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IssueResource {

	private final IssueService service;

	public IssueResource(IssueService service) {
		this.service = service;
	}

	@Context
	private UriInfo uriInfo;

	@POST
	public Response create(Issue issue) throws ServiceException {
		issue = new Issue(issue.getNumber(), issue.getName());
		service.addIssue(issue);
		URI location = uriInfo.getAbsolutePathBuilder().path(issue.getNumber()).build();
		return Response.created(location).build();
	}
	
	@PUT
	@Path("update/{number}")
	public Response updateIssue(@PathParam("number") String number, String name) throws ServiceException{
		service.updateIssue(name, number);
		return number == null ? Response.status(Status.NOT_FOUND).build() : Response.noContent().build();
	}
	
	@PUT
	@Path("workItem/{issueId}")
	public Response addIssueToWorkItem(@PathParam("issueId") Long issueId, WorkItem workItem) throws ServiceException {
		service.addIssueToWorkItem(issueId, workItem);
		return workItem == null ? Response.status(Status.NOT_FOUND).build() : Response.noContent().build();
	}
		
}
