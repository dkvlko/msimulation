import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

class MyPanel extends JPanel {
	StringBuilder equation = null;
	int prevX = 0;
	int prevY = 0;

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.drawLine(300, 0, 300, 480);
		g.drawLine(0, 240, 600, 240);

		if (equation != null) {
			g.drawString(equation.toString(), 100, 100);
			// given the equation ... pass the value of x from -300 to 300
			// calculate y
			/*
			 * int y=0; char equ[]; //equation = equation +"+0"; for(int
			 * i=-10;i<=10;i++){ equ = equation.toCharArray();
			 * y=getYValue(equ,i); if(i==-300){ prevX=-300; prevY=y; }else{
			 * System.out.println("X,Y "+i +" ,"+y); g.drawLine(prevX+300,
			 * 240-prevY, i+300,240-y); prevX=i; prevY=y; }
			 * 
			 * }
			 */

			EquationParserEngine epn = new EquationParserEngine();
			float yValue = 0;
			equation = new StringBuilder(equation.toString().replaceAll("sin", "s"));
			equation = new StringBuilder(equation.toString().replaceAll("cos", "c"));
			equation = new StringBuilder(equation.toString().replaceAll("tan", "t"));
			equation = new StringBuilder(equation.toString().replaceAll("log", "l"));
			equation = new StringBuilder(equation.toString().replaceAll("exp", "e"));
			for (int x = -300; x <= 300; x++) {
				// System.out.println("X Value " + x);
				StringBuilder processEqu = new StringBuilder(equation);
				yValue = epn.getYValue(processEqu, x);
				// System.out.println("Y value " + yValue);
				if (x == -300) {
					prevX = -300;
					prevY = (int) yValue;
				} else {
					g.drawLine(prevX + 300, 240 - prevY, x + 300, 240 - (int) yValue);
					prevX = x;
					prevY = (int) yValue;
				}
				// System.out.println("Y Value " + yValue);
			}
		}

	}
}

class MyFrame extends JFrame {
	public MyFrame() {
		super();
		init();
	}

	public MyFrame(String title) {
		super(title);
		init();
	}

	private void init() {
		this.setTitle("MathsSimulation");
		// this.setContentPane(new );
		// check whether size of jframe needs to be set
		this.setSize(600, 480);
		this.setLocation(100, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		// create another JPanel Class and add it to the MyFrame
		MyPanel jPanel = new MyPanel();
		jPanel.setBackground(Color.WHITE);
		this.add(jPanel, BorderLayout.CENTER);

		JPanel jpnl2 = new JPanel();
		JButton button = new JButton("Draw");
		JTextField textField = new JTextField(20);
		JLabel label = new JLabel("y=");
		this.add(jpnl2, BorderLayout.PAGE_END);
		jpnl2.add(label);
		jpnl2.add(textField);
		JFrame frame = this;

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
				 * JDialog d = new JDialog(frame,textField.getText(),true);
				 * d.setLocationRelativeTo(frame); d.setVisible(true);
				 */
				jPanel.equation = new StringBuilder(textField.getText());
				jPanel.repaint();
			}
		}

		);

		jpnl2.add(button);

		/*
		 * An ActionListener is used to handle the logical click of a button. A
		 * click happens when the mouse is pressed then released on a button, or
		 * when the keyboard shortcut of that button is used, or when the button
		 * has the focus and the space bar is pressed, or when the button is the
		 * default button and Enter is pressed, or when the button's click()
		 * method is called programmatically A MouseListener only handles
		 * low-level mouse events.
		 */
		// set the layout and add label with input text field and a button

	}
}

public class MathsSimulation {
	private static void constructGUI() {
		// System.out.println("Hello MathsSimulation");
		JFrame.setDefaultLookAndFeelDecorated(true);
		MyFrame frame = new MyFrame();
		frame.setPreferredSize(new Dimension(600, 480));
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String args[]) {
		// System.out.println("Hello MathsSimulation");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				constructGUI();
			}
		}

		);
	}
}
