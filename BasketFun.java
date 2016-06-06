import java.awt.*;
import java.awt.image.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.event.*;

/** The BasketFun class creates the game screen panel and contains all of the game logic. The game screen will have 5
  * food buttons that are different depending on the selected level, a pause button to pause the game midway, a
  * button to reset everything in the basket, and a check button to see if the user has fulfilled the request. These
  * buttons are created in this class. It will also add the background to the panel.
  * 
  * @author Top Of The Stack(C Liu)
  * @version 1 05.18.16 Spent 4 hours
  * 
  * @author of modifications by Top of The Stack (Alice Z)
  * @version 2 05.20.16 Spent 3 hours
  * Added actionListeners to some food buttons. Now, a picture of the food they represent appears onto the screen.
  * The constructor now only takes in the level number rather than both the level difficulty and level number. The
  * constructor will make the level panel depending on the level number. There are various themes for each level. Level
  * 1 is forest themed, level 2 is ocean themed, and level 3 is farm themed.
  * 
  * @author of modifications by Top of The Stack (Alice Z)
  * @version 3 06.02.16 Spent 2 hours
  * Added methods to generate requests, randomize amounts of fruits, and randomize foods. Each level generates a
  * different kind of food and has a different maximum amount. The constructor now calls the method to generate the
  * requests after setting up the level panel. Added a String array to store the names of the foods to help with the
  * randomization of the foods.
  * 
  * @author of modifications by Top of The Stack (Caroline L)
  * @version 4 06.02.16 Spent  hours
  * 
  * @author of modifications by Top of The Stack (Alice Z)
  * @version 4.1 06.04.16 Spent 0.5 hours
  * The constructor now has the JFrame from GameRunner being passed in as a reference variable.
  * 
  * @author of modifications by Top of The Stack (Alice Z)
  * @version 5 06.05.16 Spent 3 hours
  * Now, when the panel is created, the images of the food and the amount of it are already in the basket. Each time the
  * user presses the button, the number of that food increases. Empty the Basket button is now functional and when
  * pressed, all the added food values are set to 0. The method to randomize foods is fully functional as well. The
  * method to generate requests has been modified so that the food amounts needed to fulfill the level are stored under
  * the new variable foodRequest. The processing for the Check button is being worked on and the method generateRequest
  * is being modified to fit with it.
  * 
  * @author of modifications by Top of The Stack (Alice Z)
  * @version 5.1 06.06.16 Spent 0.5 hours
  * Check button is now fully functioning and will unlock and move on to the next level if a certain amount of requests
  * is fulfilled.
  * 
  * <p>
  * <b> Instance variables: </b>
  * <p>
  * <b> levelNum </b> This int is used to store which level the user chose to play.
  * <p>
  * <b> bCol </b> This Color variable is used to store the background colour.
  * <p>
  * <b> backName </b> This String is used to store the file name of the background image being used for the level.
  * <p>
  * <b> j </b> This referene variable is used to point at the JFrame object that is created in the constructor.
  * <p>
  * <b> chars </b> This String array contains the names of the animal JPG's used in our game.
  * <p>
  * <b> foods </b> This String array contains the names of the foods.
  */ 

public class BasketFun extends JPanel{
  
  private int levelNum, randAmnt1, randAmnt2, requestNum = 0, maxRequest;
  private String backName;
  private Color bCol;
  JFrame j;
  String [] chars, foods = new String [5];
  private SpringLayout s;
  private int [] foodCount = {0,0,0,0,0}, foodRequest = new int[5];
  
  /**The class constructor will create a JPanel that is added to a JFrame that is also created here. The buttons that
    * all levels have in common: pause, empty, and check, are made and added here as well. The layout used is flow. 
    * Depending on the level selected, the backgrounds, button creation methods, and themes are different. The if
    * statement is to determine which button creation method to call.
    * @param levelNum This int is used to store which level the user has chosen to play.
    * @param bName This String is used to store the name of the background file.
    * @param s This Color is used to store the colour of the background.
    */
  public BasketFun(int level, String bName, Color w, JFrame jf ) { 
    super();
    levelNum=level;
    backName=bName;
    bCol=w;
    j = jf;
    j.setSize(1000,850);
    setPreferredSize(new Dimension(1000,900));
    s = new SpringLayout();
    setLayout(s);
    JButton check=new JButton("CHECK!");
    JButton empty=new JButton ("Empty the basket!");
    int maxNum;
    JButton pause = makeButtons("Pause","Click here to pause the game!");
    s.putConstraint (s.NORTH, pause, 0, s.WEST, this);
    add(pause);
    pause.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e)
      {
        //stop timer, make things appear Caroline
      }});
    
    if (levelNum==1) //3 requests
    {
      foods[0] = "Apple";
      foods[1] ="Orange";
      foods[2] = "Banana";
      foods[3] ="Grape";
      foods[4] ="Watermelon";
      makePanel1();
      maxNum = 3;
      maxRequest = 3;
    }
    else if (levelNum==2) //5 requests
    {
      foods[0] = "Red";
      foods[1] = "Yellow";
      foods[2] = "Green";
      foods[3] = "Octopus";
      foods[4] = "Crab";
      makePanel2();
      maxNum = 5;
      maxRequest = 5;
    }
    else //5 requests
    {
      foods[0] = "Tomato";
      foods[1] = "RedA";
      foods [2] = "GreenA";
      foods [3]="Carrot";
      foods[4]="Potato";
      makePanel3();
      maxNum = 7;
      maxRequest = 5;
    }
    generateRequest(maxNum);
    s.putConstraint (s.NORTH, check, 50, s.NORTH, this);
    add(check);
    check.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e)
      {
        for(int x = 0;x < 5;x++)
        {
        System.out.println(foodCount[x] + " count");
        System.out.println(foodRequest[x] + " request");
        }
        if(foodAreSame())
        {
          requestNum++;
          System.out.println("You did it!");
          if(requestNum ==3 && levelNum == 1)
          {
            if(!Menus.getLevelOneDone())
            {
              Menus.setLevelOneTrue();
              j.remove(BasketFun.this);
              j.add(new BasketFun(2,"back2", new Color (0,126,255), j));
            }
            else
            {
              Menus.setLevelTwoTrue();
              j.remove(BasketFun.this);
              j.add(new BasketFun(3,"back3", new Color (37,177,77), j));
            }
            j.revalidate();
            j.repaint();
          }
          else
          {
          for(int x = 0;x < 5;x++)
          foodRequest[x]=0;
            generateRequest(maxNum);
          }
          for(int x = 0;x < 5;x++)
          foodCount[x] = 0;
    revalidate();
    repaint();
        }
      }});
    s.putConstraint (s.NORTH, empty, 25, s.WEST, this);
    add(empty); 
    empty.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e)
      {
        for(int x = 0;x < 5;x++)
          foodCount[x] = 0;
        revalidate();
        repaint();
      }});
    revalidate();
    repaint();
  }
 
  public boolean foodAreSame()
  {
    for(int x= 0;x < 5;x ++)
      if(foodCount[x]!=foodRequest[x])
      return false;
      return true;
  }
  private void generateRequest(int maxNum)
  {
    String randFood, randFood2;
    if(levelNum == 3)
    {
      randAmnt1= randomizeAmounts(maxNum);
      randAmnt2= randomizeAmounts(maxNum);
      randFood = randomizeFoods(randAmnt1);
      randFood2 = randomizeFoods(randAmnt2);
      if(randFood.indexOf("Apple") >= 0)
        foodRequest[0] = randAmnt1;
      else if(randFood.indexOf("Orange")>=0)
        foodRequest[1]=randAmnt1;
      else if(randFood.indexOf("Banana")>=0)
        foodRequest[2]=randAmnt1;
      else if(randFood.indexOf("Grape")>=0)
        foodRequest[3]=randAmnt1;
      else
        foodRequest[4]=randAmnt1;
      if(randFood2.indexOf("Apple") >= 0)
        foodRequest[0] = randAmnt2;
      else if(randFood2.indexOf("Orange")>=0)
        foodRequest[1]=randAmnt2;
      else if(randFood2.indexOf("Banana")>=0)
        foodRequest[2]=randAmnt2;
      else if(randFood2.indexOf("Grape")>=0)
        foodRequest[3]=randAmnt2;
      else
        foodRequest[4]=randAmnt1;
      System.out.println("I would like to have " + randAmnt1 + randFood + " and " + randAmnt2 + randFood2 +".");
    }
    else
    {
      randAmnt1= randomizeAmounts(maxNum);
      randFood = randomizeFoods(randAmnt1);
      if(randFood.indexOf("Apple") >= 0)
        foodRequest[0] = randAmnt1;
      else if(randFood.indexOf("Orange")>=0)
        foodRequest[1]=randAmnt1;
      else if(randFood.indexOf("Banana")>=0)
        foodRequest[2]=randAmnt1;
      else if(randFood.indexOf("Grape")>=0)
        foodRequest[3]=randAmnt1;
      else
        foodRequest[4]=randAmnt1;
      System.out.println("I would like to have " + randAmnt1 + randFood +".");
    }
  }
  
  private String randomizeFoods(int randAmnt)
  {
    int r = (int)(Math.random()*5);
    if(randAmnt !=1 && (levelNum != 2 || (r == 4 || r == 5)))
    {
      return " "+foods[r]+"s";
    }
    return " "+foods[r];
  }
  
  private int randomizeAmounts(int maxNum)
  {
    int r = (int)(Math.random()*maxNum +1);
    return r;
  }
  
  /**This method will make and add the forest themed buttons to the panel for Level 1.
    * The parameter for the inner methods is e - a reference variable for ActionEvent.
    * The try/catch is to errortrap FileIO.
    */
  private void makePanel1()
  {
    JButton apple =makeButtons(foods[0],"Click here to drop an apple into the basket!"), orange = makeButtons(foods[1],"Click here to drop an orange into the basket!");
    JButton banana =makeButtons(foods[2],"Click here to drop a banana into the basket!"), grape = makeButtons(foods[3],"Click here to drop an orange into the basket!");
    JButton watermelon =makeButtons(foods[4],"Click here to drop an apple into the basket!");
    Image picture; ImageIcon icon;JLabel label, fruitNum;
    
    try
    {
      s.putConstraint (s.WEST, apple, 150, s.WEST, this);
      add(apple);
      apple.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e)
        {
          foodCount[0]++;
          revalidate();
          repaint();
        }});
      picture =ImageIO.read (new File ("apple.jpg"));
      icon =new ImageIcon(picture);
      label =new JLabel(icon);
      s.putConstraint(s.WEST, label,460, s.WEST, BasketFun.this);
      s.putConstraint(s.NORTH, label,50, s.SOUTH, banana);
      add(label);
      
      s.putConstraint (s.WEST, orange, 300, s.WEST, this);
      add(orange);
      orange.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e)
        { foodCount[1]++;
          revalidate();
          repaint();
        }});
      picture =ImageIO.read (new File ("orange.jpg"));
      icon =new ImageIcon(picture);
      label =new JLabel(icon);
      s.putConstraint(s.WEST, label,460, s.WEST, BasketFun.this);
      s.putConstraint(s.NORTH, label,180, s.SOUTH, banana);
      add(label);
      
      s.putConstraint (s.WEST, banana, 450, s.WEST, this);
      add(banana);
      banana.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e)
        {
          foodCount[2]++;
          revalidate();
          repaint();
        }});
      picture =ImageIO.read (new File ("banana.jpg"));
      icon =new ImageIcon(picture);
      label =new JLabel(icon);
      s.putConstraint(s.WEST, label,630, s.WEST, BasketFun.this);
      s.putConstraint(s.NORTH, label,120, s.SOUTH, banana);
      add(label);
      
      s.putConstraint (s.WEST, grape, 600, s.WEST, this);
      add(grape);
      grape.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e)
        {
          foodCount[3]++;
          revalidate();
          repaint();
        }});
      picture =ImageIO.read (new File ("grape.jpg"));
      icon =new ImageIcon(picture);
      label =new JLabel(icon);
      s.putConstraint(s.WEST, label,800, s.WEST, BasketFun.this);
      s.putConstraint(s.NORTH, label,50, s.SOUTH, banana);
      add(label);
      
      s.putConstraint (s.WEST, watermelon, 750, s.WEST, this);
      add(watermelon);
      watermelon.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e)
        {
          foodCount[4]++;
          revalidate();
          repaint();
        }});
      picture =ImageIO.read (new File ("watermelon.jpg"));
      icon =new ImageIcon(picture);
      label =new JLabel(icon);
      s.putConstraint(s.WEST, label,800, s.WEST, BasketFun.this);
      s.putConstraint(s.NORTH, label,180, s.SOUTH, banana);
      add(label);
    }
    catch(IOException e)
    {}
  }
  
  
  /**This method will draw the level backgrounds depending on the level.
    * @param g This will be used to draw the image of the background onto the panel.
    * The try/catch is to errortrap FileIO.
    */
  public void paintComponent(Graphics g)
  {
    BufferedImage b=null, c=null,b1=null;
    super.paintComponent(g);
    try
    {
      b = ImageIO.read(new File (backName+".jpg"));
      g.drawImage(b,0,0,null);
      b1 = ImageIO.read(new File ("Basket.jpg"));
      g.drawImage(b1,450,90,null);
      if (levelNum==1)
      {
        //for (int i=0;i<chars.length;i++)
        //{
        c=ImageIO.read(new File("Squirrel"+".jpg"));
        g.drawImage(c,400,550,null);
        //}
      }
      else if (levelNum==2)
      {
        c=ImageIO.read(new File("Seal"+".jpg"));
        g.drawImage(c,-10,340,null);
        
      }
      else 
      {
        
        c=ImageIO.read(new File("Bunny"+".jpg"));
        g.drawImage(c,400,400,null);
      }
    }
    catch(IOException e){
    }
    g.setColor(Color.WHITE);
    g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
    g.drawString(Integer.toString(foodCount[0]), 540, 200);
    g.drawString(Integer.toString(foodCount[1]), 540, 330);
    g.drawString(Integer.toString(foodCount[2]), 720, 270);
    g.drawString(Integer.toString(foodCount[3]),900, 200);
    g.drawString(Integer.toString(foodCount[4]), 900, 330);
    g.setColor(new Color(51,51,51));
    
  }
  
  /**This method will make and add the underwater themed buttons to the panel for Level 2. */
  private void makePanel2()
  {
    add(makeButtons("Red","Click here to drop a red fish into the basket!"));
    add(makeButtons("Yellow","Click here to drop a yellow fish into the basket!"));
    add(makeButtons("Green","Click here to drop a green fish into the basket!"));
    add(makeButtons("Octopus","Click here to drop an octopus into the basket!"));
    add(makeButtons("Crab","Click here to drop a crab into the basket!"));
  }
  
  /**This method will make and add the farm themed buttons to the panel for Level 3.*/
  private void makePanel3()
  {
    add(makeButtons("Tomato","Click here to drop a tomato into the basket!"));
    add(makeButtons("RedA","Click here to drop a red apple into the basket!"));
    add(makeButtons("GreenA","Click here to drop a green apple into the basket!"));
    add(makeButtons("Carrot","Click here to drop a carrot into the basket!"));
    add(makeButtons("Potato","Click here to drop a potato into the basket!"));
  }
  
  /**This method creates the buttons based off of the passed in image file's name and the text for the tool tip. The
    * method then returns the button so that it can be added to the panel.
    * 
    * @param imageName String passed in the for the image file's name.
    * @param text String passed in text for the tool tip.
    */
  private JButton makeButtons(String imageName,String text)
  {
    ImageIcon icon=new ImageIcon(this.getClass().getResource( imageName + ".jpg"));
    JButton button=new JButton(icon);
    button.setBackground(bCol);
    button.setToolTipText(text);
    return button;
  }
  
//  public static void main(String[] args) { 
//    String []c={"Squirrel","Monkey","Panda"};
//    String []b={"Seal","Dolphin1","Turtle","Jelly","Narwhal"};
//    String []d={"Pig","Bunny"};
//    BasketFun s= new BasketFun (3,"back3", new Color (37,177,77),d);
//    //forest green and farm grass green;new Color(37,177,77)
//    //ocean 0,126,255
//  }
}
