package daointerfaces;

import java.util.List;

import models.Project;
import models.ProjectMember;
import models.ProjectRole;
import models.User;

public interface ProjectMemberDAO {

	
	public void addMember(Project project, User user, ProjectRole projectRole);
	
	public void removeMember(Project project, User user);	
	
	public List<ProjectMember> findMembersByProject(Long projectId);
	
	public void changeProjectRole(Project project, User user, ProjectRole projectRole);
}
