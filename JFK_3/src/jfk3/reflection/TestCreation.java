package jfk3.reflection;

import java.lang.reflect.InvocationTargetException;

/**
 * 
 * @author Damian Fr¹szczak
 *
 */
public class TestCreation {

	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		TestBean testBean1 = TestBean.class.newInstance();
		TestBean testBean2 = TestBean.class.getConstructor(int.class)
				.newInstance(1);
		System.out.println(testBean1.getValue());
		System.out.println(testBean2.getValue());
	}
}
