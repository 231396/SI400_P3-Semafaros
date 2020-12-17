package util;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class JCircle extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public Color color = Color.black;
	
	boolean enable;
	
	public JCircle(Color color) 
	{
		this.color = color;
	}	
	
	@Override
	protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
		g.setColor(color);
		if (enable)
			g.fillOval(0, 0, this.getBounds().width, this.getBounds().height);
		else
			g.drawOval(0, 0, this.getBounds().width - 1, this.getBounds().height - 1);
    }
	
	public void setBounds(int x, int y, int r) {
		this.setBounds(x, y, r, r);
	}
	
	public void changeState(boolean enable) {
		this.enable = enable;
		repaint();
	}
	
}
