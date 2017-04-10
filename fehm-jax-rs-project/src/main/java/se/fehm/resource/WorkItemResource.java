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
import se.fehm.model.WorkItem;
import se.fehm.service.IssueService;
import se.fehm.service.ServiceException;
import se.fehm.service.WorkItemService;

@Component
@Path("/workItems/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public final class WorkItemResource {

	private final WorkItemService service;
	private final IssueService issueService;

	public WorkItemResource(WorkItemService service, IssueService issueService) {
		this.service = service;
		this.issueService = issueService;
	}

	@Context
	private UriInfo uriInfo;

	@POST
	public Response create(WorkItem workItem) throws ServiceException {
		workItem = new WorkItem(workItem.getNumber(), workItem.getName(), workItem.getStatus(), workItem.isActive());
		service.addWorkItem(workItem);
		URI location = uriInfo.getAbsolutePathBuilder().path(workItem.getNumber()).build();
		return Response.created(location).build();
	}

	@GET
	public Response getByName(@DefaultValue("%") @QueryParam("name") String name) throws ServiceException {
		Collection<WorkItem> workItems = service.findByNameLike(name);
		return workItems == null ? Response.status(Status.NOT_FOUND).build() : Response.ok(workItems).build();
	}

	@GET
	@Path("status/{status}")
	public Response getByStatus(@PathParam("status") String status) throws ServiceException {
		Collection<WorkItem> workItems = service.findByStatus(status);
		return workItems == null ? Response.status(Status.NOT_FOUND).build() : Response.ok(workItems).build();
	}

	@GET
	@Path("fromUser/{number}")
	public Response getFromUser(@PathParam("number") String number) throws ServiceException {
		Collection<WorkItem> workItems = service.getWorkItemsForUser(number);
		return workItems == null ? Response.status(Status.NOT_FOUND).build() : Response.ok(workItems).build();
	}

	@GET
	@Path("fromTeam/{number}")
	public Response getFromTeam(@PathParam("number") String number) throws ServiceException {
		Collection<WorkItem> workItems = service.getWorkItemsByTeam(number);
		return workItems == null ? Response.status(Status.NOT_FOUND).build() : Response.ok(workItems).build();
	}

	@GET
	@Path("issues")
	public Response getWorkItemsWithIssues() throws ServiceException {
		Collection<WorkItem> workItems = issueService.getAllWorkItemsWithIssue();
		return workItems == null ? Response.status(Status.NOT_FOUND).build() : Response.ok(workItems).build();
	}

	@PUT
	@Path("changeStatus/{number}")
	public Response changeStatus(@PathParam("number") String number, String status) throws ServiceException {
		service.changeStatusForWorkItem(status, number);
		return number == null ? Response.status(Status.NOT_FOUND).build(): Response.noContent().build();
	}

	@PUT
	@Path("changeActivity/{number}")
	public Response changeActivity(@PathParam("number") String number, boolean active) throws ServiceException {
		service.inActivateWorkItem(active, number);
		return number == null ? Response.status(Status.NOT_FOUND).build() : Response.noContent().build();
	}
	
	@PUT
	@Path("user/{workItemId}")
	public Response addWorkItemToUser(@PathParam("workItemId") Long workItemId, User user) throws ServiceException {
		service.addWorkItemToUser(user.getId(), workItemId, user);
		return user == null ? Response.status(Status.NOT_FOUND).build() : Response.noContent().build();
	}

}
