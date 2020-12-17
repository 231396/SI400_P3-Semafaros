package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import util.JClientScroll;
import util.MenuBar;

public class ServerScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	
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
	
	private JPanel contentPane;

	StringBuilder sb = new StringBuilder();
	JTextArea logArea;	
	JScrollPane scrollPaneLog;
	
	public ServerScreen() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 560, 480);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		MenuBar.CreateMenuBar(this);		
		
		logArea = new JTextArea("Server");
		logArea.setLineWrap(true);		
		scrollPaneLog = new JScrollPane(logArea);
		scrollPaneLog.setBounds(337,11,200,400);
		scrollPaneLog.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);		
		contentPane.add(scrollPaneLog);	
		
		JClientScroll jcs = new JClientScroll();
		jcs.setBounds(10, 10, 303, 400);
		contentPane.add(jcs);	
	}
		
	public void print(String str) {
		sb.append(str);
		logArea.setText(sb.toString());
	}
	
	public void println(String str) {
		print(str + '\n');
	}
	
}

