package view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import util.JClientScroll;
import util.MenuBar;
import util.OnClose;

public class ServerScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;

	StringBuilder sb = new StringBuilder();
	JTextArea logArea;	
	JScrollPane scrollPaneLog;
	JClientScroll clientScrolls;
	
	public ServerScreen() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 640, 470);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		MenuBar.CreateMenuBar(this);		
		
		logArea = new JTextArea("");
		logArea.setLineWrap(true);		
		scrollPaneLog = new JScrollPane(logArea);
		scrollPaneLog.setBounds(337,11,280,400);
		scrollPaneLog.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);		
		contentPane.add(scrollPaneLog);	
		
		clientScrolls = new JClientScroll();
		clientScrolls.setBounds(10, 10, 303, 400);
		contentPane.add(clientScrolls);	
		
	}
		
	public void log(String str) {
		sb.append(str);
		logArea.setText(sb.toString());		
		logArea.setCaretPosition(logArea.getDocument().getLength());
	}
	
	public void logln(String str) {
		log(str + '\n');
	}
	
	public JClientScroll getJClientScroll() {
		return clientScrolls;
	}	
	
	public void setCloseEvent(OnClose onClose) {
		addWindowListener(new WindowAdapter(){
	        public void windowClosing(WindowEvent e){
	        	onClose.onCloseTrigger();
	        }
	    });
	}	
}

