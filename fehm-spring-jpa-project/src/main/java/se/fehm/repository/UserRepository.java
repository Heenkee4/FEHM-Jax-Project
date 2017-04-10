package se.fehm.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import se.fehm.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	static final String UPDATE_USER_BY_NUMBER = "update User u set u.firstName =?2, u.lastName = ?3 where u.number = ?4";
	static final String GET_USER_BY_NUMBER = "select u from User u where u.number = ?1";
	static final String GET_USERS_BY_NAME = "select u from #{#entityName} u where u.userName like :uName"
											+ " or u.firstName like :fName or u.lastName like :lName";
	static final String INACTIVATE_USER_BY_NUMBER = "update User u set u.active =?1 where u.number =?2";
	static final String GET_USERS_FROM_TEAM = "select u from User u where team_id = ?1";
	static final String CHANGE_WORKSTATUS = "update users join users_workItems on users_workItems.user_Id = Users.id"
											+ " join WorkItems on users_workItems.workItem_Id = workItems.id"
											+ " set WorkItems.status =?1 where Users.active = ?2";
	
	@Query(GET_USERS_BY_NAME)
	Collection<User> getUsersByName(@Param("uName") String username, @Param("fName") String firstName,
							        @Param("lName") String lastName);

	@Transactional
	@Modifying
	@Query(UPDATE_USER_BY_NUMBER)
	void updateUserByNo(String firstName, String lastName, String number);

	@Query(GET_USER_BY_NUMBER)
	User getUserByNo(String number);

	@Transactional
	@Modifying
	@Query(INACTIVATE_USER_BY_NUMBER)
	void inactivateUserByNo(boolean active, String number);

	@Query(GET_USERS_FROM_TEAM)
	Collection<User> getUsersFromTeam(Long teamId);

	@Transactional
	@Modifying
	@Query(value = CHANGE_WORKSTATUS, nativeQuery = true)
	void changeWorkstatus(String status, boolean active);

}
