/*
 * [Zombie.java]
 * This is the class that creates zombies
 * Authour: Braydon Wang
 * Date: 12/05/2019
*/

class Zombie extends Person{ 
  Zombie(int x, int y){ 
    super(x,y,15,2); 
  } 
  Zombie(int x, int y, int health, int direction){ 
    super(x,y,health,direction); 
  } 
}