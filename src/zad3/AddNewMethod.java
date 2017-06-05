package zad3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.*;

public class AddNewMethod {

	public static JPanel getGui(Class<?> myclass){
		
		JPanel panel = new JPanel();
		JLabel nazwa = new JLabel("Nazwa metody");
		JLabel cialo = new JLabel("Cia³o metody");
		JTextField methodName = new JTextField(20);
		//JTextArea body = new JTextArea();
		
		JTextArea body = new JTextArea(); 
		body.setRows(25);
		body.setColumns(25);
		body.setWrapStyleWord(true);
		   JScrollPane scroll = new JScrollPane (body);
		 //panel.add(scroll); 
		
		
		JButton button = new JButton("Dodaj metodê");
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					addMethod(myclass, methodName.getText(), body.getText());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			}
		});
		
		panel.add(nazwa);
		panel.add(methodName, BorderLayout.NORTH);
		panel.add(cialo);
		panel.add(scroll);
		panel.add(button, BorderLayout.SOUTH);
		panel.setBackground(Color.GREEN);
		
		return panel;
	}
	
	private static void addMethod(Class<?> myclass, String name, String body) throws NotFoundException, CannotCompileException, IOException, NoSuchMethodException, SecurityException{
		System.out.println("Dodaje now¹ klasê, ale nie jest to ³atwe...");
		
		ClassPool cp = ClassPool.getDefault();
		
		CtClass klasa = cp.get(myclass.getName());
		
		//String method = "public void wypisz(){System.out.println(\"To dzia³a!\");}";
		String method = body;
		
		CtMethod m = CtNewMethod.make(method, klasa);
		
		String nethodName = m.getName();
		
		klasa.addMethod(m);
		
		ClassManagment.addedMethods.add(m.getName());
				
		klasa.writeFile();
		
		Method[] methods = myclass.getDeclaredMethods();
		
//		myclass.getMethod(nethodName);
		
		System.out.println("Dodano now¹ metodê");
	}
	
}
