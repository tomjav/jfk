package zad3;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/*
 * W programie wykorzystano niektóre fragmenty i klasy
 * programu którego autorem jest Damian Fr¹szczak
 * 
 * Przyj¹³em nastêpuj¹ce za³o¿enia
 * 	1. Parametry metod s¹ tylko typami prostymi + String
 *  2. 
 * 
 */
public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame frame = new JFrame("Laboratorium3");
				frame.setMinimumSize(new Dimension(500, 500));
				frame.setLocation(600, 200);
				
				JPanel mainPanel = new JPanel(new GridBagLayout());
				
				JPanel panel = new JPanel(new GridBagLayout());
				
				panel.setBackground(Color.CYAN);
				
				frame.add(panel);
				
				JTextField field = new JTextField(20);
				field.setMinimumSize(new Dimension(100,400));
				field.setToolTipText("Œcie¿ka do pliku");
				
				JLabel label = new JLabel("Nazwa pliku .jar");
				panel.add(label);
				panel.add(field);
				//frame.add(label);
				
				JButton loadButton = new JButton();
				
				frame.add(new JButton(new AbstractAction("Za³aduj") {

					@Override
					public void actionPerformed(ActionEvent e) {
						try (Scanner scanner = new Scanner(field.getText())) {
							String className = scanner.nextLine();
							className = className.contains(".jar") ? className : className+".jar";
							System.out.println("Nazwa pliku to " + className);
							
							//Ladowanie pliku
							Class<?> loadJarFile = ClassLoaderUtil.loadJarFile(className);
							//Class<?> loadJarFile = TestClass.class;
							
							//Object createdObject = loadJarFile.newInstance();
							
							JPanel panelManagment =	ClassManagment.getGui(loadJarFile, frame);
						
							JButton addMethod = new JButton("Dodaj metodê");
							addMethod.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									System.out.println("Dodawanie nowej metody");
									JPanel addMethodPanel = AddNewMethod.getGui(loadJarFile);	
									
									JButton but = new JButton("Wróæ");
									but.addActionListener((event) -> {
										ClassManagment.updatePanel(panelManagment, loadJarFile, frame);
										update(frame, addMethodPanel, panelManagment);
									});
									
									
									addMethodPanel.add(but);
									
									update(frame, panelManagment, addMethodPanel);
								}
							});
							
							panelManagment.add(addMethod);
							
							update(frame, panel, panelManagment);
							
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
	
	private static void update(JFrame frame, JPanel removed, JPanel added){
		frame.remove(removed);
		frame.add(added);
		frame.validate();
		frame.repaint();
	}

}
