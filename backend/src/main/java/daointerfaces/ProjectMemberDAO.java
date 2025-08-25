package daointerfaces;

import java.util.List;

import models.Project;
import models.ProjectMember;
import models.ProjectRole;
import models.User;

public interface ProjectMemberDAO {

	
	  public boolean addMember(ProjectMember projectMember);

	    public boolean removeMember(Long projectmemberid);

	    public List<ProjectMember> findMembersByProject(Long projectId);

	    public boolean updateProjectRole(ProjectMember projectMember);
}
