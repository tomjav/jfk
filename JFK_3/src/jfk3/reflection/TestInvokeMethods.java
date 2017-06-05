package jfk3.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * @author Damian Fr¹szczak
 *
 */
public class TestInvokeMethods {
	public static void main(String[] args) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		TestBean bean = new TestBean();
		for (Method method : TestBean.class.getDeclaredMethods()) {
			System.out.println(method.getName());
		}
		System.out.println("PRZED " + bean.getValue());
		Method increaseValueField = TestBean.class
				.getDeclaredMethod("increaseValue");
		increaseValueField.invoke(bean);
		System.out.println("PO " + bean.getValue());
	}
}
