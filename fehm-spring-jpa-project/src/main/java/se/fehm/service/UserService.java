package se.fehm.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import se.fehm.model.User;
import se.fehm.repository.UserRepository;

@Component
public final class UserService<User_WorkItem> {

	private final UserRepository userRepository;
	private final ServiceTransaction executor;

	@Autowired
	public UserService(UserRepository userRepository, ServiceTransaction executor) {
		this.userRepository = userRepository;
		this.executor = executor;
	}

	public User addUser(User user) throws ServiceException {
		try {
			validUsername(user);
			return executor.execute(() -> {
				return userRepository.save(user);
			});
		} catch (DataAccessException e) {
			throw new ServiceException("Could not add User", e);
		}
	}
	
	public void updateUserByNumber(User user) throws ServiceException {
		try {
			userRepository.updateUserByNo(user.getFirstName(), user.getLastName(), user.getNumber());
		} catch (DataAccessException e) {
			throw new ServiceException("Could not uppdate user", e);
		}
	}

	public User getUserByNo(String number) throws ServiceException {
		try {
			return userRepository.getUserByNo(number);
		} catch (DataAccessException e) {
			throw new ServiceException("Could not find user with that number", e);
		}
	}

	public Collection<User> getUsersByName(String userName, String firstName, String lastName) throws ServiceException {
		try {
			return userRepository.getUsersByName(userName, firstName, lastName);
		} catch (DataAccessException e) {
			throw new ServiceException("Could not find any users with that name", e);
		}
	}

	public Collection<User> getUserByUserName(String userName) {
		return userRepository.getUsersByName(userName, "%", "%");
	}

	public Collection<User> getUserByFirstName(String firstName) {
		return userRepository.getUsersByName("%", firstName, "%");
	}

	public Collection<User> getUserByLastName(String lastName) {
		return userRepository.getUsersByName("%", "%", lastName);
	}

	public Collection<User> getUsersFromTeam(Long teamId) throws ServiceException {
		try {
			return userRepository.getUsersFromTeam(teamId);
		} catch (DataAccessException e) {
			throw new ServiceException("Could not get users from that team", e);
		}
	}
	
    public void inactivateUser(boolean active, String number) throws ServiceException {
        try {
            userRepository.inactivateUserByNo(active, number);
            userRepository.changeWorkstatus("UNSTARTED", false);
        } catch (DataAccessException e) {
            throw new ServiceException("Could not inactivate user", e);
        }
    }
	
	private void validUsername(User user) throws ServiceException {
		if (user.getUserName().length() >= 10) {
		} else {
			throw new ServiceException("Username must be at least 10 characters long.");
		}
	}
	
}
