import java.util.Scanner; 
import java.util.Arrays;

/*
 * [CheervilleProject.java]
 * This program is the solution to the Cheerville project
 * Authour: Braydon Wang
 * Date: 12/05/2019
*/

/*
 * An overview of the program:
 * 1. There are four main organisms on the map: Humans, zombies, plants and poison fungus
 * 2. Humans and zombies move one tile per turn and lose one health per turn as well
 * 3. Plants and poison fungus can gain or lose health depending on their age (plants in the growing stage gain health, while in the decaying stage, they lose health)
 * 4. Humans gain health by eating plants, while zombies gain health by eating poison fungus
 * 5. Humans are created when they collide with an opposite gender human and are both above the age of 20 (every turn = 5 years)
 * 6. The human that is created spawns in an empty space around their creator, but if all spaces are occupied, no human is created
 * 7. After creating a human, the female cannot produce anymore for another 9 turns
 * 8. Zombies can either infect humans and kill them when they collide
 * 9. Humans that eat poison fungus turn into zombies
 * 10. Humans and zombies can see one tile around them, which gives them better movement
 * 11. Humans will avoid zombies and poison fungus, and will go towards plants
 * 12. Plants spawn at a constant rate depending on the input and have a greater chance of spawning near other plants
 * 13. Initially, no zombies are on the map, but clicking a human will replace the human with a zombie
 * 14. The strongest human and zombie are displayed in the program, as well as a bar graph that shows the number of plants, humans and zombies at any given time
 * 15. All organisms are represented with an image and humans/zombies have a direction that they face based on their movement
 * 16. Hovering your cursor over any organism will display the basic statistics of it
 * 17. All organisms die once they reach a health of zero, but when humans become extinct, the program stops
 */
class CheervilleProject {  
  static int row; 
  static int col; 
  static int[][] direction = {{0,1},{0,-1},{1,0},{-1,0},{-1,1},{-1,-1},{1,-1},{1,1}};
  public static void main(String[] args) {
    //Initializing the scanner class
    Scanner sc = new Scanner (System.in); 
    
    //Getting the number of rows and columns for the grid
    System.out.println("Number of rows:");
    row = sc.nextInt(); //Recommended: 20
    System.out.println("Number of columns:");
    col = sc.nextInt(); //Recommended: 20
    
    //Creating the world map, the direction array and both plant coordinate arrays
    Organism[][] worldMap = new Organism[row][col];
    int[] plantX = new int[row*col];
    int[] plantY = new int[row*col];
    Organism[] humanArray;
    Organism[] zombieArray;
    
    //Filling both plant arrays with -1 to test if there is any empty space left
    Arrays.fill(plantX, -1);
    Arrays.fill(plantY, -1);
 
    //Getting input for number of humans, plants, plant spawning rate, plant value and fungus spawning rate
    System.out.println("How many humans?");
    int numberOfHumans = sc.nextInt(); //Recommended: 20?
    System.out.println("How many plants?");
    int numberOfPlants = sc.nextInt(); //Recommended: 20?
    System.out.println("What's the plant spawning rate?");
    int plantSpawningRate = sc.nextInt(); //Recommended: 1
    System.out.println("What's the starting nutritional value of the plant?");
    int plantValue = sc.nextInt(); //Recommended: 5
    System.out.println("What's the poisonous fungus spawning rate?");
    int fungusSpawningRate = sc.nextInt(); //Recommended: 2
    
    //Initializing all variables
    int turn = 0;
    int numberOfZombies = 0;
    int planted;
    boolean full = false;
    int index;
    int newx;
    int newy;
    int tempx;
    int tempy;
    int humanHealth;
    int zombieHealth;
    int stage;
    int plantLife;
    int nutrition;
    int count = 0;
    int PREGNANT_TIMER = 9;
    int strongestHuman = 0;
    int strongestZombie = 0;
    int hcount;
    int zcount;
    int dir;
     
    //Closing the scanner class
    sc.close(); 
     
    //Spawning the initial humans on the map 
    while (count <= numberOfHumans){ 
      newx = (int) (Math.random()*col); 
      newy = (int) (Math.random()*row); 
      int age = (int) (Math.random()*100); 
      int gender = (int)(Math.random()*2); 
      if (gender == 0){ //Spawn a male human with the randomly generated age and coordinates
        worldMap[newy][newx] = new Human(newx,newy,age,true,2); 
        count++;
      } else { //Spawn a female human with the randomly generated age and coordinates
        worldMap[newy][newx] = new Human(newx,newy,age,false,2); 
        count++;
      } 
    }
    
    //Spawning the initial plants on the map
    count = 0;
    while (count <= numberOfPlants && !full){
      newx = (int) (Math.random()*col); 
      newy = (int) (Math.random()*row);
      stage = (int)(Math.random()*3);
      if (stage == 0){ //Spawning a plant in the early stage of life
        plantLife = (int)(Math.random()*5);
        nutrition = (int)(Math.random()*5+plantValue);
      } else if (stage == 1){ //Spawning a plant in the mature stage of life
        plantLife = (int)(Math.random()*5+5);
        nutrition = (int)(Math.random()*5+plantValue+1);
      } else { //Spawning a plant in the decaying stage of life
        plantLife = (int)(Math.random()*5+10);
        nutrition = (int)(Math.random()*plantValue+1);
      }
      if (worldMap[newy][newx] == null){
        worldMap[newy][newx] = new Plant(newx,newy,nutrition,plantLife);
        count++;
        index = indexOf(plantX,-1);
        if (index != -1){
          plantX[index] = newx;
          plantY[index] = newy;
        } else {
          full = true;
        }
      }
    }
   
    MatrixDisplayWithMouse display = new MatrixDisplayWithMouse("Cheerville Simulator", worldMap, row, col);
    
    //Constantly run the program until humans become extinct
    while(numberOfHumans != 0) {  
      //Setting the number of plants, humans and zombies to be displayed, as well as the healthiest human and zombie
      display.increaseTurn();
      display.setNumberOfHumans(numberOfHumans);
      display.setNumberOfZombies(numberOfZombies);
      display.setNumberOfPlants(numberOfPlants);
      display.setHealthiestHuman(strongestHuman);
      display.setHealthiestZombie(strongestZombie);
      
      //Updates the world map
      display.refresh();       
      
      //Small delay 
      try{ Thread.sleep(500); }catch(Exception e) {};     
      
      //Moving all the humans and zombies on the map
      movementPerTurn(worldMap);
      
      //Spawning a plant based on the plant's spawning rate (spawns three plants per turn)
      if (turn % plantSpawningRate == 0) {
        planted = 0;
        do {
          int chance = (int)(Math.random()*4);
          //The plant has a 75% chance to spawn beside another plant
          if (chance >= 0 && chance <= 2){
            index = indexOf(plantX, -1);
            if (index != -1) {
              //Getting a random plant coordinate from the plant arrays
              int randNum = (int)(Math.random()*index);
              //Checking all eight directions around the random plant if there are any empty spaces to place it
              for (int i = 0; i < direction.length; i++) {
                newx = plantX[randNum]+direction[i][0];
                newy = plantY[randNum]+direction[i][1];
                if (withinGrid(newx,newy) && worldMap[newy][newx] == null) {
                  worldMap[newy][newx] = new Plant(newx, newy, plantValue, 0);
                  numberOfPlants++;
                  planted++;
                  break;
                }
              }
            }
          } else {
            //Spawning the plant on a random coordinate on the map
            newx = (int)(Math.random()*col);
            newy = (int)(Math.random()*row);
            if (worldMap[newy][newx] == null) {
              worldMap[newy][newx] = new Plant(newx,newy,plantValue,0);
              planted++;
              numberOfPlants++;
            }
          }
        } while(!fullGrid(worldMap) && planted < 3);    
      }
      
      //Spawning a poisonous fungus based on the fungus' spawning rate
      if (turn % fungusSpawningRate == 0) {
        planted = 0;
        do {
          newx = (int)(Math.random()*col);
          newy = (int)(Math.random()*row);
          if (worldMap[newy][newx] == null) {
            worldMap[newy][newx] = new PoisonFungus(newx,newy,0);
            planted++;
          }
        } while (!fullGrid(worldMap) && planted < 1);
      }
      
      //Changing the coordinates of every organism on the world map and checking for collision
      for (int i = 0; i < worldMap[0].length; i++) { 
        for (int j = 0; j < worldMap.length; j++) { 
          //Human organism occupies this coordinate
          if (worldMap[i][j] instanceof Human) { 
            tempx = worldMap[i][j].getXCoordinate(); 
            tempy = worldMap[i][j].getYCoordinate(); 
            int age = ((Human)worldMap[i][j]).getAge(); 
            boolean gender = ((Human)worldMap[i][j]).getGender(); 
            int health = worldMap[i][j].getHealth();
            int pregnantTimer = ((Human)worldMap[i][j]).getPregnantTimer();
            dir = worldMap[i][j].getDirection();
            
            //Collision with a poison fungus turns humans into zombies
            if (worldMap[tempy][tempx] instanceof PoisonFungus) {
              worldMap[i][j] = null;
              worldMap[tempy][tempx] = new Zombie(tempx,tempy,health,dir);
              
            //Collision with a plant gives humans additional health
            } else if (worldMap[tempy][tempx] instanceof Plant) {
              worldMap[i][j] = null; 
              int plantHealth = worldMap[tempy][tempx].getHealth();
              worldMap[tempy][tempx] = new Human(tempx,tempy,age,gender,health+plantHealth,pregnantTimer,dir);
              numberOfPlants--;
              
            //Collision with a zombie can infect the human or kill it
            } else if (worldMap[tempy][tempx] instanceof Zombie) {
              zombieHealth = worldMap[tempy][tempx].getHealth();
              //If the zombie has a health greater than the human, the human dies and the zombie gains health
              if (zombieHealth > health) {
                worldMap[i][j] = null;
                worldMap[tempy][tempx].increaseHealth(health);
              //If the zombie has a health less than or equal to the human, the human becomes a zombie
              } else {
                worldMap[i][j] = new Zombie(j,i,health,dir);
              }
              
            //Collision with a human could produce another human
            } else if (worldMap[tempy][tempx] instanceof Human) {
              //If the two humans have opposite genders, are both above the age of 20, and did not get pregnant recently, then another human can be created
              if (gender != ((Human)worldMap[tempy][tempx]).getGender() && age >= 20 && ((Human)worldMap[tempy][tempx]).getAge() >= 20 && !fullGrid(worldMap) && ((Human)worldMap[i][j]).getPregnantTimer() == 0 && 
                  ((Human)worldMap[tempy][tempx]).getPregnantTimer() == 0) {
                //Checking the eight possible spots around the human to place the human that was created
                for (int k = 0; k < direction.length; k++) {
                  int xCoordinate = j + direction[k][0];
                  int yCoordinate = i + direction[k][1];
                  if (yCoordinate >= 0 && yCoordinate < row && xCoordinate >= 0 && xCoordinate < col && worldMap[yCoordinate][xCoordinate] == null) {
                    int randGender = (int)(Math.random()*2);
                    if (randGender == 0) {
                      worldMap[yCoordinate][xCoordinate] = new Human(xCoordinate,yCoordinate,0,true,2);
                    } else {
                      worldMap[yCoordinate][xCoordinate] = new Human(xCoordinate,yCoordinate,0,false,2);
                    }
                    //Give the female human a pregant timer and prevent her from making babies for 9 more turns
                    if (gender) {
                      ((Human)worldMap[tempy][tempx]).setPregnantTimer(PREGNANT_TIMER);
                    } else {
                      ((Human)worldMap[i][j]).setPregnantTimer(PREGNANT_TIMER);
                    }
                    break;
                  }
                }
              }
              
            //If no collision occurs, move the human to the designated coordinates
            } else {
              worldMap[i][j] = null; 
              worldMap[tempy][tempx] = new Human(tempx,tempy,age,gender,health,pregnantTimer,dir); 
            } 
            
          //Zombie occupies this coodinates
          } else if (worldMap[i][j] instanceof Zombie) { 
            tempx = worldMap[i][j].getXCoordinate(); 
            tempy = worldMap[i][j].getYCoordinate(); 
            int health = worldMap[i][j].getHealth();
            dir = worldMap[i][j].getDirection();
            
            //Collision with a human could kill the human or infect the human
            if (worldMap[tempy][tempx] instanceof Human) {
              humanHealth = worldMap[tempy][tempx].getHealth();
              int humanDir = worldMap[tempy][tempx].getDirection();
              //Zombie kills the human and gains health
              if (health > humanHealth) {
                worldMap[i][j] = null;
                worldMap[tempy][tempx] = new Zombie(tempx,tempy,health,dir);
                worldMap[tempy][tempx].increaseHealth(humanHealth);
              //Human gets infected and becomes a zombie
              } else {
                worldMap[tempy][tempx] = new Zombie(tempx,tempy,humanHealth,humanDir);
              }
              
            //Collision with a poison fungus increases the zombie's health
            } else if (worldMap[tempy][tempx] instanceof PoisonFungus) {
              worldMap[i][j] = null;
              int fungusHealth = worldMap[tempy][tempx].getHealth();
              worldMap[tempy][tempx] = new Zombie(tempx,tempy,health+fungusHealth,dir);
              
            //Collision with a plant kills the plant
            } else if (worldMap[tempy][tempx] instanceof Plant) {
              worldMap[i][j] = null;
              numberOfPlants--;
              worldMap[tempy][tempx] = new Zombie(tempx,tempy,health,dir);
              
            //If no collision occurs, move the zombie to the designated coordinates
            } else if (worldMap[tempy][tempx] == null) {
              worldMap[i][j] = null;
              worldMap[tempy][tempx] = new Zombie(tempx,tempy,health,dir);
            }
          } 
        } 
      }
      
      //Counting the number of humans and zombies still on the map
      numberOfZombies = 0;
      numberOfHumans = 0;
      for (int i = 0; i < row; i++) {
        for (int j = 0; j < col; j++) {
          if (worldMap[i][j] instanceof Zombie) {
            numberOfZombies++;
          } else if (worldMap[i][j] instanceof Human) {
            numberOfHumans++;
          }
        }
      }
      
      //Decreasing the health of humans, zombies, plants and fungus by 1 health per turn
      for (int i = 0; i < worldMap[0].length; i++) {
        for (int j  = 0; j < worldMap.length; j++) {
          if (worldMap[i][j] instanceof Human || worldMap[i][j] instanceof Zombie) {
            worldMap[i][j].decreaseHealth(1);
            
            if (worldMap[i][j] instanceof Human) {
              //Decrease the pregnant timer of females every turn by 1, if they are pregnant
              if (((Human)worldMap[i][j]).getPregnantTimer() > 0){
                ((Human)worldMap[i][j]).setPregnantTimer(((Human)worldMap[i][j]).getPregnantTimer()-1);
              }
            }
            
            if (worldMap[i][j].getHealth() == 0) { 
              //Decrease the count of humans if their health reaches 0
              if (worldMap[i][j] instanceof Human) {
                numberOfHumans--;
              //Decrease the count of zombies if their health reaches 0
              } else {
                numberOfZombies--;
              }
              worldMap[i][j] = null; 
            }
            //Humans increase their age by 5 every turn
            if (worldMap[i][j] instanceof Human) {
              ((Human)worldMap[i][j]).increaseAge(5);
            }
          } else if (worldMap[i][j] instanceof PoisonFungus) {
            ((Plant)worldMap[i][j]).increaseLife(1);
            worldMap[i][j].decreaseHealth(1);
            //Delete fungus if their health reaches 0
            if (worldMap[i][j].getHealth() == 0) {
              worldMap[i][j] = null;
            }
          } else if (worldMap[i][j] instanceof Plant) {
            ((Plant)worldMap[i][j]).increaseLife(1);
            //If the plant's life span is above 5 turns, they are in the decaying process stage (lose health from now on)
            if (((Plant)worldMap[i][j]).getLife() >= 5) {
              worldMap[i][j].decreaseHealth(1);
              //Delete plant and reduce count by 1 if their health reaches 0
              if (worldMap[i][j].getHealth() == 0) {
                worldMap[i][j] = null;
                numberOfPlants--;
              }
            //The plant is growing larger so their health increases
            } else {
              worldMap[i][j].increaseHealth(1);
            }
          }
        }
      }
      //Determines the healthiest human and healthiest zombie using the comparable interface
      humanArray = new Organism[numberOfHumans];
      zombieArray = new Organism[numberOfZombies];
      hcount = 0;
      zcount = 0;
      strongestHuman = 0;
      strongestZombie = 0;
      for (int i = 0; i < row; i++) {
        for (int j = 0; j < col; j++) {
          if (worldMap[i][j] instanceof Human) {
            humanArray[hcount] = worldMap[i][j];
            hcount++;
          } else if (worldMap[i][j] instanceof Zombie) {
            zombieArray[zcount] = worldMap[i][j];
            zcount++;
          }
        }
      }
      if (numberOfHumans > 0) {
        strongestHuman = sortHealth(humanArray);
      }
      if (numberOfZombies > 0) {
        strongestZombie = sortHealth(zombieArray);
      }
    } 
  } 
  //Method that moves humans and zombies up, down, left or right per turn
  public static void movementPerTurn(Organism[][] data) { 
    boolean done = false;
    int newx;
    int newy;
    int tempx;
    int tempy;
    int[] directionArray = new int[2];
    for (int i = 0; i < row; i++) { 
      for (int j = 0; j < col; j++) { 
        done = false;
        if (data[i][j] instanceof Human) {
          for (int k = 0; k < direction.length; k++) {
            newx = j + direction[k][0];
            newy = i + direction[k][1];
            if (withinGrid(newx,newy)) {
              //Humans move away from zombies and poisonous fungus
              if (data[newy][newx] instanceof Zombie || data[newy][newx] instanceof PoisonFungus) {
                tempx = j - (newx - j);
                tempy = i - (newy - i);
                if (withinGrid(tempx,tempy)) {
                  directionArray = moveTowards(j,i,tempx,tempy);
                  data[i][j].setX(directionArray[0]);
                  data[i][j].setY(directionArray[1]);
                  done = true;
                  break;
                }
              //Humans move towards plants
              } else if (data[newy][newx] instanceof Plant) {
                  directionArray = moveTowards(j,i,newx,newy);
                  data[i][j].setX(directionArray[0]);
                  data[i][j].setY(directionArray[1]);
                  done = true;
                  break;
              }
            }
          }
  
        } else if (data[i][j] instanceof Zombie) {
          for (int k = 0; k < direction.length; k++) {
            newx = j + direction[k][0];
            newy = i + direction[k][1];
            if (withinGrid(newx,newy)) {
              //Zombies move towards humans and poisonous fungus
              if (data[newy][newx] instanceof Human || data[newy][newx] instanceof PoisonFungus) {
                directionArray = moveTowards(j,i,newx,newy);
                data[i][j].setX(directionArray[0]);
                data[i][j].setY(directionArray[1]);
                done = true;
                break;
              }
            }
          }
        }
        //If there are no organisms around the specified human or zombie, move randomly
        if (!done && (data[i][j] instanceof Human || data[i][j] instanceof Zombie)) {
          int direction = (int)(Math.random()*4); 
          if (direction == 0) { 
            if (data[i][j].getYCoordinate()-1 >= 0) { 
              ((Person)data[i][j]).moveUp(); 
            } 
          } else if (direction == 1) { 
            if (data[i][j].getYCoordinate()+1 < row) { 
              ((Person)data[i][j]).moveDown(); 
            } 
          } else if (direction == 2) { 
            if (data[i][j].getXCoordinate()-1 >= 0) { 
              ((Person)data[i][j]).moveLeft(); 
            } 
          } else if (direction == 3) { 
            if (data[i][j].getXCoordinate()+1 < col) { 
              ((Person)data[i][j]).moveRight(); 
            } 
          }
        }
      } 
    } 
  }
  //Method that checks if the grid is full
  public static boolean fullGrid(Organism[][] data) {
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        if (data[i][j] == null) {
          return false;
        }
      }
    }
    return true;
  }
  //Method that returns the index of the specific element in an array
  public static int indexOf(int[] array, int n) {
    for (int i = 0; i < array.length; i++) {
      if (array[i] == n) {
        return i;
      }
    }
    return -1;
  }
//Method that determines where to go based on their surroundings (better movement) 
  public static int[] moveTowards(int x, int y, int newx, int newy) {
    //A direction array that holds the final x and y coordinates and a direction variable that generates two random movements for diagonals
    int dir = (int)(Math.random()*2);
    int[] directionArray = new int[2];
    //If the surrounding organism is on the right hand side of the specified organism
    if (newx > x) {
      //Bottom right
      if (newy > y) {
        //Move right
        if (dir == 0) {
          directionArray[0] = x + 1;
          directionArray[1] = y;
          //Move down
        } else {
          directionArray[0] = x;
          directionArray[1] = y + 1;
        }
        //Directly right
      } else if (newy == y) {
        //Move right
        directionArray[0] = newx;
        directionArray[1] = newy;
        //Top right
      } else {
        //Move right
        if (dir == 0) {
          directionArray[0] = x + 1;
          directionArray[0] = y;
          //Move up
        } else {
          directionArray[0] = x;
          directionArray[1] = y - 1;
        }
      }
      //If the surrounding organism is directly above or directly below the specified organism
    } else if (newx == x) {
      //Move directly right or left towards it
      directionArray[0] = newx;
      directionArray[1] = newy;
      //If the surrounding organism is on the left hand side of the specified organism
    } else {
      //Bottom left
      if (newy > y) {
        //Move left
        if (dir == 0) {
          directionArray[0] = x - 1;
          directionArray[1] = y;
          //Move down
        } else {
          directionArray[0] = x;
          directionArray[1] = y + 1;
        }
        //Directly left
      } else if (newy == y) {
        //Move left
        directionArray[0] = newx;
        directionArray[1] = newy;
        //Top left
      } else {
        //Move left
        if (dir == 0) {
          directionArray[0] = x - 1;
          directionArray[1] = y;
          //Move up
        } else {
          directionArray[0] = x;
          directionArray[1] = y - 1;
        }
      }
    }
    return directionArray;
  }
  //Method that checks if a pair of coordinates are within the grid
  public static boolean withinGrid(int x, int y) {
    if (x >= 0 && x < col && y >= 0 && y < row) {
      return true;
    }
    return false;
  }
  //Method that sorts the array holding all human and zombie objects and sorting their health using the comparable interface
  public static int sortHealth(Organism[] healthArray) {
    Arrays.sort(healthArray);
    return healthArray[healthArray.length-1].getHealth();
  }
}