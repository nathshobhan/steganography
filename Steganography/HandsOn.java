package handson;

public class HandsOn 
{
  public static void main(String[] args) 
  {
    HiddenMarker hm = new HiddenMarker();
    WaterMarker wm = new WaterMarker();
    
    /*
    if(wm.stringWaterMark("f:/Answer.png", "Aitian", "f://AitianAnswer.png"))
      System.out.println("DONE");
    else
      System.out.println("Failed");
    */
  
    /*
    if(wm.imgWaterMark("f:/Answer.png", "f:/logo.jpg", "f://AitianLogo.png"))
      System.out.println("DONE");
    else
      System.out.println("Failed");
    */
    
    String s = "This is some important data that we embed in our image, without spoiling the look";
    if(hm.writeHiddenMark("f:/Answer.png", s, "f://Answer1.png"))
    {
      //extract
      String s1;
      s1 = hm.readHiddenMark("f://Answer1.png");
      System.out.println(s1);
    }
    else
      System.out.println("Failed");
  }
  
}
