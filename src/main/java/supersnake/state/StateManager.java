
package supersnake.state;

import java.util.ArrayDeque;
import java.util.Deque;

public final class StateManager{
  private static final Deque<AbstractState> stack = new ArrayDeque<>();

  private StateManager(){}

  public static boolean isEmpty(){
    return stack.isEmpty();
  }

  public static void push(AbstractState s){
    if(!isEmpty()){
      peek().pause();
    }
    stack.addFirst(s);
    s.load();
  }

  public static AbstractState peek(){
    return stack.getFirst();
  }

  public static AbstractState pop(){
    AbstractState s = stack.removeFirst();
    s.exit();
    if(!isEmpty()){
      peek().resume();
    }
    return s;
  }

  public static void update(long timeElapsed){
    peek().update(timeElapsed);
  }

  public static void render(){
    peek().render();
  }
}
