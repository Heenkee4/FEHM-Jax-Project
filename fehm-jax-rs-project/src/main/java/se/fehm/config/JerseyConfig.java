package se.fehm.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import se.fehm.resource.IssueResource;
import se.fehm.resource.TeamResource;
import se.fehm.resource.UserResource;
import se.fehm.resource.WorkItemResource;

@Component
public final class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(WorkItemResource.class);
		register(UserResource.class);
		register(TeamResource.class);
		register(IssueResource.class);
	}
}
