package jfk3.reflection;

import java.lang.reflect.Field;

import javax.swing.plaf.metal.MetalBorders.TextFieldBorder;

/**
 * 
 * @author Damian Fr¹szczak
 *
 */		
public class TestFields {

	public static void main(String[] args) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		TestBean bean = new TestBean();
		for (Field field : TestBean.class.getDeclaredFields()) {
			System.out.println(field.getName());
		}
		System.out.println("PRZED " + bean.getValue());
		Field valueField = TestBean.class.getDeclaredField("value");
		valueField.setInt(bean, 1);
		System.out.println("PO " + bean.getValue());
	}
}
