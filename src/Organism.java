/*
 * [Organism.java]
 * This is the abstract superclass that humans, zombies and plants all have in common
 * Authour: Braydon Wang
 * Date: 12/05/2019
*/

abstract class Organism{ 
  private int x; 
  private int y; 
  private int health; 
  private int direction;
  Organism(int xCoordinate, int yCoordinate, int health, int direction){ 
    this.x = xCoordinate; 
    this.y = yCoordinate; 
    this.health = health; 
    this.direction = direction;
  } 
  public int getXCoordinate(){ 
    return this.x; 
  } 
  public int getYCoordinate(){ 
    return this.y; 
  } 
  public int getHealth(){ 
    return this.health; 
  } 
  public void decreaseHealth(int amount){ 
    this.health -= amount; 
  } 
  public void increaseHealth(int amount){ 
    this.health += amount; 
  } 
  public void setX(int xCoordinate){ 
    if (this.x > xCoordinate) {
      this.direction = 3;
    } else if (this.x < xCoordinate) {
      this.direction = 4;
    }
    this.x = xCoordinate; 
  } 
  public void setY(int yCoordinate){ 
    if (this.y > yCoordinate) {
      this.direction = 1;
    } else if (this.y < yCoordinate) {
      this.direction = 2;
    }
    this.y = yCoordinate; 
  }
  public int getDirection() {
    return this.direction;
  }
  public void setDirection(int amount) {
    this.direction = amount;
  }
}