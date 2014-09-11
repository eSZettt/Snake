import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.io.*;

public class ScoreBoard extends JFrame implements ActionListener{
  private String[] scores;
  private int currentScore;
  private JTextField field = new JTextField(15);
  
  public ScoreBoard(){
    this.setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
    setVisible(true);
    setResizable(false);
    setTitle("Scores");
    setSize(100,300);
    setLocationRelativeTo(null);
    if ((new File("Scores")).exists() && !(new File("Scores").isDirectory())) 
      read();
    else{
      scores = new String[10];
      for(int i=0;i<10;i++)
        scores[i] = "0";
      write();
    }
    add(new JLabel("HIGH SCORES"));
    for(int i=9;i>0;i--){
      add(new JLabel(scores[i]));
    }
    validate();
  }
  
  public ScoreBoard(int currentScore){
    this.currentScore = currentScore;
    this.setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
    setVisible(true);
    setResizable(false);
    setTitle("Scores");
    setSize(100,300);
    setLocationRelativeTo(null);
    if ((new File("Scores")).exists() && !(new File("Scores").isDirectory())) 
      read();
    else{
      scores = new String[10];
      for(int i=0;i<10;i++)
        scores[i] = "0";
      write();
    }
    add(new JLabel("HIGH SCORES"));
    for(int i=9;i>0;i--){
      add(new JLabel(scores[i]));
    }
    add(new JLabel("Your Score: "+currentScore));
    add(new JLabel(" Name:"));
    field.setMaximumSize(field.getPreferredSize());
    add(field);
    field.addActionListener(this);
    validate();
  }
  
  public void validate(){
    for(Component c :this.getContentPane().getComponents())
      ((JComponent)c).setAlignmentX(CENTER_ALIGNMENT);
    super.validate();
    repaint();
  }
  public void write(){
    try{
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Scores"));
      out.writeObject(scores);
      out.flush();
      out.close();
    }
    catch(Exception e){
    System.out.println("Save Write Error!");
    e.printStackTrace();
    }
  }
  public void read(){
    try{
      ObjectInputStream in = new ObjectInputStream(new FileInputStream("Scores"));
      scores = (String[]) in.readObject();
      in.close();
    }
    catch(Exception e){
    System.out.println("Save Read Error!");
    e.printStackTrace();
    }
  }
  
  public void actionPerformed(ActionEvent e){
    scores[0] = field.getText()+" : "+currentScore;
    Arrays.sort(scores,new Comparator<String>() {
        public int compare(String str1, String str2) {
            return Integer.valueOf(str1.replaceAll("[^0-9]","")).compareTo(Integer.valueOf(str2.replaceAll("[^0-9]","")));
        }
    });
    write();
    System.out.println("ScoreBoard updated");
    remove(field);
    add(new JLabel("Score Saved."));
    validate();
  }
}