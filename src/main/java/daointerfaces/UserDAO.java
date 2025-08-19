package daointerfaces;

import java.util.List;

import models.User;
import models.User.Role;

public interface UserDAO {

	public void registerUser(User user);

	public boolean loginUser(String email, String password, Role role);
	//doubt 
	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String username);


	public List<User> findAll();
	
	public List<User> findUsersByRole(Role role);

	public void updateProfile(User user);

	public void deleteUser(Long id);
}
