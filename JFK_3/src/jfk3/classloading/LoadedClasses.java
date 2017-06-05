package jfk3.classloading;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 
 * @author Damian Fr¹szczak
 *
 */
public class LoadedClasses {
	private static String PATH_TO_JAR = "testclassloader.jar";
	private static String CLASS_FROM_JAR = "test.classloader.TestClass";
	private static String METHOD_NAME = "testMethod";

	public static void main(String[] args) throws IllegalArgumentException,
			IllegalAccessException, MalformedURLException,
			ClassNotFoundException, InstantiationException,
			NoSuchMethodException, SecurityException, InvocationTargetException {
		showClassesLoadedByClassLoader(LoadedClasses.class.getClassLoader()
				.getParent());
		showClassesLoadedByClassLoader(LoadedClasses.class.getClassLoader());

		Class loadedClass = loadJarFile();
		createObjectAndExecuteMethodFromLoadedJarFile(loadedClass);

	}

	private static void createObjectAndExecuteMethodFromLoadedJarFile(
			Class loadedClass) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException,
			InvocationTargetException {
		Object createdObject = loadedClass.newInstance();
		Method method = loadedClass.getMethod(METHOD_NAME);
		method.invoke(createdObject);
	}

	private static void showClassesLoadedByClassLoader(ClassLoader classLoader)
			throws IllegalArgumentException, IllegalAccessException {
		System.out.println(String.format("Classloader %s zaladowal klasy %s",
				classLoader, ClassLoaderResolver.getSingleton()
						.getLoadedClasses(classLoader)));

	}

	private static Class<?> loadJarFile() throws MalformedURLException,
			ClassNotFoundException {
		File file = new File(PATH_TO_JAR);
		URL url = file.toURI().toURL();
		URL[] urls = new URL[] { url };
		ClassLoader cl = new URLClassLoader(urls);
		return cl.loadClass(CLASS_FROM_JAR);
	}
}
