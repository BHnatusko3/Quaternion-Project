/*

*/

package renderer.gui;
import  renderer.framebuffer.FrameBuffer;

import java.awt.event.AdjustmentEvent;
import java.awt.Scrollbar;
import java.awt.BorderLayout;

/**
   This class adds a horizontal {@link Scrollbar} to the bottom edge
   of an {@link InteractiveFrame} window.
*/
@SuppressWarnings("serial")
public class InteractiveFrame_H extends InteractiveFrame
{
   public Scrollbar sb;

   /**
      Add a horizontal {@link Scrollbar} to the bottom edge of an
      {@link InteractiveFrame} window. Use the {@link Scrollbar}'s
      {@link AdjustmentEvent}s to update a {@link renderer.scene.Scene}.

      @param title     title for the {@link javax.swing.JFrame} window
      @param fbWidth   width for the initial {@link FrameBuffer} used by this {@link javax.swing.JFrame}
      @param fbHeight  height for the initial {@link FrameBuffer} used by this {@link javax.swing.JFrame}
   */
   public InteractiveFrame_H(String title, int fbWidth, int fbHeight)
   {
      super(title, fbWidth, fbHeight);

      // Create a Scrollbar for this JFrame.
      sb = new Scrollbar(Scrollbar.HORIZONTAL, 50, 2, 0, 100);
      // Place the Scrollbar in this JFrame.
      this.getContentPane().add(sb,  BorderLayout.SOUTH );
      this.pack();
      this.setVisible(true);

      // Make this object the event listener for the scrollbar's events.
      sb.addAdjustmentListener(this);
   }


   /**
      Implement the {@link java.awt.event.AdjustmentListener} interface for the {@link Scrollbar}.
   <p>
      Override this method to specify how an application updates its
      {@link renderer.scene.Scene} object in response to the firing
      of an {@link AdjustmentEvent} from the {@link Scrollbar}.

      @param e  {@link AdjustmentEvent} from this application's {@link Scrollbar}
   */
   @Override public void adjustmentValueChanged(AdjustmentEvent e)
   {
      System.out.println(e);
   }
}
