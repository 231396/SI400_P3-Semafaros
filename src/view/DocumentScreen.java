package view;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class DocumentScreen extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextPane txtpnDesc;

	public DocumentScreen() {
		setBounds(100, 100, 650, 295);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setIcon(new ImageIcon(DocumentScreen.class.getResource("/resources/unicamp-logo.jpg")));
		lblNewLabel.setBounds(10, 11, 189, 221);
		contentPane.add(lblNewLabel);
		
		txtpnDesc = new JTextPane();
		JTextPane textPane=new JTextPane();
		StyledDocument style = textPane.getStyledDocument();
		SimpleAttributeSet align= new SimpleAttributeSet();
		StyleConstants.setAlignment(align, StyleConstants.ALIGN_RIGHT);
		style.setParagraphAttributes(0, style.getLength(), align, false);
		txtpnDesc.setDisabledTextColor(UIManager.getColor("Button.foreground"));
		txtpnDesc.setEditable(false);
		txtpnDesc.setEnabled(false);
		txtpnDesc.setBackground(Color.WHITE);
		txtpnDesc.setSelectionColor(Color.BLACK);
		txtpnDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JScrollPane scrollPane = new JScrollPane(txtpnDesc);
		scrollPane.setBounds(224,11,393,221);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);		
		contentPane.add(scrollPane);	
		
	}
	
    public void setText(String text, String title) {
    	txtpnDesc.setText(text);
    	setTitle(title);
    }
}
