package daointerfaces;

import java.util.List;

import models.Deployment;

public interface DeploymentDAO {

	public void deployProject(Deployment deployment);
	
	public Deployment findDeploymentById(Long deployid);
	
	public List<Deployment> findDeploymentsByProjectId(Long projectid);
	
	public void updateDeployment(Deployment deployment);
	
	public void linkIssue(Long deployId, Long issueId);
	
	public void deleteDeployment(Long deployid);
}
