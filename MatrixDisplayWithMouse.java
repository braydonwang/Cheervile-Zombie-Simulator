import javax.swing.JFrame; 
import javax.swing.JPanel; 
import java.awt.Toolkit; 
import java.awt.Graphics; 
import java.awt.Image;
import java.awt.Color; 
import java.awt.Font;
import java.awt.event.MouseListener; 
import java.awt.event.MouseEvent; 
import java.awt.event.MouseMotionListener;
 
/* [MatrixDisplayWithMouse.java] 
 * A small program showing how to use the MatrixDisplayWithMouse class 
 *  Authour: Braydon Wang 
 *  Date: 12/05/2019
 */ 
 
class MatrixDisplayWithMouse extends JFrame { 
  
  private int maxX,maxY, GridToScreenRatio; 
  private Organism[][] matrix; 
  private int row;
  private int col;
  private int turn;
  private int numberOfHumans;
  private int numberOfZombies;
  private int numberOfPlants;
  private int healthiestHuman;
  private int healthiestZombie;
  private int health;
  private int rowNumber;
  private int colNumber;
  private boolean gender;
  private int age;
  private int plantLife;
  private String organism;
   
  MatrixDisplayWithMouse(String title, Organism[][] matrix, int row, int col) { 
    super(title); 
     
    this.matrix = matrix; 
    maxX = Toolkit.getDefaultToolkit().getScreenSize().width; 
    maxY = Toolkit.getDefaultToolkit().getScreenSize().height; 
    
    GridToScreenRatio = maxY / (matrix.length+1);  //ratio to fit in screen as square map 
     
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize()); 
     
    this.add(new MatrixPanel()); 
     
    this.setVisible(true); 
    this.row = row;
    this.col = col;
    this.turn = 0;
    this.numberOfHumans = 0;
    this.numberOfZombies = 0;
    this.numberOfPlants = 0;
    this.healthiestHuman = 0;
    this.healthiestZombie = 0;
    this.health = 0;
    this.rowNumber = 0;
    this.colNumber = 0;
    this.gender = true;
    this.age = 0;
    this.plantLife = 0;
    this.organism = "";
  } 
   
   public void refresh() {  
    this.repaint(); 
  }
   public void increaseTurn() {
     this.turn++;
   }
   public void setNumberOfHumans(int amount) {
     this.numberOfHumans = amount;
   }
   public void setNumberOfZombies(int amount) {
     this.numberOfZombies = amount;
   }
   public void setNumberOfPlants(int amount) {
     this.numberOfPlants = amount;
   }
   public void setHealthiestHuman(int health) {
     this.healthiestHuman = health;
   }
   public void setHealthiestZombie(int health) {
     this.healthiestZombie = health;
   }
   
  //Inner Class  
  class MatrixPanel extends JPanel { 
   
    MatrixPanel() {  
       
      addMouseListener(new MatrixPanelMouseListener()); 
      addMouseMotionListener(new MatrixPanelMouseListener()); 
    } 
     
    public void paintComponent(Graphics g) {         
      super.repaint(); 
       
      setDoubleBuffered(true);  
      
      //Initializing all images used in the program
      Image maleDown = Toolkit.getDefaultToolkit().getImage("MaleDown.png");
      Image maleUp = Toolkit.getDefaultToolkit().getImage("MaleUp.png");
      Image maleRight = Toolkit.getDefaultToolkit().getImage("MaleRight.png");
      Image maleLeft = Toolkit.getDefaultToolkit().getImage("MaleLeft.png");
      Image femaleDown = Toolkit.getDefaultToolkit().getImage("FemaleDown.png");
      Image femaleUp = Toolkit.getDefaultToolkit().getImage("FemaleUp.png");
      Image femaleRight = Toolkit.getDefaultToolkit().getImage("FemaleRight.png");
      Image femaleLeft = Toolkit.getDefaultToolkit().getImage("FemaleLeft.png");
      Image zombieUp = Toolkit.getDefaultToolkit().getImage("ZombieUp.png");
      Image zombieDown = Toolkit.getDefaultToolkit().getImage("ZombieDown.png");
      Image zombieLeft = Toolkit.getDefaultToolkit().getImage("ZombieLeft.png");
      Image zombieRight = Toolkit.getDefaultToolkit().getImage("ZombieRight.png");
      Image youngPlant = Toolkit.getDefaultToolkit().getImage("YoungPlant.png");
      Image maturePlant = Toolkit.getDefaultToolkit().getImage("MaturePlant.png");
      Image dyingPlant = Toolkit.getDefaultToolkit().getImage("DyingPlant.png");
      Image poisonFungus = Toolkit.getDefaultToolkit().getImage("PoisonFungus.png");
      Image grass = Toolkit.getDefaultToolkit().getImage("Grass.png");
      Image heart = Toolkit.getDefaultToolkit().getImage("Heart.png");
      
      //Initializing all fonts
      Font  f1  = new Font(Font.SANS_SERIF, Font.BOLD,  15);
      Font f2 = new Font(Font.SANS_SERIF,Font.BOLD,30);
      
      //Creating the background grass
      for (int i = 0; i < matrix[0].length; i++) {
        for (int j = 0; j < matrix.length; j++) {
          g.drawImage(grass,GridToScreenRatio*j,GridToScreenRatio*i,GridToScreenRatio,GridToScreenRatio,null);
        }
      }
      
      //Displaying the images for the organisms
      for(int i = 0; i < matrix[0].length; i++)  {  
        for(int j = 0; j < matrix.length; j++)  {         
          if (matrix[i][j] instanceof Human){ 
            if (((Human)matrix[i][j]).getGender()){
              if (matrix[i][j].getDirection() == 1) {
                g.drawImage(maleUp,GridToScreenRatio*j-GridToScreenRatio/4,GridToScreenRatio*i-GridToScreenRatio/4,GridToScreenRatio+GridToScreenRatio/2,GridToScreenRatio+GridToScreenRatio/2,null);
              } else if (matrix[i][j].getDirection() == 2) {
                g.drawImage(maleDown,GridToScreenRatio*j-GridToScreenRatio/4,GridToScreenRatio*i-GridToScreenRatio/4,GridToScreenRatio+GridToScreenRatio/2,GridToScreenRatio+GridToScreenRatio/2,null);
              } else if (matrix[i][j].getDirection() == 3) {
                g.drawImage(maleLeft,GridToScreenRatio*j-GridToScreenRatio/4,GridToScreenRatio*i-GridToScreenRatio/4,GridToScreenRatio+GridToScreenRatio/2,GridToScreenRatio+GridToScreenRatio/2,null);
              } else {
                g.drawImage(maleRight,GridToScreenRatio*j-GridToScreenRatio/4,GridToScreenRatio*i-GridToScreenRatio/4,GridToScreenRatio+GridToScreenRatio/2,GridToScreenRatio+GridToScreenRatio/2,null);
              }
            } else {
              if (matrix[i][j].getDirection() == 1) {
                g.drawImage(femaleUp,GridToScreenRatio*j-GridToScreenRatio/4,GridToScreenRatio*i-GridToScreenRatio/4,GridToScreenRatio+GridToScreenRatio/2,GridToScreenRatio+GridToScreenRatio/2,null);
              } else if (matrix[i][j].getDirection() == 2) {
                g.drawImage(femaleDown,GridToScreenRatio*j-GridToScreenRatio/4,GridToScreenRatio*i-GridToScreenRatio/4,GridToScreenRatio+GridToScreenRatio/2,GridToScreenRatio+GridToScreenRatio/2,null);
              } else if (matrix[i][j].getDirection() == 3) {
                g.drawImage(femaleLeft,GridToScreenRatio*j-GridToScreenRatio/4,GridToScreenRatio*i-GridToScreenRatio/4,GridToScreenRatio+GridToScreenRatio/2,GridToScreenRatio+GridToScreenRatio/2,null);
              } else {
                g.drawImage(femaleRight,GridToScreenRatio*j-GridToScreenRatio/4,GridToScreenRatio*i-GridToScreenRatio/4,GridToScreenRatio+GridToScreenRatio/2,GridToScreenRatio+GridToScreenRatio/2,null);
              }
            }
          } else if (matrix[i][j] instanceof PoisonFungus) {
            g.drawImage(poisonFungus,GridToScreenRatio*j-GridToScreenRatio/4,GridToScreenRatio*i-GridToScreenRatio/4,GridToScreenRatio+GridToScreenRatio/2,GridToScreenRatio+GridToScreenRatio/2,null);
          } else if (matrix[i][j] instanceof Plant) {
            if (((Plant)matrix[i][j]).getLife() >= 0 && ((Plant)matrix[i][j]).getLife() <= 4){
              g.drawImage(youngPlant,GridToScreenRatio*j-GridToScreenRatio/4,GridToScreenRatio*i-GridToScreenRatio/4,GridToScreenRatio+GridToScreenRatio/2,GridToScreenRatio+GridToScreenRatio/2,null);
            } else if (((Plant)matrix[i][j]).getLife() >= 5 && ((Plant)matrix[i][j]).getLife() <= 9){
              g.drawImage(maturePlant,GridToScreenRatio*j-GridToScreenRatio/4,GridToScreenRatio*i-GridToScreenRatio/4,GridToScreenRatio+GridToScreenRatio/2,GridToScreenRatio+GridToScreenRatio/2,null);
            } else {
              g.drawImage(dyingPlant,GridToScreenRatio*j-GridToScreenRatio/4,GridToScreenRatio*i-GridToScreenRatio/4,GridToScreenRatio+GridToScreenRatio/2,GridToScreenRatio+GridToScreenRatio/2,null);
            }
          } else if (matrix[i][j] instanceof Zombie) {
            if (matrix[i][j].getDirection() == 1) {
              g.drawImage(zombieUp,GridToScreenRatio*j-GridToScreenRatio/4,GridToScreenRatio*i-GridToScreenRatio/4,GridToScreenRatio+GridToScreenRatio/2,GridToScreenRatio+GridToScreenRatio/2,null);
            } else if (matrix[i][j].getDirection() == 2) {
              g.drawImage(zombieDown,GridToScreenRatio*j-GridToScreenRatio/4,GridToScreenRatio*i-GridToScreenRatio/4,GridToScreenRatio+GridToScreenRatio/2,GridToScreenRatio+GridToScreenRatio/2,null);
            } else if (matrix[i][j].getDirection() == 3) {
              g.drawImage(zombieLeft,GridToScreenRatio*j-GridToScreenRatio/4,GridToScreenRatio*i-GridToScreenRatio/4,GridToScreenRatio+GridToScreenRatio/2,GridToScreenRatio+GridToScreenRatio/2,null);
            } else {
              g.drawImage(zombieRight,GridToScreenRatio*j-GridToScreenRatio/4,GridToScreenRatio*i-GridToScreenRatio/4,GridToScreenRatio+GridToScreenRatio/2,GridToScreenRatio+GridToScreenRatio/2,null);
            }
          }  
        }   
      }
      //Displaying the visuals and statistics
      g.setFont(f1);
      g.drawString("Turn Number: " + turn, 1080, 50);
      g.drawString("" + numberOfHumans, 1000, 700 - numberOfHumans*7 - 20);
      g.drawString("" + numberOfZombies, 1150, 700 - numberOfZombies*7 - 20);
      g.drawString("" + numberOfPlants, 1300, 700 - numberOfPlants*7 - 20);
      g.drawString("Healthiest Human: " + healthiestHuman, 1065, 85);
      g.drawString("Healthiest Zombie: " + healthiestZombie, 1063, 120);
      g.drawString("Humans", 975, 725);
      g.drawString("Zombies", 1125, 725);
      g.drawString("Plants", 1285, 725);
      
      //Displaying the statistics of the organism that the cursor hovers over
      g.setFont(f2);
      if (organism.equals("HUMAN")) {
        g.drawString(organism,1080,200);
        g.setFont(f1);
        g.drawString("Row number: " + rowNumber, 1000,245);
        g.drawString("Column number: " + colNumber, 1000,275);
        g.drawString("Health: " + health, 1000,305);
        g.drawString("Age: " + age, 1190,260);
        if (gender) {
          g.drawString("Gender: Male", 1185,290);
          g.drawImage(maleDown,1200,150,80,80,null);
        } else {
          g.drawString("Gender: Female",1185,290);
          g.drawImage(femaleDown,1200,150,80,80,null);
        }
      } else if (organism.equals("ZOMBIE")) {
        g.drawString(organism,1080,200);
        g.setFont(f1);
        g.drawString("Row number: " + rowNumber, 1000,245);
        g.drawString("Column number: " + colNumber, 1000,275);
        g.drawString("Health: " + health, 1000,305);
        g.drawImage(zombieDown,1200,150,80,80,null);
      } else if (organism.equals("PLANT")) {
        g.drawString(organism,1090,200);
        g.setFont(f1);
        g.drawString("Row number: " + rowNumber, 1000,260);
        g.drawString("Column number: " + colNumber, 1000,290);
        g.drawString("Health: " + health, 1185,260);
        g.drawString("Plant life: " + plantLife, 1185,290);
        if (plantLife >= 0 && plantLife <= 4) {
          g.drawImage(youngPlant,1200,150,80,80,null);
        } else if (plantLife >= 5 && plantLife <= 9) {
          g.drawImage(maturePlant,1200,150,80,80,null);
        } else {
          g.drawImage(dyingPlant,1200,150,80,80,null);
        }
      } else if (organism.equals("POISON FUNGUS")) {
        g.drawString(organism,995,200);
        g.setFont(f1);
        g.drawString("Row number: " + rowNumber, 1000,260);
        g.drawString("Column number: " + colNumber, 1000,290);
        g.drawString("Health: " + health, 1185,260);
        g.drawString("Plant life: " + plantLife, 1185,290);
        g.drawImage(poisonFungus,1240,150,80,80,null);
      }
      
      g.drawImage(heart,1240,73,15,15,null);
      g.drawImage(heart,1240,107,15,15,null);
      g.drawLine(970,700,1350,700);
      g.setColor(Color.BLACK);
      g.drawRect(970,150,350,200);
      
      //Displaying the bar graph with the specified colour and size
      if (numberOfHumans >= 15) {
        g.setColor(Color.GREEN);
      } else if (numberOfHumans >= 10) {
        g.setColor(Color.YELLOW);
      } else{
        g.setColor(Color.RED);
      }
      g.fillRect(975, 700 - numberOfHumans*7, 70, numberOfHumans*7);
      g.setColor(Color.BLACK);
      g.drawRect(975, 700 - numberOfHumans*7, 70, numberOfHumans*7);
      
      if (numberOfZombies >= 15) {
        g.setColor(Color.GREEN);
      } else if (numberOfZombies >= 10) {
        g.setColor(Color.YELLOW);
      } else{
        g.setColor(Color.RED);
      }
      g.fillRect(1125, 700 - numberOfZombies*7, 70, numberOfZombies*7);
      g.setColor(Color.BLACK);
      g.drawRect(1125, 700 - numberOfZombies*7, 70, numberOfZombies*7);
      
      if (numberOfPlants >= 15) {
        g.setColor(Color.GREEN);
      } else if (numberOfPlants >= 10) {
        g.setColor(Color.YELLOW);
      } else{
        g.setColor(Color.RED);
      }
      g.fillRect(1275, 700 - numberOfPlants*7, 70, numberOfPlants*7);
      g.setColor(Color.BLACK);
      g.drawRect(1275, 700 - numberOfPlants*7, 70, numberOfPlants*7);
    } 
  }
   
  //Mouse Listener  
  class MatrixPanelMouseListener implements MouseListener,MouseMotionListener{ 
    public void mouseMoved(MouseEvent e) {
      //Gets the statistics of the specified organism through the location of the cursor
      int x = (int) (e.getX() / GridToScreenRatio);
      int y = (int) (e.getY() / GridToScreenRatio);
      if (x >= 0 && x < col && y >= 0 && y < row && matrix[y][x] != null) {
        rowNumber = y;
        colNumber = x;
        health = matrix[y][x].getHealth();
        if (matrix[y][x] instanceof Human) {
          organism = "HUMAN";
          gender = ((Human)matrix[y][x]).getGender();
          age = ((Human)matrix[y][x]).getAge();
        } else if (matrix[y][x] instanceof Zombie) {
          organism = "ZOMBIE";
        } else if (matrix[y][x] instanceof PoisonFungus) {
          organism = "POISON FUNGUS";
          plantLife = ((Plant)matrix[y][x]).getLife();
        } else {
          organism = "PLANT";
          plantLife = ((Plant)matrix[y][x]).getLife();
        }
      }
    }
    public void mouseDragged(MouseEvent e) {
      //System.out.println("Mouse dragged");
    }
    public void mousePressed(MouseEvent e) { 
      //Creates a new zombie if user clicks a human
      int tempx = (int) (e.getPoint().x / GridToScreenRatio);
      int tempy = (int) (e.getPoint().y / GridToScreenRatio);
      if (tempx >= 0 && tempx < col && tempy >= 0 && tempy < row && matrix[tempy][tempx] instanceof Human){
        int health = matrix[tempy][tempx].getHealth();
        int direction = matrix[tempy][tempx].getDirection();
        matrix[tempy][tempx] = new Zombie(tempx,tempy,health,direction); 
      }
    }
 
    public void mouseReleased(MouseEvent e) { 
      //System.out.println("Mouse released; # of clicks: " + e.getClickCount()); 
    } 
    
    public void mouseEntered(MouseEvent e) { 
      //System.out.println("Mouse entered"); 
    } 
    
    public void mouseExited(MouseEvent e) { 
      //System.out.println("Mouse exited"); 
    } 
    
    public void mouseClicked(MouseEvent e) { 
      //System.out.println("Number of zombies: " + numberOfZombies); 
    } 
  }   
}