package daointerfaces;

import java.util.List;

import models.Project;
import models.ProjectMember;
import models.ProjectRole;
import models.User;

public interface ProjectMemberDAO {

	
	  public void addMember(ProjectMember projectMember);

	    public void removeMember(Long projectmemberid);

	    public List<ProjectMember> findMembersByProject(Long projectId);

	    public void updateProjectRole(ProjectMember projectMember);
}
