import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.util.*;
import java.io.*;

public class Player extends GameObject //inherits from GameObject no new memeber variables but new methods
{

public Player(double x, double y, Color c, Color outline)
{
   super((int)x, (int)y, c, outline); //type cast into int when passed into the super constructor
}

boolean onGround;
int topBlock;
public boolean isOnGround(int gravity, ArrayList<ArrayList<GameObject>> al) 
{
   this.y = this.y + gravity; //move the player down one
   if(this.collides(al)) //check for collisions
   {   
      onGround = true;
   }
   else if(!this.collides(al))
   {  onGround = false;  }
   this.y = this.y - gravity; //move back
   return onGround; //return if it collided
}
public boolean hitCieling(int moveY, ArrayList<ArrayList<GameObject>> al)
{
   this.y = this.y - moveY;
   if(this.collides(al))
    { return true; }
   else 
      return false;

}
boolean move;
public boolean move(int a, int b, ArrayList<ArrayList<GameObject>> al)
{
   this.x+=(a); //set the X to whatever was passed through
   this.y+=(b); 
   if(this.collides(al))
   {   
      move = false; 
      this.x-= (a); //move back to original position
      this.y-= (b);
   }
   else if (!this.collides(al))
   {  
       move = true; 
   }
   return move;
}

public boolean winGame(GameObject VB)
{
   return this.collides(VB); //just return if the player collides with the victory block
}

boolean collides;
ArrayList<GameObject> colList = new ArrayList<GameObject>();
public boolean collides(ArrayList<ArrayList<GameObject>> al)
{ 
  for(int i=0; i<al.size(); i++) //loop through array of blocks
   { 
      for(int j=0; j<al.get(i).size(); j++) 
      {
         if(al.get(i).get(j) != null) //if it is a block
         { 
            if(this.collides(al.get(i).get(j))) //if collides
            {
                colList.add(al.get(i).get(j)); //create a list of collisions so things can be fixed
                return true; //return true
            }
         }
      }
   } 
      return false;
}

}
