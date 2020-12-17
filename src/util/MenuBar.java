package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
				TxtOpennerAction("..\\src\\resources\\Help.txt");
			}
		});
	}

	static void QuitAction() {

		System.exit(0);

	}

	static void TxtOpennerAction(String str) {
		
		try {
			Runtime.getRuntime().exec(String.format("notepad '%s' ", str));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
//		
//		try {
//			// constructor of file class having file as argument
//			File file = new File("path");
//			if (!Desktop.isDesktopSupported())// check if Desktop is supported by Platform or not
//			{
//				System.out.println("not supported");
//				return;
//			}
//			Desktop desktop = Desktop.getDesktop();
//			if (file.exists()) // checks file exists or not
//				desktop.open(file); // opens the specified file
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
	}
}
