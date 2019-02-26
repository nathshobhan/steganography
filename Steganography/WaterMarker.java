package handson;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

public class WaterMarker 
{

  boolean stringWaterMark(String srcImg, String mark, String trgtImg)
  {
    try
    {
      //load the srcImg in memory
      File srcF = new File(srcImg);
      BufferedImage bImg = ImageIO.read(srcF);
    
      //get the writing pen to the buffered image
      Graphics pen = bImg.getGraphics();
      
      //set the font
      Font f = new Font("Comic Sans MS", Font.PLAIN, 40);//name, style, size
      pen.setFont(f);
      
      //set the color
      //pen.setColor(Color.BLUE);
      Color c= new Color(97,63,199);
      pen.setColor(c);

      //write the given text
      pen.drawString(mark, 70, 400);//string, x,y (top left : 0,0)
      
      //save back
      File trgtF = new File(trgtImg);
      ImageIO.write(bImg, "PNG", trgtF);

      return true;
    }
    catch(Exception ex)
    {
      System.out.println("Err : "+ ex);
      return false;
    }
  }

  boolean imgWaterMark(String srcImg, String markImg, String trgtImg)
  {
    try
    {
      //load the srcImg in memory
      File srcF = new File(srcImg);
      BufferedImage bImg = ImageIO.read(srcF);
    
      //get the writing pen to the buffered image
      Graphics pen = bImg.getGraphics();
      

      //load the watermark image in memory
      File markF = new File(markImg);
      BufferedImage mImg = ImageIO.read(markF);

      //draw the given image
      pen.drawImage(mImg, 80, 320, null);//img, x,y, null
      
      //draw an outline
      pen.setColor(Color.red);
      pen.drawRect(79, 319, mImg.getWidth()+1, mImg.getHeight()+ 1);//x,y,w,h
      
      //save back
      File trgtF = new File(trgtImg);
      ImageIO.write(bImg, "PNG", trgtF);//DS, format, targetFile

      return true;
    }
    catch(Exception ex)
    {
      System.out.println("Err : "+ ex);
      return false;
    }
  }

}
