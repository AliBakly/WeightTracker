package Weight;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
public class popUp extends JDialog {

	private static final int SMALL_SIZE1 = 62;
	private static final int SMALL_SIZE2 = 54;

	private String text;
	private String title;
	private boolean error;
	public popUp(String text, String title, boolean error) {
		this.text = text;
		this.title = title;
		this.error = error;
		setTitle(title);
		
		Font font = new Font("Calibri", Font.BOLD, 16);
		//Font fontH = new Font("Calibri", Font.BOLD, 20);
		Color blue = new Color(0,181,236);
		Color grey = new Color(50, 50, 50);
		setBounds(100, 100, 450, 150);
		{
			JPanel upperPanel = new JPanel();
			upperPanel.setBackground(grey);
			getContentPane().add(upperPanel, BorderLayout.NORTH);
			{
				JLabel label = new JLabel(text);
				label.setFont(font);
				label.setForeground(blue);
				if(this.error) {
					JLabel label2 = new JLabel("ERROR:");
					label2.setFont(font);
					label2.setForeground(Color.RED);
					upperPanel.add(label2);
				}
				upperPanel.add(label);
			}
		}
		
		JPanel panel = new JPanel();
		panel.setBackground(grey);
		getContentPane().add(panel, BorderLayout.CENTER);
		StyledButtonUI niceButtonUi = new StyledButtonUI();
		JButton okButton = new JButton("Ok");
		okButton.setUI(niceButtonUi);
		panel.add(okButton);
		okButton.addActionListener(e->{
			setVisible(false);
		});
		pack();
	}

}
