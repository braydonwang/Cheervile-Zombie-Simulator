/*
 * [Human.java]
 * This is a class for creating humans
 * Authour: Braydon Wang
 * Date: 12/05/2019
*/

class Human extends Person{ 
  private int age; 
  private boolean gender; 
  private int pregnantTimer;
  Human(int x, int y, int age, boolean gender,int direction){ 
    super(x,y,20,direction); 
    this.age = age; 
    this.gender = gender; 
    this.pregnantTimer = 0;
  } 
  Human(int x, int y, int age, boolean gender, int health, int pregnantTimer, int direction){ 
    super(x,y,health,direction); 
    this.age = age; 
    this.gender = gender; 
    this.pregnantTimer = pregnantTimer;
  } 
  public boolean getGender(){ 
    return this.gender; 
  } 
  public int getAge(){ 
    return this.age; 
  }
  public void increaseAge(int amount){
    this.age += amount;
  }
  public int getPregnantTimer(){
    return this.pregnantTimer;
  }
  public void setPregnantTimer(int amount){
    this.pregnantTimer = amount;
  }
}