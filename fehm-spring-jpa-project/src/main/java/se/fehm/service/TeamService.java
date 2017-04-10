package se.fehm.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import se.fehm.model.Team;
import se.fehm.repository.TeamRepository;
import se.fehm.repository.UserRepository;

@Component
public final class TeamService {

	private final TeamRepository teamRepository;
	private final ServiceTransaction executor;
	private final UserRepository userRepository;

	@Autowired
	public TeamService(TeamRepository teamRepository, ServiceTransaction executor, UserRepository userRepository) {
		this.teamRepository = teamRepository;
		this.executor = executor;
		this.userRepository = userRepository;
	}

	public Team addTeam(Team team) throws ServiceException {
		try {
			return executor.execute(() -> {
			return teamRepository.save(team);
			});
		} catch (DataAccessException e) {
			throw new ServiceException("Could not add Team", e);
		}
	}

	public void updateTeam(Team team) throws ServiceException {
		try {
			teamRepository.update(team.getName(), team.getNumber());

		} catch (DataAccessException e) {
			throw new ServiceException("Could not update Team", e);
		}
	}

	public void inactivateTeam(Team team) throws ServiceException {
		try {
			teamRepository.inactivate(team.isActive(), team.getNumber());

		} catch (DataAccessException e) {
			throw new ServiceException("Could not inactivate Team", e);
		}
	}

	public Collection<Team> getAllTeams() throws ServiceException {
		try {
			return teamRepository.findAll();

		} catch (DataAccessException e) {
			throw new ServiceException("Could not get All Teams", e);
		}
	}

	public void addUserToTeam(Long teamId, String number) throws ServiceException {
		try {
			maxAllowedUsers(teamId);
			teamRepository.addUserToTeam(teamId, number);

		} catch (DataAccessException e) {
			throw new ServiceException("Could not add user to Team", e);
		}
	}

	private void maxAllowedUsers(Long teamId) throws ServiceException {
		if (userRepository.getUsersFromTeam(teamId).size() < 10) {
		} else {
			throw new ServiceException("Only a maximum amount of 10 users is allowed");
		}
	}
	
}
