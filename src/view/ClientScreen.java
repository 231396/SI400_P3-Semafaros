package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.JCircle;
import util.MenuBar;

public class ClientScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	public static void main(String[] args) {		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientScreen frame = new ClientScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ClientScreen() {
		Color background = Color.darkGray;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		pack();
		
		MenuBar.CreateMenuBar(this);
		
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setBackground(background);
		contentPane.setLayout(null);
		
		int circleSize = 150;		
		int offset = 30;
		
		int borderX = getInsets().left + getInsets().right;
		int borderY = getInsets().top + getInsets().bottom;
		
		setSize(circleSize * 3 + offset * 4 + borderX, (int)(circleSize * 1.5f) + borderY);
		
		JCircle circleGreen = new JCircle(Color.green);
		JCircle circleYellow = new JCircle(Color.yellow);
		JCircle circleRed = new JCircle(Color.red);
		
		circleGreen.setBackground(background);
		circleYellow.setBackground(background);
		circleRed.setBackground(background);
		
		int x = 0;
		int y = (getBounds().height-borderY) / 2 - circleSize / 2;
		
		x += offset;
		circleGreen.setBounds(x, y, circleSize);
		x += circleSize + offset;
		circleYellow.setBounds(x, y, circleSize);
		x += circleSize + offset;
		circleRed.setBounds(x, y, circleSize);
		
		contentPane.add(circleGreen);
		contentPane.add(circleYellow);
		contentPane.add(circleRed);
	}
}
