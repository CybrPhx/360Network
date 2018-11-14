package DiagramParser;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

 
public class Path implements Comparable<Path> {
	private List<Activity> path;
	private String name;
	
	public Path() {
    	this.path = new LinkedList<>();
    }
    
    public int getDuration() {
    	int duration = 0;
    	for (Activity p : path) {
    		duration = duration + p.getDuration();
    	}
    	return duration;
    }

	public int compareTo(Path i) {
		return i.getDuration() - this.getDuration();
	}

	public void addActivity(Activity activity) throws Exception {
		boolean exists = false;
		int count = 0;
		for (Activity p: path) {
			if (p.getName().equals(activity.getName())) {
				count+=1;
				if (count==10) {
					exists = true;
					throw new CycleException("error, cycle exists" /*+ this.toCycle() */);
				}
			}
		}
		if (!exists) {
			this.path.add(activity);
		} 
	}
	
	public void addActivity(int index, Activity activity) throws CycleException {
		boolean exists = false;
		int count = 0;
		for (Activity p: path) {
			if (p.getName().equals(activity.getName())) {
				count+=1;
				if (count==10) {
					exists = true;
					throw new CycleException("error, cycle exists" /*+ this.toCycle()*/);
				}
			}
		}
		if (!exists) {
			this.path.add(index, activity);
		} 
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public List<Activity> getPath() {
		return this.path;
	}
	
	public String toString() {
		List<Activity> temp = new LinkedList<>();
		boolean inList = false;
		for (Activity a : this.path) {
			for (Activity t : temp) {
				if (a.getName().equals(t.getName())) {
					inList = true;
				}
			}
			if (!inList) {
				temp.add(a);
			}
		}
		String output = "";
		for (Activity a : temp) {
			output += a.getName()+",";
		}
		if (output != null && output.length() > 0 && output.charAt(output.length() - 1) == ',') {
	        output = output.substring(0, output.length() - 1);
	    }
		return output;
	}
	
	public String toCycle() {
		List<Activity> temp = new LinkedList<>();
		boolean inList = false;
		for (Activity a : this.path) {
			for (Activity t : temp) {
				if (a.getName().equals(t.getName())) {
					inList = true;
				}
			}
			if (!inList) {
				temp.add(a);
			}
		}
		
		List<Activity> temp2 = new LinkedList<>();
		int count = 0;
		for (Activity t : temp) {
			for (Activity a : this.path) {
				if (a.getName().equals(t.getName())) {
					count+=1;
				}
			}
			if (count>1) {
				temp2.add(t);
				count=0;
			}
		}
		temp = temp2;
		
		String output = "";
		for (Activity a : temp) {
			output += a.getName()+",";
		}
		if (output != null && output.length() > 0 && output.charAt(output.length() - 1) == ',') {
	        output = output.substring(0, output.length() - 1);
	    }
		return output;
	}

	public Iterator<Activity> iterator() {
		return this.path.iterator();
	}
}