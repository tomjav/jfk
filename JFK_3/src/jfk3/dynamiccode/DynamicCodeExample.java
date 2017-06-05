package jfk3.dynamiccode;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.tools.JavaFileObject;

/**
 * 
 * @author Damian Fr¹szczak
 *
 */	
public class DynamicCodeExample extends JFrame {

	private static String TEMP_FILE_DIR = "/classes/dynamic";
	private String METHOD_NAME = "print";
	private Class[] params = { String.class };
	private Object[] paramsObj = { "Nowy tekst" };
	JTextField field = new JTextField();
	JTextArea area = new JTextArea();
	JButton button = new JButton("Kliknij mnie");

	public DynamicCodeExample() throws HeadlessException {
		super();
		add(field, BorderLayout.NORTH);
		add(new JScrollPane(area));
		add(button, BorderLayout.SOUTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				button.setEnabled(false);
				new Thread(new Runnable() {

					@Override
					public void run() {
						JavaFileObject jfo = DynamicCodeCompiler
								.createJavaFileObjectFromString(
										field.getText(), area.getText());
						if (jfo != null) {
							DynamicCodeCompiler.compile(Arrays.asList(jfo), TEMP_FILE_DIR);
							DynamicCodeCompiler.createObjectAndInvokeMethod(
									TEMP_FILE_DIR, field.getText(),
									METHOD_NAME, params, paramsObj);
						}
						EventQueue.invokeLater(new Runnable() {

							@Override
							public void run() {
								button.setEnabled(true);
							}
						});

					}
				}).start();

			}
		});
		pack();
		setVisible(true);
	}

	public static void main(String[] args) throws IOException {
		Files.createDirectories(Paths.get(TEMP_FILE_DIR));
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new DynamicCodeExample();
			}
		});

	}
}
