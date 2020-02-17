/*

*/

package renderer.gui;
import  renderer.framebuffer.FrameBuffer;

import java.awt.event.AdjustmentEvent;
import java.awt.Scrollbar;
import java.awt.BorderLayout;

/**
   This class adds vertical and horizontal {@link Scrollbar}s to
   the right and bottom edges of an {@link InteractiveFrame} window.
*/
@SuppressWarnings("serial")
public class InteractiveFrame_HV extends InteractiveFrame
{
   public Scrollbar sbV;
   public Scrollbar sbH;

   /**
      Add vertical and horizontal {@link Scrollbar}s to the right
      and bottom edges of an {@link InteractiveFrame} window. Use
      the {@link Scrollbar}'s {@link AdjustmentEvent}s to update
      a {@link renderer.scene.Scene}

      @param title     title for the {@link javax.swing.JFrame} window
      @param fbWidth   width for the initial {@link FrameBuffer} used by this {@link javax.swing.JFrame}
      @param fbHeight  height for the initial {@link FrameBuffer} used by this {@link javax.swing.JFrame}
   */
   public InteractiveFrame_HV(String title, int fbWidth, int fbHeight)
   {
      super(title, fbWidth, fbHeight);

      // Create Scrollbars for this JFrame.
      sbV = new Scrollbar(Scrollbar.VERTICAL,   50, 2, 0, 100);
      sbH = new Scrollbar(Scrollbar.HORIZONTAL, 50, 2, 0, 100);
      // Place the Scrollbars in this JFrame.
      this.getContentPane().add(sbV, BorderLayout.EAST );
      this.getContentPane().add(sbH, BorderLayout.SOUTH );
      this.pack();
      this.setVisible(true);

      // Make this object the event listener for the scrollbar events.
      sbV.addAdjustmentListener(this);
      sbH.addAdjustmentListener(this);
   }


   /**
      Implement the {@link java.awt.event.AdjustmentListener} interface for the {@link Scrollbar}s.
   <p>
      Override this method to specify how an application updates its
      {@link renderer.scene.Scene} object in response to the firing
      of an {@link AdjustmentEvent} from one of the {@link Scrollbar}s.

      @param e  {@link AdjustmentEvent} from either of this application's {@link Scrollbar}s
   */
   @Override public void adjustmentValueChanged(AdjustmentEvent e)
   {
      System.out.println(e);
   }
}
