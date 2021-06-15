/*
 * [Person.java]
 * This is the abstract class that holds characteristics that humans and zombies have in common
 * Authour: Braydon Wang
 * Date: 12/05/2019
*/

abstract class Person extends Organism implements Moveable, Comparable<Person>{
  Person(int x, int y, int health,int direction){ 
    super(x,y,health,direction); 
  } 
  public void moveUp() { 
    this.setY(this.getYCoordinate()-1); 
    this.setDirection(1);
  } 
  public void moveDown() { 
    this.setY(this.getYCoordinate()+1); 
    this.setDirection(2);
  } 
  public void moveLeft() { 
    this.setX(this.getXCoordinate()-1); 
    this.setDirection(3);
  } 
  public void moveRight() {  
    this.setX(this.getXCoordinate()+1); 
    this.setDirection(4);
  } 
  public int compareTo(Person person) {
    return this.getHealth() - person.getHealth();
  }
}