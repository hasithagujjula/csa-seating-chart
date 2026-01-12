/*
* Problem 1: Escape Room
* 
* V1.0
* 10/10/2019
* Copyright(c) 2019 PLTW to present. All rights reserved
*/
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

// class used to create timer for game
class timeManager extends TimerTask {

  public static int i = 30;

  public void run() {

    // when timer equals 0, produces game over and a score of zero
    if (i==0){
      System.out.println("Time's up! Game over.");
      System.out.println("Score=0");
      System.exit(0);
    }
    else{
      System.out.println("\n" + i + " seconds remaining.\nEnter command: ");
      i--;
    }
  }
}

/**
 * Create an escape room game where the player must navigate
 * to the other side of the screen in the fewest steps, while
 * avoiding obstacles and collecting prizes.
 */
public class EscapeRoom
{
  public static void main(String[] args) 
  {      

    // create timer
    Timer timer = new Timer();
    timer.schedule(new timeManager(), 0); // timer starts immediately when program starts
    timer.scheduleAtFixedRate(new timeManager(), 1000, 1000); // timer counts down every second
    
    // welcome message
    System.out.println("Welcome to EscapeRoom!");
    System.out.println("Get to the other side of the room, avoiding walls and invisible traps,");
    System.out.println("pick up all the prizes.\n");
    
    GameGUI game = new GameGUI();
    game.createBoard();

    // size of move
    int m = 60; 
    // individual player moves
    int px = 0;
    int py = 0; 
    
    int score = 0;

    Scanner in = new Scanner(System.in);
    String[] validCommands = {"r", "l", "u", "d", "jr", "jl", "ju", "jd", "p", "c", "s", "q", "re", "?"};
  
    // set up game
    boolean play = true;
    while (play)
    {

      // get user command and validate
      String input = in.nextLine().toLowerCase(); // changed this line of code so that it is easier to calculate score deductions for invalid commands

	    /* process user commands*/

      // move right
      if (input.equals(validCommands[0])){
        score += game.movePlayer(m, 0);
        // if player doesn't spring trap if trap exists, score decreases by 5
        if (game.isTrap(px, py)){
          System.out.println("You set off a trap! Score deducted by 5 points.");
          score -= 5;
        }
      }

      // move left
      else if (input.equals(validCommands[1])){
        score += game.movePlayer(-m, 0);
        // if player doesn't spring trap if trap exists, score decreases by 5
        if (game.isTrap(px, py)){
          System.out.println("You set off a trap! Score deducted by 5 points.");
          score -= 5;
        }
      }
      
      // move up
      else if (input.equals(validCommands[2])){
        score += game.movePlayer(0, -m);
        // if player doesn't spring trap if trap exists, score decreases by 5
        if (game.isTrap(px, py)){
          System.out.println("You set off a trap! Score deducted by 5 points.");
          score -= 5;
        }
      }

      // move down
      else if (input.equals(validCommands[3])){
        score += game.movePlayer(0, m);
        // if player doesn't spring trap if trap exists, score decreases by 5
        if (game.isTrap(px, py)){
          System.out.println("You set off a trap! Score deducted by 5 points.");
          score -= 5;
        }
      }

      // jump right
      else if (input.equals(validCommands[4])){
        score += game.movePlayer(2*m, 0);
        // if player doesn't spring trap if trap exists, score decreases by 5
        if (game.isTrap(px, py)){
          System.out.println("You set off a trap! Score deducted by 5 points.");
          score -= 5;
        }
      }

      // jump left
      else if (input.equals(validCommands[5])){
        score += game.movePlayer(-2*m, 0);
        // if player doesn't spring trap if trap exists, score decreases by 5
        if (game.isTrap(px, py)){
          System.out.println("You set off a trap! Score deducted by 5 points.");
          score -= 5;
        }
      }

      // jump up
      else if (input.equals(validCommands[6])){
        score += game.movePlayer(0, -2*m);
        // if player doesn't spring trap if trap exists, score decreases by 5
        if (game.isTrap(px, py)){
          System.out.println("You set off a trap! Score deducted by 5 points.");
          score -= 5;
        }
      }

      // jump down
      else if (input.equals(validCommands[7])){
        score += game.movePlayer(0, 2*m);
        // if player doesn't spring trap if trap exists, score decreases by 5
        if (game.isTrap(px, py)){
          System.out.println("You set off a trap! Score deducted by 5 points.");
          score -= 5;
        }
      }

      // pickup prize
      else if (input.equals(validCommands[8])){
        score += game.pickupPrize();
        if (game.isLollipop(px,py)){
          game.addMorePrizesAndTraps(); // adds more prizes and traps to the board if lollipop is picked up and spawns another lollipop
          timeManager.i += 10; // increases timer by 10 seconds if lollipop is picked up
          System.out.println("You picked up a lollipop! Timer increased by 10 seconds and more prizes and traps have been added to the board.");
        }

      }

      // check for trap
      else if (input.equals(validCommands[9])){
        boolean hasTrap = game.isTrap(px, py);
        if (hasTrap){
          System.out.println("There is a trap on your square! Score increased by 5 points.");
          score += 5;
        }
        else {
          System.out.println("There is no trap on your square.");
        }
      }

      // spring trap
      else if (input.equals(validCommands[10])){
        score += game.springTrap(px, py);
      }

      // quit game
      else if (input.equals(validCommands[11])){
        timer.cancel(); // stops old timer
        play = false;
      }

      // replay game
      else if (input.equals(validCommands[12])){
        timer.cancel(); // stops old timer
        timeManager.i = 30; // resets timer
        timer = new Timer(); // creates new timer
        score += game.replay();
        System.out.println("score=" + score);
        score = 0;

        System.out.println("Enter command: ");
        timer.schedule(new timeManager(), 0); // timer starts immediately when program starts
        timer.scheduleAtFixedRate(new timeManager(), 1000, 1000); // timer counts down every second
      }
      
      // help button
      else if (input.equals(validCommands[13])){
        System.out.print("\nr: move right\nl: move left\nu: move up\nd: move down\njr: skip over one space to the right\njl: skip over one space to the left\nju: skip over one space up\njd: skip over one space down\np: pick up candy on the board\nc: check if there is a trap existing on the player's square\ns: deactivate the trap on the square the player is on\nq: quit or end the game\nre: end the current game and replay the game\n?: open the help menu (what you're on right now :D)\n\n");
      }

      else{
        System.out.println("Invalid command. Type ? for a list of valid commands. Score deducted by 5 points.");
        score -= 5;
      }
    }

    score += game.endGame();

    System.out.println("score=" + score);
    System.out.println("steps=" + game.getSteps());
  }
}

        