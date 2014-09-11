import javax.swing.*;
import java.awt.event.*;
import java.awt.FlowLayout;
import sun.audio.*;
import java.io.*;

public class Menu extends JFrame implements ActionListener{
  private JButton newGameButton = new JButton("New Game");
  private JButton pauseButton = new JButton("Pause");
  private JButton unPauseButton = new JButton("Un-Pause");
  private JButton scoreBoard = new JButton("Scoreboard");
  private JButton optionsButton = new JButton("Options [WIP]");
  private Snake game = new Snake();
  private AudioStream beep;
  private Timer soundPlay = new Timer(300, this);
    
  public Menu(){
    setVisible(true);
    setResizable(false);
    setTitle("Menu");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(100,200);
    setLayout(new FlowLayout());
    add(newGameButton);
    add(pauseButton);
    add(unPauseButton);
    add(scoreBoard);
    add(optionsButton);
    pauseButton.addActionListener(this);
    unPauseButton.addActionListener(this);
    newGameButton.addActionListener(this);
    scoreBoard.addActionListener(this);
    optionsButton.addActionListener(this);
    try{
    beep = new AudioStream(new FileInputStream("Sound/Menu.wav"));
    }
    catch(Exception e){
      System.out.println("Sound file not found!");
      e.printStackTrace();
    }
    game.start();
  }
  
  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == soundPlay){
      try{
        beep = new AudioStream(new FileInputStream("Sound/Menu.wav"));
      }
      catch (Exception er){
        System.out.println("Sound file not found!");
        er.printStackTrace();
      }
    }
    else{
      AudioPlayer.player.start(beep);
      soundPlay.start();
      if(e.getSource() == newGameButton){
        game.newGame();
      }
      else if(e.getSource() == pauseButton){
        game.pause();
      }
      else if(e.getSource() == unPauseButton){
        game.unPause();
      }
      else if(e.getSource() == scoreBoard){
        new ScoreBoard();
      }
      else if(e.getSource() == optionsButton){
        new Options();
      }
    }   
  }

  public class Options extends JFrame implements ActionListener{
    private JButton musicOn = new JButton("Music On");
    private JButton musicOff = new JButton("Music Off");
    private JButton sfxOn = new JButton("SFX On");
    private JButton sfxOff = new JButton("SFX Off");
    
    
    public Options(){
      setVisible(true);
      setResizable(false);
      setTitle("Options");
      setSize(100,300);
      setLayout(new FlowLayout());
      add(musicOn);
      add(musicOff);
      add(sfxOn);
      add(sfxOff);
      musicOn.addActionListener(this);
      musicOff.addActionListener(this);
      sfxOn.addActionListener(this);
      sfxOff.addActionListener(this);
    }
    public void actionPerformed(ActionEvent e){
      if(e.getSource() == musicOn){
        game.musicStart();
      }
      else if(e.getSource() == musicOff){
        game.musicStop();
      }
      else if(e.getSource() == sfxOn){
        game.getBoard().sfxOn();
      }
      else if(e.getSource() == sfxOff){
        game.getBoard().sfxOff();
      }
    }
  }
}