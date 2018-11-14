package DiagramParser;

import java.util.*;

public class Network {
    private static List<Activity> activities;
    private static List<Path> paths;
	
	public static String createTree(List<Activity> listActivity, int ch_dur, String ch_act) throws Exception {
    	// Create activities list
		activities = listActivity;
		
		/**
		 * here's where we check whether we want to change an activity
		 * from an old list of activities  
		 */
		if( ch_dur != -1)
		{
			for(Activity thisNode: activities)
			{
				if( thisNode.getName().equals(ch_act) )
				{
					thisNode.setDuration(ch_dur);
				}
			}
		}
		// rootNode is a secret starting node, to catch multiple starting nodes
		Activity rootNode = new Activity("start_node");
		activities.add(0, rootNode);
		
		// Sort activities, and create an array of nodes to create tree
		Collections.sort(activities);
		Activity[] nodes = new Activity[activities.size()];
    	int count = 0;
        for(Activity activity : activities) {
            nodes[count] = activity;
            nodes[count].setId(count);
            count++;
        }
        
        
        for(Activity node : nodes) {
        	// Connect empty predecessor nodes to rootNode
        	if (node.predecessors.size()!=0) {
	        	if (node.predecessors.get(0).getName().isEmpty()) {
	        		node.predecessors.get(0).setName(rootNode.getName());
	        	}
        	}
        	
       	// Add each predecessor as parent of node.
        	for (Predecessor p : node.predecessors) {
        		boolean pExists = false;
        		for(Activity n : nodes) {
        			if (n.getName().equals(p.getName())) {
        				nodes[n.id].addChild(node);
        				pExists = true;
        			}
       		}
        		if (!pExists) {
        			throw new UnconnectedNodeException("Unconnected nodes found" /*p.getName()*/);
        		}
        	}
        }
        

        // Create list of paths
        paths = new LinkedList<>();
        List<Path> lists = getPaths(nodes[0]);
        for(Path list : lists) {
        	// Get paths one at a time
        	Path path = new Path();
        	String pathName = "";
            for(count = 0; count < list.getPath().size(); count++) {
            	Activity currentNode = list.getPath().get(count);
            	if (!currentNode.getName().equals(rootNode.getName())) {
            		path.addActivity(currentNode);
                    pathName += currentNode.getName();
                    if(count != list.getPath().size() - 1) {
                        pathName += ",";
                    }
            	}
            }
            pathName+= "\n Duration is: " + path.getDuration() + "\n";
            path.setName(pathName);
            
            // Is this path already in the list?
            boolean exists = false;
            for (Path p: paths) {
            	if (p.getName().equals(path.getName())) {
            		exists = true;
            	}
            }
            if (!exists) {
            	paths.add(path);
            }
        }

        // This whole bottom part checks to see if paths are connected to form a network, and prints each path out if so
        String output = "";
        Collections.sort(paths);
        Path prevPath = new Path();
        boolean pathsConnected = false;
        for (Path p: paths) {
        	// Make sure each path is connected
        	if (!prevPath.getPath().isEmpty()) {
        		for (Activity a : p.getPath()) {
        			for (Activity ap : prevPath.getPath()) {
        				if (a.getName().equals(ap.getName())) {
        					pathsConnected = true;
        				}
        			}
        		}
        		if (!pathsConnected) {
            		throw new UnconnectedNodeException("Unconnected nodes found" /*+ p.toString() */);
            	}
        	}
        	prevPath = p;
        	pathsConnected=false;
        	output += p.getName();
        }
        return output;
    }
	
	private static List<Path> getPaths(Activity head) throws Exception {
        if(head == null) { 
            return new ArrayList<>();
        } else { 
        	// Recursively find each path with getEachPath()
        	List<Path> retPaths = new ArrayList<>();
            if(head.getChildren().size() == 0) {
                Path leafPath = new Path();
                leafPath.addActivity(head);
                retPaths.add(leafPath);
            } else {
                for (Activity node : head.getChildren()) {
                	// currentPath keeps track of the path to make sure that there are no repeated activities (aka cycles)
                	Path currentPath = new Path();
                	List<Path> nodePaths = getEachPath(node, currentPath);
                	currentPath = new Path();
                    for (Path nodePath : nodePaths) {
                        nodePath.addActivity(0, head);
                        retPaths.add(nodePath);
                    }
                }
            }
            return retPaths;
        }
    }
    
    // Recursive function to find each path
    private static List<Path> getEachPath(Activity pos, Path currentPath) throws Exception {
        List<Path> retPaths = new ArrayList<>();
        
        if(pos.getChildren().size() == 0) {
            Path leafPath = new Path();
            leafPath.addActivity(pos);
            retPaths.add(leafPath);
        } else {
        	currentPath.addActivity(pos);
    		for (Activity node : pos.getChildren()) {
                List<Path> nodePaths = getEachPath(node, currentPath);
                for (Path nodePath : nodePaths) {
                    nodePath.addActivity(0, pos);
                    retPaths.add(nodePath);
                }
            }
        }
        return retPaths;
    }
   
    public Iterator<Activity> iterator() {
		return activities.iterator();
	}
    
    public Iterator<Path> iterator1() {
		return paths.iterator();
	} 
}