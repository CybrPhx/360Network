package DiagramParser;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;



public class Activity implements Comparable<Activity> {
    private String name;
    public int id, duration, rank;
    private Activity parent;
    private List<Activity> children;
    List <Predecessor> predecessors;

    public Activity(String name) {
        this.setName(name);
        this.children = new LinkedList<>();
        this.predecessors = new LinkedList<>();
        this.duration = 0;
    }
    
    // Predecessor List
    public Iterator<Predecessor> iterator() {
		return predecessors.iterator();
	}
    
    public boolean exists(String p2) {
		boolean exists = false;
		for(Predecessor p : predecessors) {
			if (p.getName().toLowerCase().equals(p2.toLowerCase()))
				exists = true;
		}
		return exists;
	}
    
    public void addPredecessor(String p) {
		if(!exists(p)) {
			Predecessor newP = new Predecessor(p);
			this.predecessors.add(newP);
			this.rank = this.rank + 1;
		}
	}

    // Child Node
    public void addChild(Activity child) {
    	boolean exists = false;
    	for (Activity c: this.children) {
    		if (c.getName().equals(child.getName())) {
    			exists = true;
    		}
    	}
    	if (!exists) {
    		this.children.add(child);
    		child.setParent(this);
    	}
    }

    public List<Activity> getChildren() {
        return Collections.unmodifiableList(this.children);
    }

    private void setParent(Activity parent) {
        this.parent = parent;
    }

    public Activity getParent() {
        return this.parent;
    }
    
    public void setId(int id) {
    	this.id = id;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public String printPredecessors() {		
		String names = new String();
		for (int i = 0; i < predecessors.size(); i++) {
			names += predecessors.get(i).getName() + " ";
		}
		
		return names;
	}
	
	public String toString() {
		String result = "Node: "  + name  + "\nPredecessors: " +
				printPredecessors() + "\nDuration: " + duration + "\n************\n";				
		return result;
	}
	
	public int compareTo(Activity i) {
		return this.predecessors.size() - i.predecessors.size();
	}

}
