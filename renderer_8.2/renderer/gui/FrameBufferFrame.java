/*

*/

package renderer.gui;
import  renderer.framebuffer.FrameBuffer;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.JFrame;
import java.awt.BorderLayout;

/**
   This class allows our rendering code to be used as the primary renderer
   for Java programs. That is, this class allows us to write Java programs
   that use our renderer instead of the renderer built into Java's GUI
   library. Of course, our renderer will be much slower than the one built
   into Java (which uses the computer's GPU).
<p>
   A {@code FrameBufferFrame} "is a" {@link JFrame} (a top level window). A
   {@code FrameBufferFrame} "has a" {@link FrameBufferPanel}. A
   {@link FrameBufferPanel} "has a" {@link FrameBuffer}. And a
   {@link FrameBuffer} "has a" array of pixel data. The pixel data in a
   {@link FrameBuffer} is put there by calling our rendering algorithms.
<p>
   A {@code FrameBufferFrame} should be instantiated by an application that
   uses our renderer. The application should initialize a {@link renderer.scene.Scene}
   object with appropriate models and geometry. The application should then
   render the {@link renderer.scene.Scene} into the {@link FrameBuffer} object
   contained in the {@code FrameBufferFrame}'s {@link FrameBufferPanel}.
<p>
   This {@code FrameBufferFrame} window may be resizeable. When this window
   resizes, its {@link FrameBufferPanel} will also resize, and so the
   {@link FrameBufferPanel}'s {@link FrameBuffer) object will also need to
   resize. But {@link FrameBuffer} objects cannot be resized. So each time
   this window resizes, a new {@link FrameBuffer} object needs to be created
   for this window's {@link FrameBufferPanel}. The componentResized() method
   from the {@link java.awt.event.ComponentListener} interface should call
   the setFrameBuffer() method in this object's {@link FrameBufferPanel}
   and pass it a reference to a new {@link FrameBuffer} object with this
   resized window's new dimensions.
*/
@SuppressWarnings("serial")
public class FrameBufferFrame extends JFrame
{
   public FrameBufferPanel fbp;

   /**
      Create a {@link JFrame} window with a {@link BorderLayout} and place a
      {@link FrameBufferPanel} (containing a {@link FrameBuffer}) in the center
      of the layout.

      @param title     title for the {@link JFrame} window
      @param fbWidth   width for the initial {@link FrameBuffer} used by this {@link JFrame}
      @param fbHeight  height for the initial {@link FrameBuffer} used by this {@link JFrame}
   */
   public FrameBufferFrame(String title, int fbWidth, int fbHeight)
   {
      super(title);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setLocationByPlatform(true);

      // Create the initial FrameBuffer for the FrameBufferPanel.
      FrameBuffer fb = new FrameBuffer(fbWidth, fbHeight);

      // Create a FrameBufferPanel (a JPanel) for this FrameBufferFrame.
      fbp = new FrameBufferPanel(fb);

      // Place the FrameBufferPanel in this FrameBufferFrame.
      this.getContentPane().add(fbp, BorderLayout.CENTER);
      this.pack();
      this.setVisible(true);
   }


   /**
      Accessor method for this {@link FrameBufferFrame}'s {@link FrameBufferPanel}.

      @return a reference to the {@link FrameBufferPanel} owned by this {@link FrameBufferFrame}
   */
   public FrameBufferPanel getFrameBufferPanel()
   {
      return fbp;
   }


   /**
      Returns the current width of this {@link FrameBufferFrame}'s {@link FrameBufferPanel}.

      @return the current width of this {@link FrameBufferFrame}'s {@link FrameBufferPanel}
   */
   public int getFrameBufferPanelWidth()
   {
      return fbp.getWidth();
   }


   /**
      Returns the current height of this {@link FrameBufferFrame}'s {@link FrameBufferPanel}.

      @return the current height of this {@link FrameBufferFrame}'s {@link FrameBufferPanel}
   */
   public int getFrameBufferPanelHeight()
   {
      return fbp.getHeight();
   }


   /**
      This method should be called when the {@link FrameBuffer} object
      in this {@code FrameBufferFrame}'s {@link FrameBufferPanel} has
      been updated by the renderer.
   */
   public void update()
   {
      fbp.update();
   }
}
