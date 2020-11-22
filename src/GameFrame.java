import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

public class GameFrame extends JFrame {
	private JMenuItem startItem = new JMenuItem("start");
	private JMenuItem stopItem = new JMenuItem("stop");
	
	private GamePanel gamePanel = new GamePanel();
	private ScorePanel scorePanel = new ScorePanel();
	private SpeedPanel speedPanel = new SpeedPanel();
	private EditPanel editPanel = new EditPanel();

	public GameFrame() {
		setTitle("Typing Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1024, 768);
		
		splitPane();
		makeMenu();
		setResizable(false);
		setVisible(true);
	}
	
	private void splitPane() {
		JSplitPane hPane = new JSplitPane();
		getContentPane().add(hPane, BorderLayout.CENTER);
		
		hPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		hPane.setDividerLocation(780);
		hPane.setEnabled(false);
		hPane.setLeftComponent(gamePanel);
		hPane.setDividerSize(1);
		
		JSplitPane pPane1 = new JSplitPane();
		pPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		pPane1.setDividerLocation(100);
		pPane1.setTopComponent(speedPanel);
		
		
		JSplitPane pPane2 = new JSplitPane();
		pPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		pPane2.setDividerLocation(200);
		pPane2.setTopComponent(scorePanel);
		pPane2.setBottomComponent(editPanel);
		pPane1.setBottomComponent(pPane2);
		pPane1.setDividerSize(1);
		pPane2.setDividerSize(1);
		pPane1.setEnabled(false);
		pPane2.setEnabled(false);
		
		hPane.setBorder(null);
		pPane1.setBorder(null);
		pPane2.setBorder(null);
		hPane.setRightComponent(pPane1);
	}
	
	private void makeMenu() {
		JMenuBar mBar = new JMenuBar();
		setJMenuBar(mBar);
		JMenu fileMenu = new JMenu("Game");
		fileMenu.add(startItem);
		fileMenu.add(stopItem);
		mBar.add(fileMenu);
		
		startItem.addActionListener(new StartAction());
	}
	
	private class StartAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("hello");
		}
	}
}
