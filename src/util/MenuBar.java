package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
				QuitAction();
			}
		});

		mntmCredits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TxtOpennerAction("..\\src\\resources\\Credits.txt");
			}
		});
		mntmDisclaimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TxtOpennerAction("..\\src\\resources\\Disclaimer.txt");
			}
		});
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TxtOpennerAction( "..\\src\\resources\\Help.txt" );
			}
		});
	}

	static void QuitAction() {
		
		System.exit(0);
		
		
		
		
	}

	static void TxtOpennerAction(String str) {

		try {
			java.awt.Desktop.getDesktop().open(new File(str));
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
