package view;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.MenuBar;
import util.TrafficLight;
import util.TrafficLightStates;

import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class ServerScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;

	StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerScreen frame = new ServerScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	ArrayList<JClient> clients = new ArrayList<>();
	
	JTextArea textArea;	
	JScrollPane scrollPane;
	
	public ServerScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 563, 500);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		MenuBar.CreateMenuBar(this);		
		
		JTextArea textArea = new JTextArea("Server");
		textArea.setLineWrap(true);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(337,11,200,439);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		contentPane.add(scrollPane);		
		
		JClient c = newJClient(new TrafficLight(null, 7777), TrafficLightStates.GREEN);
		c.setLocation(10, 11);
		c.setSize(317, 53);
		contentPane.add(c);	
	}
	
	
	public void print(String str) {
		sb.append(str);
		textArea.setText(sb.toString());
	}
	
	public void println(String str) {
		print(str + '\n');
	}
	
	public void updateClient(TrafficLight tl, TrafficLightStates tls) {
		//TODO - update light colors
	}
	
	public void addClient(TrafficLight tl) {
		//TODO - add new client do list and ensure it fit in the panel
	}
	
	public void removeClient(TrafficLight tl) {
		//TODO - remove client do list and ensure it fit in the panel		
	}
	
	public JClient newJClient(TrafficLight tl, TrafficLightStates initialColor) {
		return new JClient(tl, 50, 20, initialColor);
	}
}

