package daointerfaces;

import java.util.List;

import models.Deployment;

public interface DeploymentDAO {

	public boolean deployProject(Deployment deployment);
	
	public Deployment findDeploymentById(Long deployid);
	
	public List<Deployment> findDeploymentsByProjectId(Long projectid);
	
	public boolean updateDeployment(Deployment deployment);
	
	public boolean linkIssue(Long deployId, Long issueId);
	
	public boolean deleteDeployment(Long deployid);
}
