package jfk3.reflection;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
/**
 * 
 * @author Damian Fr¹szczak
 *
 */
public class TestGui {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame frame = new JFrame();
				JTextField field = new JTextField();
				frame.add(field);
				frame.add(new JButton(new AbstractAction("Button do zmian") {

					@Override
					public void actionPerformed(ActionEvent e) {
						try (Scanner scanner = new Scanner(field.getText())) {
							String methodName = scanner.next();
							String param = scanner.next();
							Object source = e.getSource();
							AbstractButton.class.getDeclaredMethod(methodName, String.class).invoke(source, param);
						} catch (Exception ex) {
							ex.printStackTrace();
						}

					}
				}), BorderLayout.SOUTH);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.pack();
				frame.setVisible(true);
			}
		});

	}
}
