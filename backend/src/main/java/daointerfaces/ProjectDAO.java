package daointerfaces;

import java.util.List;

import models.Project;

public interface ProjectDAO {

	public boolean createProject(Project project);

	public Project getProjectById(Long id);

	public List<Project> getAllProjects();

	public List<Project> getProjectsByUserId(Long userid);

	public boolean updateProject(Project project);

	public  boolean deleteProject(Long id);
}
