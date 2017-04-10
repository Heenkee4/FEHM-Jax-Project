package se.fehm.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import se.fehm.model.WorkItem;

public interface WorkItemRepository extends CrudRepository<WorkItem, Long> {

	static final String INACTIVATE_BY_NUMBER = "update WorkItem w set w.active = ?1 where w.number = ?2";
	static final String CHANGE_STATUS_BY_NUMBER = "update WorkItem w set w.status = ?1 where w.number = ?2";
	static final String ADD_WORK_ITEM_TO_USER = "insert into users_workItems (User_Id, workItem_Id) VALUES (?1, ?2)";
	static final String GET_WORKITEMS_FROM_USER = "select u from User u join fetch u.workItem where u.number = ?1";
	static final String GET_WORKITEMS_FROM_TEAM = "select u from User u join fetch u.workItem where u.team.number = ?1";

	Collection<WorkItem> findByStatus(String status);
	Collection<WorkItem> findByNameLike(String name);

	@Transactional
	@Modifying
	@Query(INACTIVATE_BY_NUMBER)
	void inActivateWorkItem(boolean active, String number);

	@Transactional
	@Modifying
	@Query(CHANGE_STATUS_BY_NUMBER)
	void changeStatusForWorkItem(String status, String number);

	@Transactional
	@Modifying
	@Query(value = ADD_WORK_ITEM_TO_USER, nativeQuery = true)
	void addWorkItemToUser(Long userId, Long workItemsId);

	@Query(GET_WORKITEMS_FROM_USER)
	Collection<WorkItem> getWorkItemsFromUser(String number);

	@Query(GET_WORKITEMS_FROM_TEAM)
	Collection<WorkItem> getWorkItemsFromTeam(String number);

}
