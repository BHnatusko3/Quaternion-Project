/*

*/

package renderer.gui;
import  renderer.framebuffer.FrameBuffer;

import java.awt.event.*;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.Timer;

/**
   This class allows our renderer to implement an animation.
   This class is an {@link InteractiveFrame} with an added
   {@link Timer} that controls the frames-per-second rate
   of the animation.
*/
@SuppressWarnings("serial")
public class AnimationFrame extends InteractiveFrame
{
   private Timer timer;
   private int fps;

   /**
      Associate a {@link Timer} object to an {@link InteractiveFrame}
      window. Use the {@link Timer}'s {@link ActionEvent}s to drive
      an animation.

      @param title     title for the {@link JFrame} window
      @param fbWidth   width for the initial {@link FrameBuffer} used by this {@link JFrame}
      @param fbHeight  height for the initial {@link FrameBuffer} used by this {@link JFrame}
      @param fps       the frames-per-second rate for this animation
   */
   public AnimationFrame(String title, int fbWidth, int fbHeight, int fps)
   {
      super(title, fbWidth, fbHeight);

      this.fps = fps;
      timer = new Timer(1000/fps, this);
      timer.start();
   }


   /**
      Use this method to get the current frames-per-second rate

      @return the frames-per-second rate for this animation
   */
   public int getFPS()
   {
      return fps;
   }


   /**
      Use this method to speed up or slow down the animation.

      @param fps  the frames-per-second rate for this animation
   */
   public void setFPS(int fps)
   {
      this.fps = fps;
      timer.stop();
      if (fps > 0)
      {
         timer = new Timer(1000/fps, this);
         timer.start();
      }
   }


   /**
      Implement the {@link ActionListener} interface for the {@link Timer}.
   <p>
      Override this method to specify how an animation updates its
      {@link renderer.scene.Scene} object in response to the firing
      of an {@link ActionEvent} from the {@link Timer}.

      @param e  {@link ActionEvent} from this animation's {@link Timer} object
   */
   @Override public void actionPerformed(ActionEvent e)
   {
      System.out.println(e);
      this.repaint();
   }
}
