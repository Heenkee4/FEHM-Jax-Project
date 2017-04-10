package se.fehm.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import se.fehm.model.Issue;
import se.fehm.model.WorkItem;

public interface IssueRepository extends CrudRepository<Issue, Long> {

	static final String UPDATE_BY_NUMBER = "update Issue i set i.name = ?1 where i.number = ?2";
	static final String ADD_ISSUE_TO_WORKITEM = "update WorkItem w set issue_Id = ?1 where w.number = ?2";
	static final String GET_WORKITEMS_WITH_ISSUE = "select w from WorkItem w where issue_id != NULL";

	Collection<Issue> findAll();

	@Transactional
	@Modifying
	@Query(UPDATE_BY_NUMBER)
	void update(String name, String number);

	@Transactional
	@Modifying
	@Query(ADD_ISSUE_TO_WORKITEM)
	void addIssueToWorkItem(Long issueId, String number);

	@Query(GET_WORKITEMS_WITH_ISSUE)
	Collection<WorkItem> getWorkItemsWithIssue();

}
