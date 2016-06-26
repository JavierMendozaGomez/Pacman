//////////////////////////////////////////// 
// Project skeleton for Computer 2D Graphics
// MVC-based design
// Author: Chus Martín
// 2014
//////////////////////////////////////////// 

package view;

//JOGL imports
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

//Swing imports
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



//AWT imports
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



//Specific imports
import controller.Controller;

public class SwingGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	private GLCanvas canvas; //AWT-based canvas!!!!
	private SwingGUI(){

		// Set the window
		super("JOGL-project for 2D Graphics");
		this.getContentPane().setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//log_JFrame();
		
        
		// Build and add the canvas
		GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        log_OpenGL(caps);
        
        canvas = new GLCanvas(caps);
        int canvasWidth= 500;
		int canvasHeight= 250;		
		canvas.setSize(canvasWidth, canvasHeight);
        this.getContentPane().add(canvas,BorderLayout.CENTER);
        log_Canvas();

        
       // Add some buttons
        JPanel btPane= new JPanel();
        this.getContentPane().add(btPane,BorderLayout.SOUTH);
        
        JButton buttonCenter = new JButton("Centro (M)");
        btPane.add(buttonCenter);
        
        JButton buttonSeleccionar = new JButton("Seleccionar (S)");
        btPane.add(buttonSeleccionar);
        
        JButton buttonSegmentoInicial = new JButton("Segmento1");
        btPane.add(buttonSegmentoInicial);
        
        JButton buttonSegmentoFinal = new JButton("Segmento2");
        btPane.add(buttonSegmentoFinal);
        
        JButton buttonInterseccion = new JButton("Interseccion");
        btPane.add(buttonInterseccion);
        
        JButton buttonAnimar = new JButton("Animar");
        btPane.add(buttonAnimar);
        
        JButton buttonRecta = new JButton("Rectas");
        btPane.add(buttonRecta);
     
      
       
        
        // Dimension the JFrame to cover the canvas!!
        
        this.pack();
        //log_Canvas();
        log_JFrame();

        
        // Build the controller, and register it as a listener for multiple events        
        controller= new Controller(canvas);
        // Canvas listener
        canvas.addGLEventListener(controller);
        canvas.addMouseListener(controller);
        canvas.addKeyListener(controller);
        
        // Buttons listener
        
        buttonCenter.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		controller.centrar();
        	}
        });
        
        buttonSeleccionar.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		controller.seleccionar();
        	}
        });
        
        buttonSegmentoInicial.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		controller.segmentoInicial();
        	}
        });
        
        buttonSegmentoFinal.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		controller.segmentoFinal();
        	}
        });
        
        buttonInterseccion.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.interseccion();
			}
		});
        
 
        buttonAnimar.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		controller.animar();
        	}
        });
        
        buttonRecta.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.recta();
			}
		});
    
        
        // Only the canvas is focusable!
        buttonCenter.setFocusable(false);
        buttonSeleccionar.setFocusable(false);
        buttonSegmentoInicial.setFocusable(false);
        buttonSegmentoFinal.setFocusable(false);
        buttonInterseccion.setFocusable(false);
        buttonAnimar.setFocusable(false);
	}
	
	
	private void log_OpenGL(GLCapabilities caps){
		//OpenGL settings
		System.out.print("OpenGL settings\n");
		System.out.print("Double Buffered: "+ caps.getDoubleBuffered() + "\n");
		System.out.print("Hardware Accelerated: "+ caps.getHardwareAccelerated() + "\n");
		System.out.print("Color depth (RGB bits): "+ caps.getRedBits() + "\t" + caps.getGreenBits() + "\t"+ caps.getBlueBits() + "\n");
		System.out.print("Depth bits: "+ caps.getDepthBits() + "\n");
	    System.out.print("\n");
	}
	
	private void log_Canvas(){
		// Canvas
	    System.out.print("Canvas properties:\n");
	    System.out.print("GL-profile:\t" + canvas.getGLProfile().getName()+ "\n");
		System.out.print("Automatic buffer swapping: "+ canvas.getAutoSwapBufferMode()+ "\n");
	    System.out.print("GL-orientated:\t" + canvas.isGLOriented() + "\n");
	    System.out.print("Canvas size:\t" + canvas.getWidth()+ " x " +  canvas.getHeight() +"\n");
	    System.out.print("\n");
	}
	
	private void log_JFrame(){
	    // Window
	    System.out.print("Window's size:\n");
	    System.out.print("Size:\t" + this.getWidth() + " x " +  this.getHeight() +"\n");
	    System.out.print("\n");
	}

	
	public static void main(String[] args) {
		
		final SwingGUI app = new SwingGUI();
		
		EventQueue.invokeLater(new Runnable(){
			public void run() {
		        app.setVisible(true);		        
		        // The canvas requests the focus in order to make the keyListener available from the beginning 
		        // The focus must be requested after setting the canvas visible!
		        app.canvas.requestFocusInWindow();  			
			}			
		});
		
	}

}
