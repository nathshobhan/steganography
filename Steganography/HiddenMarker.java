package handson;

import java.io.*;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.*;

public class HiddenMarker 
{
  boolean writeHiddenMark(String srcImg, String mark, String trgtImg)
  {
    try
    {
      mark = makeHeader(mark);
      
      //load the image in memory
      File srcF = new File(srcImg);
      BufferedImage bImg = ImageIO.read(srcF );
      
      //access the pixels of the buffered image
      WritableRaster wrstr = bImg.getRaster();
      
      int w,h,x,y;
      int r,g,b;
      int i = 0;
      int bits[];
      int len;
      
      w = wrstr.getWidth();
      h = wrstr.getHeight();
      len = mark.length();
      //rowwise
      for(y =0; y < h && i < len; y++)
      {
        for(x =0; x < w && i < len; x++)
        {
          //fetch a pixel (band by band)
          r = wrstr.getSample(x, y, 0);//red
          g = wrstr.getSample(x, y, 1);//green
          b = wrstr.getSample(x, y, 2);//blue
      
          bits = getBits(mark.charAt(i++));

          //merge bits[0] into r
          r = (r & (~0x7)) | bits[0];
          //merge bits[1] into g
          g = (g & (~0x7)) | bits[1];
          //merge bits[2] into r
          b = (b & (~0x3)) | bits[2];
          
          //update the raster
          wrstr.setSample(x, y, 0, r);
          wrstr.setSample(x, y, 1, g);
          wrstr.setSample(x, y, 2, b);
          
          
        }//x loop
      }//y loop

      //update the buffered image
      bImg.setData(wrstr);
      
      //save back
      File trgtF = new File(trgtImg);
      ImageIO.write(bImg, "PNG", trgtF);//DS, format, targetFile

      
      return true;
    }
    catch(Exception ex)
    {
      System.out.println("Err "+ ex);
      return false;
    }
    
  }
  
  String readHiddenMark(String srcImg)
  {
    try
    {
      File fileImg = new File(srcImg);
      BufferedImage bImg = ImageIO.read(fileImg);
      //matrix like data structure that holds the pixels of the image
      Raster raster = bImg.getData();

      String result = "";
      int w, h;
      int x, y;
      int r,g,b;
      int i, len;
      int current;
      int bits;


      w = raster.getWidth();
      h = raster.getHeight();
      i =-1 ;//flag
      len = 0;//flag value

      for(y =0; y < h  && i < len; y++)
      {
        for(x =0 ; x < w && i < len; x++)
        {
          r = raster.getSample(x, y, 0);//red band
          g = raster.getSample(x, y, 1);//green band
          b = raster.getSample(x, y, 2);//blue band

          //idea is to fetch
          //3 lsb of r, 3 lsb of g and 2 lsb of b
          r = r & 0x7;
          g = g & 0x7;
          b = b & 0x3;

          //merge to form a byte
          current = 0;
          current = (current | r) <<3;
          current = (current | g) <<2;
          current = current | b;

          if(len== 0)
          {
            if((char)current == ',')
            {
              len = Integer.parseInt(result);
              result = "";
              i = 0;
            }
            else
              result = result  +(char) current;
          }
          else
          {
            result = result + (char) current;
            i++;
          }
        }//for(x
      }//for(y

      return result;
    }
    catch(Exception ex)
    {
      return "Err";
    }

  }

  
  private int[] getBits(char temp)
  {
    int q = (int)temp;//get the ascii
/*
    int arr[] = new int[3];
    arr[0] = q >> 5;
    arr[1] = (q >> 2) & 0x7;
    arr[2] = q & 0x3;
    return arr;
*/
    int arr[] = {q>>5, (q>>2)&0x7, q&0x3};
    return arr;
  }
  
  private String makeHeader(String s)
  {//computer --> 8,computer
    int q;
    q = s.length();
    return q+ ","+s;
  }
  
  
}
