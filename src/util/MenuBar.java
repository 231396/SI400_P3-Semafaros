package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import view.DocumentScreen;

public class MenuBar {

	public static void CreateMenuBar(JFrame f) {
		JMenuBar menuBar = new JMenuBar();
		JMenu mnFile = new JMenu("File");
		JMenu mnHelp = new JMenu("Help");
		JMenuItem mntmQuit = new JMenuItem("Quit");
		JMenuItem mntmCredits = new JMenuItem("Credits");
		JMenuItem mntmDisclaimer = new JMenuItem("Disclaimer");
		JMenuItem mntmHelp = new JMenuItem("Help");

		f.setJMenuBar(menuBar);
		menuBar.add(mnFile);
		mnFile.add(mntmQuit);
		menuBar.add(mnHelp);
		mnHelp.add(mntmCredits);
		mnHelp.add(mntmDisclaimer);
		mnHelp.add(mntmHelp);

		mntmQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitAction(f);
			}
		});

		mntmCredits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				documentOpennerAction(getClass().getResourceAsStream("/resources/Credits.txt"), "Credits");
			}
		});
		mntmDisclaimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				documentOpennerAction(getClass().getResourceAsStream("/resources/Disclaimer.txt"), "Disclaimer");
			}
		});
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				documentOpennerAction(getClass().getResourceAsStream("/resources/Help.txt"), "Help");
			}
		});
	}

	static void quitAction(JFrame f) {
		f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
	}

	static void documentOpennerAction(InputStream stream, String title) {
		
		try {
			Reader reader = new InputStreamReader(stream, "UTF-8");
			char[] buffer = new char[4096];
			int charsRead = reader.read(buffer);
			String text = new String(buffer, 0, charsRead);			
			DocumentScreen ds = new DocumentScreen();			
			ds.setVisible(true);
			ds.setText(text, title);			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
