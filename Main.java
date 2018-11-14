package DiagramParser;

import java.io.*;
import java.util.*;
import java.util.*;


public class Main {
	
	public int changed_duration;
	public String changed_activity;
	private static List<String> predecessor = new ArrayList<String>();
	private static List<Activity> activities;
	
	public static void main(String[] args) throws Exception {
		
		System.out.println("in main");
		program();
	
	}
	public static void program() throws Exception{
		
		List<String> temporary = new LinkedList<String>();
		activities = new LinkedList<>();
		
	    File file = 
	      new File("C:\\Users\\Sergio Rodríguez\\Documents\\CSC\\Java\\src\\DiagramParser\\input.txt"); 
	    Scanner sc = new Scanner(file); 
		
	    int activitycount = 1;
	    
		while ( sc.hasNextLine() ){
		String s = sc.nextLine();
		
		String[] tokens = s.split(",");
	
		String nodeName;
		String getDuration;
		String getPredecessor;
		
		if (tokens.length <= 2)
		{
		
			//System.out.println("Insert Activity: ");
			nodeName = tokens[0];
			
			//System.out.println("Insert Duration: ");
			getDuration = tokens[1];
		
			//System.out.println("Insert Dependencies: ");
			getPredecessor = "";
		}
		else
		{
			//System.out.println("Insert Activity: ");
			nodeName = tokens[0];
			
			//System.out.println("Insert Duration: ");
			getDuration = tokens[1];
		
			//System.out.println("Insert Dependencies: ");
			getPredecessor= "";
			for (int x = 2; x < tokens.length; x++){
			getPredecessor = getPredecessor+ tokens[x] + " ";
			}
		}
        
        temporary.clear();
		
        
        predecessor = Arrays.asList(getPredecessor.split(" "));
        Activity newActivity = new Activity(null);
		newActivity.setName(nodeName);
		newActivity.setDuration(Integer.parseInt(getDuration));
		for(int i = 0; i < predecessor.size(); i++) {		
			newActivity.addPredecessor(predecessor.get(i));
		}
        
        
        /*
		Activity newActivity = new Activity(null);
		newActivity.setName(nodeName);
		newActivity.setDuration(Integer.parseInt(getDuration));
		for(int i = 0; i < predecessors.size(); i++) {		
			newActivity.addPredecessor(predecessors.get(i));
		
		}*/
		
		activities.add(newActivity);
		
		System.out.println("Here's Activity "+ activitycount +"\n" +  newActivity.toString() );
		activitycount++;
		}
		
		/**
		 * a final path system is created here with activities being the variable we hold til 
		 * the user wants to restart the program (?)
		 */
		try{
		String k = Network.createTree(activities, -1, null);
		
		System.out.println("here's all path: \n" + k);
		System.out.println();
		//System.out.println("starting new paths");
		//k = Network.createTree(activities, 69, "C");
		//System.out.println(k);
		
		/**
		 * writing out the activities and the network onto a file. 
		 * use K to parse through the paths to tell which ones to print 
		 */
		FileWriter fstream = 
				new FileWriter("C:\\Users\\Sergio Rodríguez\\Documents\\CSC\\Network\\src\\out.txt");
		
		
		} catch (Exception e){ System.out.println( "error " + e ); }
		sc.close();
	
}
}
