
package supersnake.util;

public class UpdateController{
  private double lastTimeUpdated;
  private double timeElapsedSinceUpdate;
  private double updateDelay;

  public UpdateController(){
    updateDelay = Time.SECOND;
  }

  public UpdateController(double updateDelay){
    this.updateDelay = updateDelay;
  }

  public void setUpdateDelay(double updateDelay){
    this.updateDelay = updateDelay;
  }

  public boolean shouldUpdate(double timeElapsed){
    timeElapsedSinceUpdate += timeElapsed;
    if(timeElapsedSinceUpdate > updateDelay){
      timeElapsedSinceUpdate -= updateDelay;
      lastTimeUpdated += updateDelay;
      return true;
    }
    return false;
  }
}
