package se.fehm.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import se.fehm.model.Issue;
import se.fehm.model.WorkItem;
import se.fehm.repository.IssueRepository;
import se.fehm.repository.WorkItemRepository;

@Component
public final class IssueService {

	private final IssueRepository issueRepository;
	private final WorkItemRepository workItemRepository;
	private final ServiceTransaction executor;

	@Autowired
	public IssueService(IssueRepository issueRepository, ServiceTransaction executor, WorkItemRepository workItemRepository) {
		this.issueRepository = issueRepository;
		this.executor = executor;
		this.workItemRepository = workItemRepository;
	}

	public Issue addIssue(Issue issue) throws ServiceException {
		try {
			return executor.execute(() -> {
			return issueRepository.save(issue);
			});
		} catch (DataAccessException e) {
			throw new ServiceException("Could not add Issue", e);
		}
	}

	public void updateIssue(String name, String number) throws ServiceException {
		try {
			issueRepository.update(name, number);

		} catch (DataAccessException e) {
			throw new ServiceException("Could not update Issue", e);
		}
	}

	public Collection<Issue> findAll() throws ServiceException {
		try {
			return issueRepository.findAll();

		} catch (DataAccessException e) {
			throw new ServiceException("Could not find all issues", e);
		}
	}

	public Collection<WorkItem> getAllWorkItemsWithIssue() throws ServiceException {
		try {
			return issueRepository.getWorkItemsWithIssue();

		} catch (DataAccessException e) {
			throw new ServiceException("Could not find workItems with issues", e);
		}
	}
		
	public void addIssueToWorkItem(Long issueId, WorkItem workItem) throws ServiceException {
		try {
			workItemIsDone(workItem);
			issueRepository.addIssueToWorkItem(issueId, workItem.getNumber());
			changeStatusForWorkItem(workItem);
			
			} catch (DataAccessException e) {
			throw new ServiceException("Could not add issue to workitem", e);
		}
	}
	
	public void changeStatusForWorkItem(WorkItem workItem) throws ServiceException {
		try{
			workItemRepository.changeStatusForWorkItem("UNSTARTED", workItem.getNumber());
			} catch (DataAccessException e) {
			throw new ServiceException("Could not update WorkItem to UNSTARTED", e);
		}
	}

	private void workItemIsDone(WorkItem workItem) throws ServiceException {
		if (workItem.getStatus().equals("DONE")) {			
		} else {
			throw new ServiceException("WorkItem is not DONE");
		}
	}

}

