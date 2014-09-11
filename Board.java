import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import sun.audio.*;
import java.io.*;


enum Direction{
  up,down,left,right
}

public class Board extends JPanel implements ActionListener, KeyListener{
    private ArrayList<Body> snake = new ArrayList<Body>();
    private Direction move;
    private Random gen = new Random();
    private Food food = new Food(gen.nextInt(50),gen.nextInt(25));
    private boolean gameOver, pause, title, sfx = true;
    private Timer timer = new Timer(75, this), soundPlay = new Timer(100, this);
    private int score;
    private BufferedImage image;
    private AudioStream foodSound, lose;
    
    public Board(boolean title){
      this.title = title;
      setFocusable(true);
      setVisible(true);
      setSize(1000,500);
      try{
        image = ImageIO.read(new File("./Snake Title.png"));
      }
      catch (Exception e){
        System.out.print("Title Image Missing!");
      }
    }
    
    public Board(){
      super(true);
      setFocusable(true);
      setVisible(true);
      setSize(1000,500);
      timer.start();
      snake.add(new Body(10,10,true));
      snake.add(new Body(9,10));
      move = Direction.right;
      revalidate();
      try{
        foodSound = new AudioStream(new FileInputStream("Sound/Food.wav"));
        lose = new AudioStream(new FileInputStream("Sound/Lose.wav"));
      }
      catch (Exception e){
        System.out.println("Sound file not found!");
        e.printStackTrace();
      }
    }
    public void sfxOn(){
      sfx = true;
    }
    public void sfxOff(){
      sfx = false;
    }
    
    public void actionPerformed(ActionEvent e) {
      if(SwingUtilities.getWindowAncestor(this).isFocused()){
        if(e.getSource() == soundPlay){
          try{
          foodSound = new AudioStream(new FileInputStream("Sound/Food.wav"));
          }
          catch (Exception ex){
            System.out.println("Sound file not found!");
            ex.printStackTrace();
          }
          soundPlay.stop();
        }
        else{
        checkCollisions();
        repaint();
        move();
        }
      }
    }
    public void pause(){
      timer.stop();
      pause = true;
      repaint();
    }
    public void unPause(){
      if(gameOver == false){
        timer.start();
        pause = false;
        repaint();
      }
    }
    public int getScore(){
      return score;
    }
    public void checkCollisions(){
      for (Body piece: snake){
        if (piece.getX() == food.getX() && piece.getY() == food.getY()){
          snake.add(new Body(snake.get(snake.size()-1).getX(),snake.get(snake.size()-1).getY()));
          score+=10;
          food.setX(gen.nextInt(50));
          food.setY(gen.nextInt(25));
          if (sfx){
            AudioPlayer.player.start(foodSound);
            soundPlay.start();
          }
          break;
        }
      }
      for (Body piece: snake){
        if ((piece != snake.get(0) && piece.getX() == snake.get(0).getX() && piece.getY() == snake.get(0).getY()) ||
            (snake.get(0).getX()>50) || (snake.get(0).getY()>25) || (snake.get(0).getX()<0) || (snake.get(0).getY()<0)){
          timer.stop();
          AudioPlayer.player.start(lose);
          gameOver = true;
          repaint();
          new ScoreBoard(score);
          break;
        }
      }
    }
    public void paintComponent(Graphics g){
      super.paintComponent(g);
      if(title == true){
        g.drawImage(image, 0, 0, null);
      }
      else{
        g.setColor(Color.GRAY);
        g.fillRect(0,0,this.getWidth(),this.getHeight());
        for(Body piece:snake){
          if (piece.getHead())
            g.setColor(Color.GREEN);
          else
            g.setColor(Color.BLACK);
          g.fillRect(piece.getX()*(this.getWidth()-25)/50,piece.getY()*(this.getHeight()-47)/25,(this.getWidth()-25)/50,(this.getHeight()-47)/25);
        }
        g.setColor(Color.RED);
        g.fillRect(food.getX()*(this.getWidth()-25)/50,food.getY()*(this.getHeight()-47)/25,(this.getWidth()-25)/50,(this.getHeight()-47)/25);
        g.setColor(Color.BLUE);
        Font scoreFont = new Font("Score",Font.BOLD,15);
        g.setFont(scoreFont);
        g.drawString("Score: "+score,10,20);
        if(gameOver || pause){
          Color color = new Color(0, 0, 0, 0.75f);
          g.setColor(color);
          g.fillRect(0,0,this.getWidth(),this.getHeight());
          Font over = new Font("End",Font.BOLD,40);
          g.setFont(over);
          if (gameOver){
            g.setColor(Color.RED);
            g.drawString("Game Over.",this.getWidth()/2-g.getFontMetrics(over).stringWidth("Game Over.")/2,this.getHeight()/2-g.getFontMetrics(over).getHeight()/2);
          }
          else if(pause){
            g.setColor(Color.WHITE);
            g.drawString("Paused.",this.getWidth()/2-g.getFontMetrics(over).stringWidth("Paused.")/2,this.getHeight()/2-g.getFontMetrics(over).getHeight()/2);
          }
        }
      }
    }
    
    public void move(){
      for(int i=snake.size()-1;i>0;i--){
        if (snake.get(i).getNew() == false){
          snake.get(i).setY(snake.get(i-1).getY());
          snake.get(i).setX(snake.get(i-1).getX());
        }
        try{
          if (snake.get(i+1).getNew() == false)
            snake.get(i).setNew(false);
        }
        catch (Exception e){
          snake.get(i).setNew(false);
        }
      }
      switch (move){
        case up:
          snake.get(0).setY(snake.get(0).getY()-1);
        break;
        case down:
          snake.get(0).setY(snake.get(0).getY()+1);
        break;
        case left:
          snake.get(0).setX(snake.get(0).getX()-1);
        break;
        case right:
          snake.get(0).setX(snake.get(0).getX()+1);
        break;
      }
    }
    public void keyPressed(KeyEvent e) {
      switch (e.getKeyCode()){
        case KeyEvent.VK_UP:
          if (move != Direction.down){
          move = Direction.up;
        }
          break;
        case KeyEvent.VK_DOWN:
          if (move != Direction.up){
          move = Direction.down;
        }
          break;
        case KeyEvent.VK_LEFT:
          if (move != Direction.right){
          move = Direction.left;
        }
          break;
        case KeyEvent.VK_RIGHT:
          if (move != Direction.left){
          move = Direction.right;
        }
          break;
      }
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
  }