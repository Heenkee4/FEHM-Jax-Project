package se.fehm.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import se.fehm.model.Team;

public interface TeamRepository extends CrudRepository<Team, Long>{
	
	static final String ADD_USER_TO_TEAM = "update User u set team_id = ?1 where u.number = ?2";
	static final String UPDATE_BY_NUMBER = "update Team t set t.name = ?1 where t.number = ?2";
	static final String INACTIVATE = "update Team t set t.active = ?1 where t.number = ?2";

	Collection<Team> findAll();
	
	@Transactional
	@Modifying
    @Query(ADD_USER_TO_TEAM)
	void addUserToTeam (Long teamId, String number);
	
	@Transactional
	@Modifying
	@Query(UPDATE_BY_NUMBER)
	void update(String name, String number);
	
	@Transactional
	@Modifying
	@Query(INACTIVATE)
	void inactivate(boolean active, String number);
	
	}

