/*
 * [Plant.java]
 * This is the class that creates plants
 * Authour: Braydon Wang
 * Date: 12/05/2019
*/

class Plant extends Organism{
  private int plantLife;
  Plant(int x, int y, int health, int plantLife){
    super(x,y,health,1);
    this.plantLife = plantLife;
  }
  public int getLife(){
    return this.plantLife;
  }
  public void increaseLife(int amount){
    this.plantLife += amount;
  }
}