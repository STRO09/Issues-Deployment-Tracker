package daointerfaces;

import java.util.List;
import java.util.Optional;

import models.User;
import models.User.Role;

public interface UserDAO {

	public boolean registerUser(User user);

//	public boolean loginUser(String email, String password, Role role);
	// doubt
	Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String username);

	public List<User> findAll();

	public List<User> findUsersByRole(Role role);

	public boolean updateProfile(User user);

	public boolean deleteUser(Long id);

	public User getUserById(Long id);
}
