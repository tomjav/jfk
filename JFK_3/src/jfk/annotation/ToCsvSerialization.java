package jfk.annotation;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Damian Fr¹szczak
 *
 */
public class ToCsvSerialization {
	private static final String SEPARATOR = ";";

	public static void main(String[] args) throws Exception {
		List<Person> persons = new LinkedList<Person>();
		for (int i = 0; i < 5; i++) {
			persons.add(new Person(i + "", i + ""));
		}
		for (int i = 0; i < 5; i++) {
			System.out.println(persons.get(i));
		}

		String personsAsCsvFile = toString(persons);
		System.out.println(personsAsCsvFile);
	}

	private static <T> String toString(List<T> objects) throws Exception {
		StringBuffer result = new StringBuffer();
		for (T object : objects) {
			StringBuffer objectString = new StringBuffer();
			for (Field field : object.getClass().getDeclaredFields()) {
				Info infoAnnotation = field.getAnnotation(Info.class);
				if (infoAnnotation != null) {
					field.setAccessible(true);
					objectString.append(field.get(object) + SEPARATOR);
				}
			}
			result.append(objectString.substring(0, objectString.length() - 1)
					+ "\n");
		}
		return result.toString();
	}
}
