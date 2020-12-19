package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class HelpView extends JFrame {

	private JPanel contentPane;
	private JTextPane txtpnDesc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HelpView frame = new HelpView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public HelpView() {
		setBounds(100, 100, 658, 323);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(HelpView.class.getResource("/resources/unicamp-logo.jpg")));
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
		txtpnDesc.setBackground(UIManager.getColor("CheckBox.light"));
		txtpnDesc.setSelectionColor(Color.BLACK);
		txtpnDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
		txtpnDesc.setText("Este projeto consiste no desenvolvimento de um programa distribu\u00EDdo para controle de uma rede simulada de sem\u00E1foros.\r\nO programa possui interface gr\u00E1fica constru\u00EDda a partir dos componentes Swing, que consista em dois m\u00F3dulos diferentes: um servidor e um cliente.\r\nO servidor deve coordena e comandar os sem\u00E1foros (usando um controle temporizado), deve permitir que o usu\u00E1rio possa acompanhar online quantos s\u00E3o os sem\u00E1foros ativos e quais os estados comandados; ao desligar o servidor, todos os sem\u00E1foros devem ser encerrados.\r\nO cliente \u00E9 basicamente um sem\u00E1foro com as luzes convencionais e deve, ao iniciar, enviar uma mensagem de registro ao servidor; a partir da\u00ED, deve receber os comandos de estado (qual luz deve ser acessa) e apresentar a respectiva representa\u00E7\u00E3o gr\u00E1fica. Se um sem\u00E1foro (m\u00F3dulo cliente) for desligado, ele deve comunicar esta a\u00E7\u00E3o ao servidor, para que este \u00FAltimo pare de enviar comandos a ele. Pode haver um n\u00FAmero arbitr\u00E1rio de clientes ativos da rede.");
		txtpnDesc.setBounds(209, 11, 423, 242);
		contentPane.add(txtpnDesc);
		
		
	}
	
    public void setText(String text) {
    	txtpnDesc.setText(text);
    	
    }
}
