package jfk3.dynamiccode;

import java.awt.Component;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.AbstractButton;

/**
 * 
 * @author Damian Fr¹szczak
 *
 */
public class DynamicCompiledCode {

	public void setText(Component component, String text)
			throws NoSuchFieldException, SecurityException,
			NoSuchMethodException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Field field = component.getClass().getDeclaredField("button");
		field.setAccessible(true);
		Object buttonInComponent = field.get(component);
		Method method = AbstractButton.class.getDeclaredMethod("setText",
				String.class);
		method.invoke(buttonInComponent, text);
	}
}
