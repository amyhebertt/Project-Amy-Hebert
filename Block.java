import java.awt.*;
import javax.swing.*;

public class Block extends GameObject //inherits from GameObject no new memeber variables but new methods
{
   public Block(int x, int y, Color c, Color outline) //adds nothing but it passes its own parameters into the super constructor
   {
      super(x, y, c, outline);
   }
   //Victory block is just an instance of block with its own color (green)
}