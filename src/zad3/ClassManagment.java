package zad3;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ClassManagment {

	public static Set<String> addedMethods = new HashSet<>();
	
	public static JPanel getGui(Class<?> className, JFrame frame){
		
		JLabel classLabel = new JLabel("Wykonaj akcjie na klasie " + className.getCanonicalName());
		
		JButton baza = new JButton("Dodaj obiekt bazowy");
		baza.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//Pobieranie metod klasy
		Set<String> methodNames = ClassLoaderUtil.getMethodNames(className);
		
		JPanel panel = new JPanel(new GridBagLayout());
		
		panel.add(classLabel);
		panel.add(baza);
		
		panel.setBackground(Color.WHITE);
		
		JLabel label = new JLabel("Lista metod klasy");
		panel.add(label);
		//Umieszczanie listy metod do zarz¹dzania
		for(String name : methodNames){
			
			JButton b1 = new JButton(name);
			b1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JPanel methodPanel = MethodManagment.getGui(name, className);	
					update(frame, panel, methodPanel);
					System.out.println("Przejœcie do metody");
				}
			});
			
			panel.add(b1);
		}
		return panel;
	}
	
	public static JPanel updatePanel(JPanel panel, Class<?> className, JFrame frame){
		for(String name : addedMethods){
			JButton b1 = new JButton(name);
			b1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JPanel methodPanel = MethodManagment.getGui(name, className);	
					update(frame, panel, methodPanel);
					System.out.println("Przejœcie do metody");
				}
			});
			panel.add(b1);
		}
		return panel;
		
	}
	private static void update(JFrame frame, JPanel removed, JPanel added){
		frame.remove(removed);
		frame.add(added);
		frame.validate();
		frame.repaint();
	}

}
