import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameObject 
{
protected int x; //x and y positions indicate the middle of the player rectangle
protected int y; //make the center 15
protected Color c; //color of this game object 
protected Color outline;

public GameObject(int x, int y, Color c, Color outline) //all game objects are rectangular and do not rotate
{
   this.x = x + 15; //add 15 so it goes off (approx) the center of the square
   this.y = y + 15;
   this.c = c;
   this.outline = outline;
}
public boolean collides(GameObject game) //takes in game object as a parameter and determines if the two game objects collide
{
   //compares to this game object

   if(this == game) //if the objects are the same (cannot collide with itself)
   {
      return false;
   }
   else 
   { 
      //have to add 10/subtract 14 to uncenter the x/ypos
      int topthis = y - 14; //top side y pos
      int bottomthis = y + 10; //the position of the bottom (the y value)
      int leftthis = x - 14; //the x position of the left side 
      int rightthis = x + 10; //right side x pos
      int topother = game.getY() - 14; 
      int bottomother = game.getY() + 10;
      int leftother = game.getX() - 14;
      int rightother = game.getX() + 10;
      
      /*System.out.println("topthis: " + topthis + " bottomthis: " + bottomthis + " leftthis: " + leftthis + " rightthis: " + rightthis 
      + " topthis: " + topother + " bottomother: " + bottomother + " leftother: " + leftother + " rightother: " + rightother);*/
      
      return !((bottomthis < topother) || (topthis > bottomother) || (leftthis > rightother) || (rightthis < leftother)); //return the opposite because this statement indicates that they do not crash
   }
                   
}
//getters and setters
public int getX()
{
   return x;
}
public void setY(int y)
{
   this.y = y;
}  
public void setX(int x)
{
   this.x = x;
}
public int getY()
{
   return y;
}

public void draw(Graphics g) //takes in a graphics object
{
    g.setColor(c);
    g.fillRect(x, y, 25, 25); //fill the rectangle 25x25
    g.setColor(outline);
    g.drawRect(x, y, 25, 25);
}

}