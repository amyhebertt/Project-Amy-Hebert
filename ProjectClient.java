import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class ProjectClient extends JFrame
{
   public ProjectClient()
   {
      super("Amy's Project");
      Container contents = getContentPane();
      contents.setLayout(new FlowLayout()); //flow layout on frame
      setSize(860,690); 
      setVisible(true);      
      
      GamePanel game = new GamePanel();
      contents.add(game);
      game.requestFocus();
 
      
   }
   public static void main(String[] args)
   {
      ProjectClient theFrame = new ProjectClient();
      theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      
   }
}