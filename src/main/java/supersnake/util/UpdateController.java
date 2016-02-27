
package supersnake.util;

public class UpdateController{
  private long lastTimeUpdated;
  private long timeElapsedSinceUpdate;
  private long updateDelay;

  public UpdateController(){
    updateDelay = Time.SECOND;
  }

  public UpdateController(long updateDelay){
    this.updateDelay = updateDelay;
  }

  public void setUpdateDelay(long updateDelay){
    this.updateDelay = updateDelay;
  }

  public boolean shouldUpdate(long timeElapsed){
    timeElapsedSinceUpdate += timeElapsed;
    if(timeElapsedSinceUpdate > updateDelay){
      timeElapsedSinceUpdate -= updateDelay;
      lastTimeUpdated += updateDelay;
      return true;
    }
    return false;
  }
}
