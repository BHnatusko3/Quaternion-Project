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
   for interactive Java programs. That is, this class allows us to write
   interactive Java GUI programs that use our renderer instead of the
   renderer built into Java's GUI library. Of course, our renderer will be
   much slower than the one built into Java (which uses the computer's GPU).
<p>
   The {@link FrameBufferFrame} displays our renderer's {@link FrameBuffer}
   in a {@link FrameBufferPanel} and this class acts as an interface between
   the Java GUI event system and our renderer.
<p>
   This class acts as an "adapter class" for several kinds of event
   listeners, so applications only need to worry about the events that
   are relevant to them.
<p>
   This class is meant to be sub classed by an application that uses our
   renderer. The sub class constructor should initialize a
   {@link renderer.scene.Scene} object with appropriate models and geometry.
   The sub class should also provide implementations of any desired event
   handlers. The event handlers provide interactivity by updating the
   {@link renderer.scene.Scene} object in response to user actions. After
   an event handler updates the {@link renderer.scene.Scene} object, it will
   render the {@link renderer.scene.Scene} into the {@link FrameBuffer}
   object contained in this object's {@link FrameBufferPanel}.
<p>
   Each instance of {@code InteractiveFrame} has a reference to a
   {@link FrameBufferPanel} object, which has a reference to a
   {@link FrameBuffer} object. When a GUI event happens, one of the
   implemented event listeners will update this {@link JFrame} window by
   modifying a {@link renderer.scene.Scene} object appropriately and then
   having our renderer render the {@link renderer.scene.Scene} object into
   the {@link FrameBuffer}. When the renderer is done updating the
   {@link FrameBuffer}, the event listener will call the
   {@link FrameBufferPanel}'s update() method, which will pass the
   {@link FrameBuffer}'s pixel data to the {@link java.awt.Image} being drawn
   on the {@link java.awt.Graphics} context of the {@link FrameBufferPanel}
   (which is a {@link javax.swing.JPanel}). Then the event listener will call this
   object's repaint() method which will trigger the {@link FrameBufferPanel}'s
   paintComponent() method. This will display the {@link java.awt.Image} (that
   holds the {@link FrameBuffer}'s contents) in the {@link javax.swing.JPanel}
   within this {@link JFrame}'s window.
*/
@SuppressWarnings("serial")
public class InteractiveFrame extends FrameBufferFrame implements
  ActionListener, ItemListener,  AdjustmentListener,  TextListener,
  KeyListener,    MouseListener, MouseMotionListener, ComponentListener,
  WindowListener, FocusListener
{
   /**
      Create a {@link FrameBufferFrame} window holding a
      {@link FrameBufferPanel} (which contains a {@link FrameBuffer}).
      <p>
      Make thiis {@code InteractiveFrame} object the default event
      handler for several common types of events.

      @param title     title for the {@link FrameBufferFrame} window
      @param fbWidth   width for the initial {@link FrameBuffer} used by the {@link FrameBufferFrame}
      @param fbHeight  height for the initial {@link FrameBuffer} used by the {@link FrameBufferFrame}
   */
   public InteractiveFrame(String title, int fbWidth, int fbHeight)
   {
      super(title, fbWidth, fbHeight);

      // Make this object the event handler for the following
      // events, which are events created by this window itself.
      this.addKeyListener(this);
      this.addMouseListener(this);
      this.addMouseMotionListener(this);
      this.addComponentListener(this);
      this.addWindowListener(this);
      this.addFocusListener(this);
   }


   // Implement the ActionListener interface.
   @Override public void actionPerformed(ActionEvent e){}
   // Implement the ItemListener interface.
   @Override public void itemStateChanged(ItemEvent e){}
   // Implement the AdjustmentListener interface.
   @Override public void adjustmentValueChanged(AdjustmentEvent e){}
   // Implement the TextListener interface.
   @Override public void textValueChanged(TextEvent e){}
   // Implement the KeyListener interface.
   @Override public void keyPressed(KeyEvent e){}
   @Override public void keyReleased(KeyEvent e){}
   @Override public void keyTyped(KeyEvent e){}
   // Implement the MouseListener interface.
   @Override public void mouseClicked(MouseEvent e){}
   @Override public void mousePressed(MouseEvent e){}
   @Override public void mouseReleased(MouseEvent e){}
   @Override public void mouseEntered(MouseEvent e){}
   @Override public void mouseExited(MouseEvent e){}
   // Implement the MouseMotionListener interface.
   @Override public void mouseDragged(MouseEvent e){}
   @Override public void mouseMoved(MouseEvent e){}
   // Implement the ComponentListener interface.
   @Override public void componentMoved(ComponentEvent e){}
   @Override public void componentHidden(ComponentEvent e){}
   @Override public void componentShown(ComponentEvent e){}
   @Override public void componentResized(ComponentEvent e){}
   // Implement the WindowListener interface.
   @Override public void windowOpened(WindowEvent e){}
   @Override public void windowClosing(WindowEvent e){}
   @Override public void windowClosed(WindowEvent e){}
   @Override public void windowActivated(WindowEvent e){}
   @Override public void windowDeactivated(WindowEvent e){}
   @Override public void windowIconified(WindowEvent e){}
   @Override public void windowDeiconified(WindowEvent e){}
   // Implement the FocusListener interface.
   @Override public void focusGained(FocusEvent e){}
   @Override public void focusLost(FocusEvent e){}
}
