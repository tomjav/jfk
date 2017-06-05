package zad3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javassist.ClassPool;
import javassist.CtClass;

public class MethodManagment {

	public static JPanel getGui(String methodName, Class<?> className){
		
		JPanel panel = new JPanel();
		Set<Parameter> params =  new HashSet<>();
		
		Method method = findMethod(methodName, className);
		
		Method[] m = className.getDeclaredMethods();
		
		for(Parameter arg : method.getParameters()){
			params.add(arg);
		}
		
		JLabel args = new JLabel("Parametry metody");
		for(Parameter p : params){
			 panel.add(new JLabel(p.getType().getName()));
			 panel.add(new JTextField(10));
		}
		
		JButton execute = new JButton("Wykonaj");
		
		execute.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					method.invoke(className.newInstance());
					
				}catch (Exception ex) {
					System.out.println("B³¹d przy wywolywaniu metody "+ ex.getMessage());
				}
				
			}
		});
		
		JButton edit = new JButton("Edytuj");
		
		
		panel.add(execute);
		panel.add(edit);
		
		return panel;		
	}
	
	private static Method findMethod(String name, Class<?> className){
		for(Method method : className.getDeclaredMethods()){
			if(method.getName().equals(name)){
				return method;
			}
		}
		return null;
	}
}
