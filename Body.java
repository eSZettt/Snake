public class Body{
  private int x, y;
  private boolean head = false, spawned = true;
  
  public Body(int x, int y){
    this.x = x;
    this.y = y;
  }
  public Body(int x, int y, boolean head){
    this.x = x;
    this.y = y;
    this.head = head;
  }
  
  public int getX(){
    return x;
  }
  public int getY(){
    return y;
  }
  public boolean getHead(){
    return head;
  }
  public boolean getNew(){
    return spawned;
  }
  public void setNew(boolean spawned){
    this.spawned = spawned;
  }
  public void setY(int y){
    this.y = y;
  }
  public void setX(int x){
    this.x = x;
  }

}
    