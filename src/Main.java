import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void instructions(){
        // System.out.println(ANSI.ANSI_RESET);
        System.out.println("------------------------------------");
        System.out.println("Which game would you like to play?:");
        System.out.println("- Rock Papers Scissors (RPS)");
        System.out.println("- Tic Tac Toe (TTT)");
        System.out.println("- Hangman (H)");
        System.out.print(">> ");
    }

    public static String checkPlayerRPS(String rpsMove){
        return switch (rpsMove.toUpperCase()) {
            case "R", "ROCK" -> "Rock";
            case "P", "PAPER" -> "Paper";
            case "S", "SCISSORS" -> "Scissors";
            default -> "Invalid Shorthand";
        };
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        while(true){
            instructions();
            String input = scan.nextLine().trim().toUpperCase();
            switch(input){
                case "RPS": RPS(scan); break;
                case "TTT": TTT(scan); break;
                case "H": H(scan); break;
                case "E", "EXIT": System.out.println("Thank you for playing!"); return;
                default: System.out.println("Error: Choose A Game (RPS, H, TTT)");
            }
            System.out.flush();
        }
    }

    public static void RPS(Scanner scan){      
        String[] array = {"Rock","Scissors","Paper"};
        Random random = new Random();
        int index = random.nextInt(3);
        System.out.println("Please Choose: Rock(R), Paper(P), Scissors(S)");
        System.out.print(">> ");
        String line = scan.nextLine().trim().toUpperCase();
        String playerRPS = checkPlayerRPS(line);
        while(playerRPS.equals("Invalid Shorthand")){
            System.out.println("Invalid Shorthand");
            line = scan.nextLine().trim().toUpperCase();
        }

        String systemRPS = array[index];
        System.out.println("System chose " + systemRPS + ".");
        if (playerRPS.equals(systemRPS)) {
            System.out.println("Draw!");
        } else {
            boolean win =
                    (playerRPS.equals("Rock") && systemRPS.equals("Scissors")) ||
                    (playerRPS.equals("Paper") && systemRPS.equals("Rock")) ||
                    (playerRPS.equals("Scissors") && systemRPS.equals("Paper"));
            if (win) {
                // System.out.println(ANSI.ANSI_GREEN);
                System.out.println("You Win!");
            } else {
                // System.out.println(ANSI.ANSI_RED);
                System.out.println("You Lose!");
            }
            // System.out.print(ANSI.ANSI_RESET);
        }
    }

    public static void H(Scanner scan){
        int fails = 0;
        String[] SystemWords = {"control", "alt", "delete"};
        Random random = new Random();
        int index = random.nextInt(3);
        String currentWord = SystemWords[index];
        char[] playerWord = new char[currentWord.length()];
        for(int i = 0; i < currentWord.length(); i++){
            System.out.print("_ ");
            playerWord[i] = '_';
        }
        System.out.println();

        boolean checkHangman = false; 
        while(fails <= 6 && !checkHangman){
            System.out.println("Please Guess A Letter: ");
            System.out.print(">> ");
            String line = scan.nextLine().trim().toLowerCase();
            
            if(!currentWord.contains(line)){
                fails += 1;
                System.out.println("You Guessed Incorrectly!");
            }

            for(int i = 0; i < currentWord.length(); i++){
                if(currentWord.charAt(i) == line.charAt(0)){
                    playerWord[i] = line.charAt(0);
                    System.out.println("You Guessed Correctly!");       
                }                      
            }
            for (char c : playerWord) {
                System.out.print(c + " ");
            }
            System.out.println();
           
            checkHangman = true;
            for(int i = 0; i < playerWord.length; i++){
                if(playerWord[i] == '_'){
                    checkHangman = false;
                }
            }
        }

        if(checkHangman){
            System.out.println("You Win!");
        } else if(fails > 6) {
            System.out.println("You Lose!");
        }
    }

    public static void TTT(Scanner scan){
        String[] emptyGrid = new String[9];
        Arrays.fill(emptyGrid, "_");
        printBoard(emptyGrid);
        Random random = new Random();
        int index = random.nextInt(2);
        System.out.println("Heads or Tails? (H/T): ");
        System.out.print(">> ");
        String line = scan.nextLine().trim().toUpperCase();

        while(line.length() != 1 || !Character.isLetter(line.charAt(0))){
            System.out.println("Enter a single letter.");
            line = scan.nextLine().trim().toUpperCase();
        }

        boolean isGameOver = false;
        boolean playerStarts = (line.equals("H") && index == 0) 
                            || (line.equals("T") && index == 1);
        System.out.println(playerStarts ? "Player Starts." : "System Starts.");

        while (!isGameOver) {
            if (playerStarts) {
                TTTPlayerTurn(scan, emptyGrid);
                TTTSystemTurn(emptyGrid);
            } else {
                TTTSystemTurn(emptyGrid);
                TTTPlayerTurn(scan, emptyGrid);
            }
            isGameOver = checkTTTGrid(emptyGrid);
        }
    }

    public static void printBoard(String[] TTTGrid){
        for (int i = 0; i < 9; i++) {
            System.out.print(TTTGrid[i] + " ");
            if (i % 3 == 2) System.out.println();
        }
    }

    public static String[] TTTPlayerTurn(Scanner scan, String[] TTTGrid){
        System.out.println("Please Choose a Position (0 - 8): ");
        System.out.print(">> ");
        String line = scan.nextLine();
        try{
            int pos = Integer.parseInt(line);

            while(pos < 0 || pos > 8){
                System.out.println("Error: Position Invalid. (0-8)");
                line = scan.nextLine();
            }
            while(TTTGrid[pos].equals("O")){
                System.out.println("Error: Position Taken.");
                line = scan.nextLine();
            } 
            TTTGrid[pos] = "X";
            printBoard(TTTGrid);
        } catch (NumberFormatException e) {
            System.out.println("Enter a valid number.");
        }
        return TTTGrid;
    }

    public static String[] TTTSystemTurn(String[] TTTGrid){
        Random random = new Random();
        int index = random.nextInt(9);
        while(TTTGrid[index].equals("X")){
            System.out.println("Error: Position Taken.");
        } 
        TTTGrid[index] = "O";
        printBoard(TTTGrid);
        return TTTGrid;
    }

    public static boolean checkTTTGridMove(String[] TTTGrid, String move){
        return (move.equals(TTTGrid[0]) && move.equals(TTTGrid[1]) && move.equals(TTTGrid[2])) ||
        (move.equals(TTTGrid[3]) && move.equals(TTTGrid[4]) && move.equals(TTTGrid[5])) ||
        (move.equals(TTTGrid[6]) && move.equals(TTTGrid[7]) && move.equals(TTTGrid[8])) ||
        (move.equals(TTTGrid[0]) && move.equals(TTTGrid[3]) && move.equals(TTTGrid[6])) ||
        (move.equals(TTTGrid[1]) && move.equals(TTTGrid[4]) && move.equals(TTTGrid[7])) ||
        (move.equals(TTTGrid[2]) && move.equals(TTTGrid[5]) && move.equals(TTTGrid[8])) ||
        (move.equals(TTTGrid[0]) && move.equals(TTTGrid[4]) && move.equals(TTTGrid[8])) ||
        (move.equals(TTTGrid[2]) && move.equals(TTTGrid[4]) && move.equals(TTTGrid[6]));
    }

    public static boolean checkTTTGrid(String[] TTTGrid){
        if(checkTTTGridMove(TTTGrid, "X")){
            System.out.println("Player Wins!");
            return true;
        }
        if(checkTTTGridMove(TTTGrid, "O")){
            System.out.println("System Wins!");
            return true;
        }
        return false;
    }

}