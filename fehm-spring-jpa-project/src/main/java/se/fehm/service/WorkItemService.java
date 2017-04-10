package se.fehm.service;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import se.fehm.model.User;
import se.fehm.model.WorkItem;
import se.fehm.repository.WorkItemRepository;

@Component
public final class WorkItemService<User_WorkItem> {

	private final WorkItemRepository workItemRepository;
	private final ServiceTransaction executor;

	@Autowired
	public WorkItemService(WorkItemRepository workItemRepository, ServiceTransaction executor) {
		this.workItemRepository = workItemRepository;
		this.executor = executor;
	}

	public WorkItem addWorkItem(WorkItem workItem) throws ServiceException {
		try {
			return executor.execute(() -> {
				return workItemRepository.save(workItem);
			});
		} catch (DataAccessException e) {
			throw new ServiceException("could not add WorkItem", e);
		}
	}

	public void changeStatusForWorkItem(String status, String number) throws ServiceException {
		try {
			workItemRepository.changeStatusForWorkItem(status, number);

		} catch (DataAccessException e) {
			throw new ServiceException("could not change status for workItem", e);
		}
	}

	public void inActivateWorkItem(boolean active, String number) throws ServiceException {
		try {
			workItemRepository.inActivateWorkItem(active, number);

		} catch (DataAccessException e) {
			throw new ServiceException("could not inactivate workItem", e);
		}
	}

	public void addWorkItemToUser(Long userId, Long workItemId, User user) throws ServiceException {
		try {
			userIsActive(user);
			validAmountOfWorkItems(user);
			workItemRepository.addWorkItemToUser(user.getId(), workItemId);

		} catch (DataAccessException e) {
			throw new ServiceException("could not add workItem to user", e);
		}
	}

	public Collection<WorkItem> findByStatus(String status) throws ServiceException {
		try {
			return workItemRepository.findByStatus(status);

		} catch (DataAccessException e) {
			throw new ServiceException("could not find workItem with that status", e);
		}
	}

	public Collection<WorkItem> findByNameLike(String name) throws ServiceException {
		try {
			return workItemRepository.findByNameLike(name);

		} catch (DataAccessException e) {
			throw new ServiceException("could not find workItem with similar name", e);
		}
	}

	public Collection<WorkItem> getWorkItemsForUser(String number) throws ServiceException {
		try {
			return workItemRepository.getWorkItemsFromUser(number);

		} catch (DataAccessException e) {
			throw new ServiceException("could not find workItems for that user", e);
		}
	}

	public Collection<WorkItem> getWorkItemsByTeam(String number) throws ServiceException {
		try {
			return workItemRepository.getWorkItemsFromTeam(number);

		} catch (DataAccessException e) {
			throw new ServiceException("could not workItem for that team", e);
		}
	}

	private void userIsActive(User user) throws ServiceException {
		if (user.isActive()) {
		} else {
			throw new ServiceException("Can not add workItem, user is not active");
		}
	}

	private void validAmountOfWorkItems(User user) throws ServiceException {
		if (workItemRepository.getWorkItemsFromUser(user.getNumber()).size() < 5) {
		} else {
			throw new ServiceException("User can not have more than 5 workitems at the same time");
		}
	}

}