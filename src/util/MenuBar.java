package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
				quitAction();
			}
		});

		mntmCredits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				documentOpennerAction("src/resources/Credits.txt");
			}
		});
		mntmDisclaimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				documentOpennerAction("src/resources/Disclaimer.txt");
			}
		});
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				documentOpennerAction("src/resources/Help.txt");
			}
		});
	}

	static void quitAction() {
		System.exit(0);
	}

	static void documentOpennerAction(String path) {
		File file = new File(path);
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			String str = new String(data, "ISO-8859-1");
			DocumentScreen ds = new DocumentScreen();
			ds.setVisible(true);
			ds.setText(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
