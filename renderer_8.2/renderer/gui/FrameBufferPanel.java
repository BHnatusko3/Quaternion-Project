/*

*/

package renderer.gui;
import  renderer.framebuffer.FrameBuffer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import javax.swing.JPanel;

/**
   This class is an interface between our renderer and the Java GUI system.
<p>
   This class is meant to be instantiated as a sub-panel of a
   {@link javax.swing.JFrame}. The {@link javax.swing.JFrame} may or may
   not implement event listeners. If the {@link javax.swing.JFrame} does
   implement event listeners, then the event listeners can make our
   renderer interactive.
<p>
   Each instance of {@code FrameBufferPanel} has a reference to a
   {@link FrameBuffer} object and the {@link FrameBuffer} object
   determines the (preferred) dimensions of this {@link JPanel}.
<p>
   When a GUI event happens, any implemented event listener would update
   this {@link JPanel} by modifying a {@link renderer.scene.Scene} object
   appropriately and then having our renderer render the
   {@link renderer.scene.Scene} object into the {@link FrameBuffer}.
   When the renderer is done updating the {@link FrameBuffer}, the
   event listener will call this object's {@link #update} method, which will
   pass the {@link FrameBuffer}'s pixel data to the {@link java.awt.Image}
   being drawn on the {@link java.awt.Graphics} context of the
   {@link FrameBufferPanel} (which is a {@link JPanel}). Then the event
   listener will call the {@link javax.swing.JFrame}'s repaint() method
   which will trigger this object's {@link #paintComponent} method. This
   will display the {@link java.awt.Image} (that holds the
   {@link FrameBuffer}'s contents) in the {@link JPanel} within this
   {@link javax.swing.JFrame}'s window.
<p>
   This panel may be resizeable. When this panel resizes, its
   {@link FrameBuffer} object will also need to resize. But
   {@link FrameBuffer} objects cannot be resized. So each time this
   panel resizes, the resize event handler should instantiate a new
   {@link FrameBuffer} object with the appropriate dimensions and
   then call this object's setFrameBuffer() method with a reference
   to the new {@link FrameBuffer} object.
*/
@SuppressWarnings("serial")
public class FrameBufferPanel extends JPanel
{
   private FrameBuffer fb;
   private MemoryImageSource source;
   private Image img;

   /**
      @param fb  {@link FrameBuffer} used by this {@link JPanel}
   */
   public FrameBufferPanel(FrameBuffer fb)
   {
      setFrameBuffer(fb);
   }


   @Override
   public Dimension getPreferredSize()
   {
      return new Dimension(fb.width, fb.height);
   }


   @Override
   protected void paintComponent(Graphics g)
   {
      super.paintComponent(g);

      Graphics2D g2 = (Graphics2D)g.create();
      g2.drawImage(img, 0, 0, this);
      g2.dispose();
   }


   /**
      This method should be called when this {@code FrameBufferPanel}'s
      {@link FrameBuffer} object has been updated by the renderer.
   */
   public void update()
   {
      // Send the new pixel data to the ImageConsumer.
      this.source.newPixels();
   }


   /**
      Accessor method for the {@link FrameBuffer} currently being used as
      the source for the {@link java.awt.Image} painted on this {@link JPanel}.

      @return a reference to the {@link FrameBuffer} owned by this {@link JPanel}
   */
   public FrameBuffer getFrameBuffer()
   {
      return fb;
   }


   /**
      Returns the current width of this {@link FrameBufferPanel}'s {@link FrameBuffer}.

      @return the current width of this {@link FrameBufferPanel}'s {@link FrameBuffer}
   */
   public int getFrameBufferWidth()
   {
      return fb.width;
   }


   /**
      Returns the current height of this {@link FrameBufferPanel}'s {@link FrameBuffer}.

      @return the current height of this {@link FrameBufferPanel}'s {@link FrameBuffer}
   */
   public int getFrameBufferHeight()
   {
      return fb.height;
   }


   /**
      Change the {@link FrameBuffer} being used as the source for
      the {@link java.awt.Image} painted on this {@link JPanel}.
   <p>
      This will usually be in response to a call to the
      componentResized() event handler.

      @param fb  new {@link FrameBuffer} object for this {@link JPanel}
   */
   public void setFrameBuffer(FrameBuffer fb)
   {
      this.fb = fb;

      // Use the framebuffer as the source for an Image.
      this.source = new MemoryImageSource(fb.width, fb.height,
                                          fb.pixel_buffer,
                                          0, fb.width);
      this.source.setAnimated(true);
      this.img = createImage(this.source);
   }
}
