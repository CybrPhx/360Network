package DiagramParser;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.List;
import java.io.*;
import java.util.Scanner;

public class CreatePanel extends JPanel
 {
   // initializing all the variables needed for the class
   private ArrayList nodeList;
   private JButton process;
   private JButton next;
   private JButton restart;
   private JButton quit;
   private JButton about;
   private JButton help;
   private JLabel label1;
   private JLabel label2;
   private JLabel label3;
   private JLabel label4;
   private JTextField field1;
   private JTextField field2;
   private JTextField field3;
   private JTextArea area1;
   private JScrollPane pane1;
   private final String FILENAME = 
		   "C:\\Users\\Sergio Rodríguez\\Documents\\CSC\\Java\\src\\DiagramParser\\out.txt";
   private FileWriter fw = null;
   private BufferedWriter bw = null;
   private int changed_duration;
   private String changed_activity;
   private static List<String> predecessor = new ArrayList<String>();
   private static List<Activity> activities;

 //Constructor initializes components and organize them using certain layouts
 public CreatePanel(ArrayList nodeList)
  {
    this.nodeList = nodeList;
    
    try{
    fw = new FileWriter(FILENAME);
    bw = new BufferedWriter(fw);
    } catch (Exception e){ System.out.println("my error: " + e);};
    
    //organize components here
	//setting gridlayout for the content
	this.setLayout(new GridLayout(0,2));

	//declaring all values for the labels
	label1 = new JLabel("");
	label2 = new JLabel("Enter activity");
	label3 = new JLabel("Enter dependency");
	label4 = new JLabel("Enter duration");



	//setting up new fields with a space of 15
	field1 = new JTextField(15);
	field2 = new JTextField(15);
	field3 = new JTextField(15);



	//setting up JPanels for organiztion of the layouts
	JPanel panel1= new JPanel();
	JPanel panel2= new JPanel();
	JPanel panel3= new JPanel();
	JPanel panel4= new JPanel();
	panel1.setLayout(new GridLayout(3,2));
	panel2.setLayout(new GridLayout(0,1));
	panel3.setLayout(new BorderLayout());
	panel4.setLayout(new GridLayout(2,3));

	//adding the panel to the container
	this.add(panel3);

	//adding the labels and text fields to the panel in order for the panel to be added to the container

	panel1.add(label2);
	panel1.add(field1);
	panel1.add(label3);
	panel1.add(field2);
	panel1.add(label4);
	panel1.add(field3);
	panel2.add(label1);


	//Panel layouts being set
	panel3.add(panel1,BorderLayout.CENTER);
	panel3.add(panel2,BorderLayout.NORTH);
    panel3.add(panel4,BorderLayout.SOUTH);

	//creating a button for the panel
    process = new JButton("Process");
    next = new JButton("Next");
    restart = new JButton("Restart");
    quit = new JButton("Quit");
    about = new JButton("About");
    help = new JButton("Help");
    panel4.add(restart);
    panel4.add(next);
    panel4.add(process);
    panel4.add(quit);
    panel4.add(about);
    panel4.add(help);
    panel3.add(panel4,BorderLayout.SOUTH);

	//making the text area a scroll pane, so people can view their past inputs
	area1 = new JTextArea();
	area1.setEditable(false);
	area1.setText("Node list\n");
	pane1 = new JScrollPane(area1);
	this.add(pane1);

	//setting the label red
  	label1.setForeground(Color.RED);

	//creating a button listener
  	ButtonListener ButtonL = new ButtonListener();
  	process.addActionListener(new ButtonListener());
  	next.addActionListener(new ButtonListener());
  	restart.addActionListener(new ButtonListener());
  	quit.addActionListener(new ButtonListener());
  	about.addActionListener(new ButtonListener());
  	help.addActionListener(new ButtonListener());
  }



  private class ButtonListener implements ActionListener
    {
		public void actionPerformed(ActionEvent event)
         {
			/*
			 * once you manually add the activities, duration, dep's by pressing next
			 * they will all transfer to the out.txt file which is then read here when the 
			 * keyword PROCESS is activated
			 */

			if(event.getSource()==process){
				
			//making sure the text fields all have an input
			if (field1.getText().equals("")){
				label1.setText("Please enter all fields");
			}
			else if (field2.getText().equals("")){
				label1.setText("Please enter all fields");
			}
			else if (field3.getText().equals("")){
				label1.setText("Please enter all fields");
			}
			else
			{
			/*
			 * This is where all the funkiness goes.
			 */
				activities = new LinkedList<>();
			    File file = 
			      new File("C:\\Users\\Sergio Rodríguez\\Documents\\CSC\\Java\\src\\DiagramParser\\out.txt"); 
			    Scanner sc;
			    try
			    {
			    sc = new Scanner(file);
			    
			    int activitycount = 1;
			    
				while ( sc.hasNextLine() ){
				
				String s = sc.nextLine();
				
				String[] tokens = s.split(",");
			   
				String nodeName;
				String getDuration;
				String getPredecessor;
				
				if (tokens.length <= 2)
				{
					System.out.println("in if true");
					//System.out.println("Insert Activity: ");
					nodeName = tokens[0];
					
					//System.out.println("Insert Duration: ");
					getDuration = tokens[1];
				
					//System.out.println("Insert Dependencies: ");
					getPredecessor = "";
				}
				else
				{
					System.out.println("in else");
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
		        
		        predecessor = Arrays.asList(getPredecessor.split(" "));
		        Activity newActivity = new Activity(null);
				newActivity.setName(nodeName);
				newActivity.setDuration(Integer.parseInt(getDuration));
				for(int i = 0; i < predecessor.size(); i++) {		
					newActivity.addPredecessor(predecessor.get(i));
				}
				
				activities.add(newActivity);
				activitycount++;
				
				String k = Network.createTree(activities, -1, null);
				
				area1.setText("here's all path: \n" + k);
				}
			
			    } catch (Exception e){ System.out.println("MyError15: " + e); };
			}
		}
			/*
			 * Here is where the NEXT
			 */
		else if(event.getSource()==next){

				String content = "";
				content = field1.getText() + "," + field3.getText() + "," + field2.getText();
				try{
				bw.append(content);
				bw.flush();
				fw.flush();
				bw.newLine();

				} catch(Exception k){System.out.println("MyError14: " + k); };
		}
		else if(event.getSource()==restart){
			area1.setText("");
			field1.setText("");
			field2.setText("");
			field3.setText("");
			try{
			bw.write("");
			bw.flush();
			} catch (Exception e){System.out.println("MyError17: " + e);};
			}
		else if(event.getSource()==help){
			JOptionPane.showMessageDialog(null,"The application will recieve three inputs from the user at once.\n"
												+ "Activity: Input for the name of the Activity.\n"
												+ "Dependencies: The input for the dependencies of the activity.\n"
												+ "Duration: Integer input for the length of the Activity.\n"
												+ "Process: Takes all the nodes, find the paths and displays the text of paths.\n"
												+ "Next: Gets input and stores the information.\n"
												+ "Restart: Resets all of the inputs and outputs.\n"
												+ "About: Gives information about the application.\n"
												+ "Quit: Ends the application.");
			}
		else if(event.getSource()==about){
			JOptionPane.showMessageDialog(null,"This is an application that is designed to take the three inputs\n activity, dependencies, and duration. "
												+ "The porgram will take all\n the inputs and make paths of dependencies and sort the paths\n in decending order. "
												+ "Each path will have the names of the\n activities and the total durations.");
			}
		else if(event.getSource()==quit){
			System.exit(0);
			}



         } //end of actionPerformed method
    }
 }