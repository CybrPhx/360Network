package DiagramParser;

import javax.swing.*;
import java.util.*;

public class CSE_360 extends JApplet
 {

   private int APPLET_WIDTH = 800, APPLET_HEIGHT = 300;


   private CreatePanel createPanel;
   private ArrayList nodeList;


   public void init()
    {

     nodeList = new ArrayList();
     createPanel = new CreatePanel(nodeList);

     getContentPane().add(createPanel);
     setSize (APPLET_WIDTH, APPLET_HEIGHT);
    }
}
