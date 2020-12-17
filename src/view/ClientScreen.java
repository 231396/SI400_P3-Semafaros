package view;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.JCircle;
import util.MenuBar;
import util.TrafficLightStates;

public class ClientScreen extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private TrafficLightStates currentState;

	private JCircle[] circles;

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

		setSize(circleSize * 3 + offset * 4 + borderX, (int) (circleSize * 1.5f) + borderY);

		circles = new JCircle[] { new JCircle(Color.green), new JCircle(Color.yellow), new JCircle(Color.red) };

		int x = 0;
		int y = (getBounds().height - borderY) / 2 - circleSize / 2;

		x += offset;
		circles[0].setBounds(x, y, circleSize);
		x += circleSize + offset;
		circles[1].setBounds(x, y, circleSize);
		x += circleSize + offset;
		circles[2].setBounds(x, y, circleSize);

		for (JCircle c : circles) {
			c.setBackground(background);
			contentPane.add(c);
		}

	}

	public void startTrafficLight() {
		currentState = TrafficLightStates.GREEN;
		getJCircle(currentState).changeState(true);
	}

	public void changeState(TrafficLightStates state) {
		getJCircle(currentState).changeState(false);
		getJCircle(state).changeState(true);
		currentState = state;
	}

	private JCircle getJCircle(TrafficLightStates state) {
		return circles[state.ordinal()];
	}

}
