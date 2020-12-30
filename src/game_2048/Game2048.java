package game_2048;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Game2048 extends JFrame{
	private final int ROW = 4;
	private final int COL = 4;
	private int a[][];
	//Container f;
	JPanel f;
	JLabel status;
	int score = 0;
	
	Game2048(){
		setSize(400, 410);
		setLocationRelativeTo(null);
		setVisible(true);
		getContentPane().setLayout(new BorderLayout());
		f = new JPanel();
		f.setLayout(new GridLayout(ROW, COL, 15, 15));
		f.setBackground(SystemColor.activeCaptionBorder);
		getContentPane().add(f);
		status = new JLabel("Score: 0");
		getContentPane().add(status, BorderLayout.NORTH);
		Board();
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (!checkLose()) {
					if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
						if (left()) randomElement();
					}else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
						if (right()) randomElement();
					}else if (arg0.getKeyCode() == KeyEvent.VK_UP) {
						if (up()) randomElement();
					}else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
						if (down()) randomElement();
					}
					addElement();
					if (checkLose()) {
						JOptionPane.showMessageDialog(null, "Game Over! Press SPACE to restart!");
					}
				}
				if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
					Board();
				}
			}
		});
	}
	
	Color getBackgroundd(int value) {
	      switch (value) {
	        case 2:    return new Color(0xeee4da);
	        case 4:    return new Color(0xede0c8);
	        case 8:    return new Color(0xf2b179);
	        case 16:   return new Color(0xf59563);
	        case 32:   return new Color(0xf67c5f);
	        case 64:   return new Color(0xf65e3b);
	        case 128:  return new Color(0xedcf72);
	        case 256:  return new Color(0xedcc61);
	        case 512:  return new Color(0xedc850);
	        case 1024: return new Color(0xedc53f);
	        case 2048: return new Color(0xedc22e);
	      }
	      return new Color(0xcdc1b4);
	}
	
	void Board(){
		score = 0;
		status.setText("Score: 0");
		a = new int[ROW+2][COL+2];
		createTableData(ROW, COL);
		addElement();
	}
	
	boolean checkLose() {
		boolean bool = true;
		for (int i=1; i<=ROW; i++) {
			for (int j=1; j<COL; j++)
				if (a[i][j]==a[i][j+1] || a[i][j]==0) return false;
			if (a[i][COL] == 0) return false;
		}
		for (int j=1; j<=COL; j++) {
			for (int i=1; i<ROW; i++)
				if (a[i][j]==a[i+1][j] || a[i][j]==0) return false;
			if (a[ROW][j] == 0) return false;
		}
		return bool;
	}
	
	void addElement() {
		f.removeAll();
		for (int i=1; i<=ROW; i++)
			for (int j=1; j<=COL; j++) {
				JLabel element = new JLabel(String.valueOf(a[i][j]));
				element.setHorizontalAlignment(SwingConstants.CENTER);
				element.setFont(new Font("Tahoma", Font.PLAIN, 22));
				element.setOpaque(true);
				element.setBackground(getBackgroundd(a[i][j]));
				f.add(element);
			}
		f.repaint();
		f.revalidate();
		//setVisible(true);
	}
	
	void randomElement(){
		Random rd = new Random();
		int x, y;
		do {
			x = 1 + rd.nextInt(ROW);
			y = 1 + rd.nextInt(COL);
		} while (a[x][y] != 0);
		
		a[x][y] = 2;
	}
	
	void createTableData(int r, int c){
		randomElement();
		randomElement();
	}
	
	void addScore(int score) {
		this.score += score;
		status.setText("Score: " + String.valueOf(this.score));
	}
	
	private boolean left() {
		// TODO Auto-generated method stub
		boolean bool = false;
		for (int i=1; i<=ROW; i++) {
			int c = 0;
			for (int j=1; j<=COL; j++)
				if (a[i][j] != 0) {
					c++;
					int tmp = a[i][j];
					a[i][j] = 0;
					a[i][c] = tmp;
					if (j!=c) bool = true;
				}
			for (int j=1; j<=c; j++) {
				if (a[i][j] == a[i][j+1]) {
					a[i][j] *= 2;
					addScore(a[i][j]);
					a[i][j+1] = 0;
					j++;
					bool = true;
				}
			}
			c = 0;
			for (int j=1; j<=COL; j++)
				if (a[i][j] != 0) {
					c++;
					int tmp = a[i][j];
					a[i][j] = 0;
					a[i][c] = tmp;
				}
		}
		return bool;
	}
	
	private boolean right() {
		// TODO Auto-generated method stub
		boolean bool = false;
		for (int i=1; i<=ROW; i++) {
			int c = COL+1;
			for (int j=COL; j>=1; j--)
				if (a[i][j] != 0) {
					c--;
					int tmp = a[i][j];
					a[i][j] = 0;
					a[i][c] = tmp;
					if (j!=c) bool = true;
				}
			for (int j=COL; j>=c; j--) {
				if (a[i][j] == a[i][j-1]) {
					a[i][j] *= 2;
					addScore(a[i][j]);
					a[i][j-1] = 0;
					j--;
					bool = true;
				}
			}
			c = COL+1;
			for (int j=COL; j>=1; j--)
				if (a[i][j] != 0) {
					c--;
					int tmp = a[i][j];
					a[i][j] = 0;
					a[i][c] = tmp;
				}
		}
		return bool;
	}
	
	private boolean up() {
		// TODO Auto-generated method stub
		boolean bool = false;
		for (int j=1; j<=COL; j++) {
			int c = 0;
			for (int i=1; i<=ROW; i++)
				if (a[i][j] != 0) {
					c++;
					int tmp = a[i][j];
					a[i][j] = 0;
					a[c][j] = tmp;
					if (i!=c) bool = true;
				}
			for (int i=1; i<=c; i++) {
				if (a[i][j] == a[i+1][j]) {
					a[i][j] *= 2;
					addScore(a[i][j]);
					a[i+1][j] = 0;
					i++;
					bool = true;
				}
			}
			c = 0;
			for (int i=1; i<=ROW; i++)
				if (a[i][j] != 0) {
					c++;
					int tmp = a[i][j];
					a[i][j] = 0;
					a[c][j] = tmp;
				}
		}
		return bool;
	}
	
	private boolean down() {
		// TODO Auto-generated method stub
		boolean bool = false;
		for (int j=1; j<=COL; j++) {
			int c = ROW + 1;
			for (int i=ROW; i>=1; i--)
				if (a[i][j] != 0) {
					c--;
					int tmp = a[i][j];
					a[i][j] = 0;
					a[c][j] = tmp;
					if (i!=c) bool = true;
				}
			for (int i=ROW; i>=c; i--) {
				if (a[i][j] == a[i-1][j]) {
					a[i][j] *= 2;
					addScore(a[i][j]);
					a[i-1][j] = 0;
					i--;
					bool = true;
				}
			}
			c = ROW+1;
			for (int i=ROW; i>=1; i--)
				if (a[i][j] != 0) {
					c--;
					int tmp = a[i][j];
					a[i][j] = 0;
					a[c][j] = tmp;
				}
		}
		return bool;
	}
}
