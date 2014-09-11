import javax.swing.JFrame;
import sun.audio.*;
import java.io.*;


public class Snake extends JFrame{
  private Board board;
  private AudioStream theme;
  
  public Snake(){
    super();
    setVisible(true);
    setResizable(false);
    setTitle("Snake? Snake!? SNAAAAAKE!");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(1000,500);
    setLocationRelativeTo(null);
    try{
      theme = new AudioStream(new FileInputStream("Sound/get lucky.wav"));
    }
    catch (Exception e){
      System.out.println("Sound file not found!");
      e.printStackTrace();
    }
    AudioPlayer.player.start(theme);
  }
  public Board getBoard(){
    return board;
  }
  public void musicStart(){
    try{
      theme = new AudioStream(new FileInputStream("Sound/get lucky.wav"));
    }
    catch (Exception e){
      System.out.println("Sound file not found!");
      e.printStackTrace();
    }
    AudioPlayer.player.start(theme);
  }
  public void musicStop(){
    AudioPlayer.player.stop(theme);
  }
  public void start(){
    board = new Board(true);
    add(board);
    board.repaint();
  }
  public void newGame(){
    if (board != null){
      board.pause();
      remove(board);
    }
    add(board = new Board());
    addKeyListener(board);
    repaint();
  }
  public void pause(){
    board.pause();
  }
  public void unPause(){
    board.unPause();
  }
  
  public static void main(String[] args){
    new Menu();
  }
}