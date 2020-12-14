package it.omicron.esercizio;

import java.util.List;

public class MenuNode {
	
	private int depth;
	private int nodeId;
	private String nodeName;
	private String nodeType;
	private String groupType;
	private String flowType;
	private MenuResource resource;
	
	private List<MenuNode> nodes;
	
	
	public MenuNode()
	{
		super();
	}
	
	public int getNodeId()
	{
		return nodeId;
	}
	
	public String getNodeName()
	{
		return nodeName;
	}
	
	public String getNodeType()
	{
		return nodeType;
	}
	
	public String getGroupType()
	{
		return groupType;
	}
	
	public String getFlowType()
	{
		return flowType;
	}
	
	public int getResourceId()
	{
		if (resource!=null)
			return resource.getId();
		else
			return -1;
	}
	
	public List<MenuNode> getNodes()
	{
		return nodes;
	}
	
	public int getDepth()
	{
		return depth;
	}
	
	public void setDepth(int i)
	{
		depth=i;
	}
	
	
	
}
