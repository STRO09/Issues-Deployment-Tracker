package daointerfaces;

import java.util.List;

import models.Deployment;
import models.Issue;

public interface DeploymentDAO {

	public void deployProject(Deployment deployment);
	
	public Deployment findDeploymentById(Long deployid);
	
	public List<Deployment> findDeploymentsByProjectId(Long projectid);
	
	public void updateDeployment(Deployment deployment);
	
	public void linkIssue(Long deployId, Long issueId);
	
	public List<Issue> viewIssuesPerVersion(Long deployId);
	
	public void deleteDeployment(Long deployid);
}
