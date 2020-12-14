package it.omicron.esercizio;

import java.util.List;

public class MenuContent {
    private String version;
    private List<MenuNode> nodes;

    public MenuContent() {
        super();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

	public List<MenuNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<MenuNode> nodes) {
		this.nodes = nodes;
	}
  
}
