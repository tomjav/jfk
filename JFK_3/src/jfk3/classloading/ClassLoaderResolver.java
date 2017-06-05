package jfk3.classloading;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
/**
 * 
 * @author Damian Fr¹szczak
 *
 */
public class ClassLoaderResolver {

	private static ClassLoaderResolver singleton;
	private Field classesFields;

	private ClassLoaderResolver() {
		super();
		init();
	}

	public static synchronized ClassLoaderResolver getSingleton() {
		if (singleton == null) {
			singleton = new ClassLoaderResolver();
		}
		return singleton;
	}

	private void init() {
		try {
			classesFields = ClassLoader.class.getDeclaredField("classes");
			if (classesFields.getType() != Vector.class) {
			}
			classesFields.setAccessible(true);
		} catch (Throwable t) {
		}

	}

	public List<Class<?>> getLoadedClasses(final ClassLoader... loaders)
			throws IllegalArgumentException, IllegalAccessException {
		if (loaders.length == 0)
			throw new IllegalArgumentException("null input: loaders");
		final List<Class<?>> resultList = new LinkedList<Class<?>>();
		for (int l = 0; l < loaders.length; ++l) {
			final ClassLoader loader = loaders[l];
			if (loader != null) {
				List<Class<?>> classes = getLoadedClassesByClassLoader(loaders[l]);
				resultList.addAll(classes);
			}
		}
		return resultList;

	}

	private List<Class<?>> getLoadedClassesByClassLoader(ClassLoader classLoader)
			throws IllegalArgumentException, IllegalAccessException {
		final Vector<Class<?>> classes = (Vector<Class<?>>) classesFields
				.get(classLoader);
		if (classes == null)
			return new LinkedList<Class<?>>();
		return new ArrayList<Class<?>>(classes);
	}

}
