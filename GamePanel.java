//By Amy Hebert pls be nice to me
import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.Random;
import java.util.*;
import java.io.*;

public class GamePanel extends JPanel
{
private Color c; //this will be used to determine color while passing through the constructor and adding to array
private int rows;
private int columns;
private int x;
private int y;
private int vbX; //victory block x pos
private int vbY;
private ArrayList<ArrayList<GameObject>> blocks = new ArrayList<ArrayList<GameObject>>(); //list of list of game objects
private Player p;
private Block VictoryBlock;
private JOptionPane j;
private boolean winner = false;

public GamePanel()
{
   super();
   setPreferredSize(new Dimension(830,630));
   setBackground(Color.BLACK);
   
   addKeyListener(new KeyEventDemo()); //add key listener to panel
   setFocusable(true);
   Timer t = new Timer(10, new TimeListener());
   t.start(); //start timer
   
   if(winner)
   {
      add(j); //pop up 
   }
   try
   { 
      Scanner readFile = new Scanner(new File("project.txt")); //read file in (can be hardcoded)
      //first int is x position and 2nd is y position of where the player starts in blocks
      x = readFile.nextInt();
      y = readFile.nextInt();
      //3rd int is the rows
      rows = readFile.nextInt();
      //4th int is number of colomns
      columns = readFile.nextInt();
      x = x*(800/columns); 
      y = y*(600/rows)+(y*25);
      int [][] data = new int[rows][columns]; //create a 2D array to hold the data of the text file
      for(int i=0; i<rows; i++)
      {
         blocks.add(new ArrayList<GameObject>()); //this creates the outer list 
         for(int j=0; j<columns; j++)
         {
            data[i][j] = readFile.nextInt(); //read the data from the text file into the 2D array
            if(data[i][j] == 1) //if the data is a 1
            {
               //multiply the i/j value by the length/width of the panel divided by how many rows or columns there are
               blocks.get(i).add(j, new Block(j*(800/columns), i*(600/rows), Color.BLUE, Color.PINK)); //add block to spot i,j in the blocks array
            }
            if(data[i][j] == 0)
            {
               blocks.get(i).add(j, null); //set the spot on the array to null
            }
            if(data[i][j]==2)
            {
               vbX =j*(800/columns);
               vbY = i*(600/rows); //create a victory block (instance of block)
               VictoryBlock = new Block(vbX, vbY, Color.GREEN, Color.GREEN.darker());
               blocks.get(i).add(null); //do not add as a regular block this way the collides methods do not apply
            }
         }
      }
   }
   catch(FileNotFoundException fne) { System.out.println("no file is found"); }
   p = new Player(x, y, Color.RED, Color.PINK); 
   VictoryBlock = new Block(vbX, vbY, Color.GREEN, Color.GREEN.darker());
}

public void paintComponent(Graphics g)
{  
  super.paintComponent(g);
  g.setColor(Color.GRAY);
  g.fillRect(15,15,800,600); //make smaller than the panel so there is a black border
  //have to loop through the 2D array and draw whatever is indicated by the txt file
  for(int i=0; i<blocks.size(); i++) 
  { 
   for(int j=0; j<blocks.get(i).size(); j++)
   {
      if(blocks.get(i).get(j) != null) //only draw it if it is not null (there is soemthing to draw)
      { 
         blocks.get(i).get(j).draw(g); 
      } //this will pass in this paint component then draw whatever is indicated in the GameObjects paint component
   } 
  }
  p = new Player(x, y, Color.RED, Color.PINK);
  VictoryBlock = new Block(vbX, vbY, Color.GREEN, Color.GREEN.darker()); //create the victory block here (so we can see if player collides w it? )
  VictoryBlock.draw(g);
  p.draw(g);

}

public class KeyEventDemo implements KeyListener 
  {
    public void keyTyped(KeyEvent e) {} 
    public void keyReleased(KeyEvent e) 
    {
      if(e.getKeyCode() == KeyEvent.VK_A) 
      { left = false; }
      if(e.getKeyCode() == KeyEvent.VK_D) 
      { right = false; }
    }
    public void keyPressed(KeyEvent e) 
    {
      if(e.getKeyCode() == KeyEvent.VK_W) //only need key pressed for up because it intiates "jump"
      { 
         up = true; 
      }
      if(e.getKeyCode() == KeyEvent.VK_A) 
      { 
         left = true;
      }
      if(e.getKeyCode() == KeyEvent.VK_D) 
      { 
          right = true;
      }

  repaint();
    }
} 
boolean up; //booleans from key pressed released
boolean left;
boolean right;

private int win;

private double gravity = 1; //gravity starts at 1
private double gravitySpeed;
private int moveX;
private int moveY;
private double jump = 7; //jump starts at 7
private double jumpSpeed = jump;
//private int topBlock;
//private int bottomBlock;

public class TimeListener implements ActionListener //this is the timer so it runs smoothly
{
   public void actionPerformed(ActionEvent e)
      {  
      
         VictoryBlock = new Block(vbX, vbY, Color.GREEN, Color.GREEN.darker());  
         Player p = new Player(x, y, Color.RED, Color.PINK);
         
         if(up) //if the player clicks the W
         {      
           if(p.hitCieling((int)jumpSpeed, blocks))
           {    
               jumpSpeed = 0;  
           }
           y -= jumpSpeed; 
           jumpSpeed -= 0.1; //subtract 0.1 from the jump speed with every timer tick
           if(jumpSpeed <= 0)
           {
             jumpSpeed = jump;
             up = false; //if it is less than or equal to 0 then stop jumping
             moveY = 0;
             gravitySpeed = 1; //reset the gravity
           } 
         }
         gravitySpeed++;
         if(gravitySpeed % 7 == 0) //every 20 ticks (until gravity is 7) increase by 1
         {
            moveY++; 
         }  
         if(p.isOnGround(moveY, blocks))
         {
               moveY = 0;
               gravitySpeed = 1;
               y+=0; //keep in the same position
         }
         else if(!p.isOnGround(moveY, blocks))
         {
            y+=moveY;     
         }
         if(left)
         {
            if(p.move(-1, 0, blocks)) //check to see if this move is possible
               moveX = -1;
            else
               moveX = 0;
            x += moveX; //either stays in the same position or moves one pixel to the left
         }
         if(right)
         {
            if(p.move(1, 0, blocks))
               moveX = 1;
            else
               moveX = 0;
            x+=moveX;
         }

         if(p.winGame(VictoryBlock)) 
         {   
             win++;
             if(win>1)
               winner = true;
         }   
             
         if(winner)
         {
            JOptionPane j = new JOptionPane();
            j.showMessageDialog(null, "WINNER!!!");
            System.exit(1);
         }
         
         repaint(); //calls the paint component
      }
}

}


