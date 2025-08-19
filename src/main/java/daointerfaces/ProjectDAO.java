package daointerfaces;

import java.util.List;

import models.Project;

public interface ProjectDAO {

	public void createProject(Project project);

	public Project getProjectById(Long id);

	public List<Project> getAllProjects();

	public List<Project> getProjectsByUserId(Long userid);

	public void updateProject(Project project);

	public void deleteProject(Long id);
}
