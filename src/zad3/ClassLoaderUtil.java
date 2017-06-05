package zad3;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

public class ClassLoaderUtil {
	
	
	private static String PATH_TO_JAR = "testclassloader.jar";
	
	private static String CLASS_FROM_JAR = "test.classloader.TestClass";
	private static String METHOD_NAME = "testMethod";
	
	
	public static Class<?> loadJarFile(String fileName) throws MalformedURLException,
		ClassNotFoundException {
		File file = new File(fileName);
		URL url = file.toURI().toURL();
		URL[] urls = new URL[] { url };
		ClassLoader cl = new URLClassLoader(urls);
		return cl.loadClass(CLASS_FROM_JAR);
	}
	
	public static Set<String> getMethodNames(Class<?> loadedClass){
		Set methodsSet = new HashSet<String>();
		for(Method method : loadedClass.getDeclaredMethods()){
			methodsSet.add(method.getName());
		}
		
		return methodsSet;
		
	}
}
