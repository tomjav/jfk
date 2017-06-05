package jfk3.classloading;

import java.io.File;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * 
 * @author Damian Fr¹szczak
 *
 */
public class ClassScope

{
	private static final Field CLASSES_VECTOR_FIELD;
	private static final CallerResolver CALLER_RESOLVER;
	private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
	private static final Throwable CVF_FAILURE, CR_FAILURE;

	static {
		Throwable failure = null;
		Field tempf = null;
		try {
			tempf = ClassLoader.class.getDeclaredField("classes");
			if (tempf.getType() != Vector.class) {
				throw new RuntimeException("not of type java.util.Vector: "
						+ tempf.getType().getName());
			}
			tempf.setAccessible(true);
		} catch (Throwable t) {
			failure = t;
		}
		CLASSES_VECTOR_FIELD = tempf;
		CVF_FAILURE = failure;
		failure = null;
		CallerResolver tempcr = null;
		try {
			tempcr = new CallerResolver();
		} catch (Throwable t) {
			failure = t;
		}
		CALLER_RESOLVER = tempcr;
		CR_FAILURE = failure;
	}

	@SuppressWarnings("unchecked")
	public static Class<?>[] getLoadedClasses(final ClassLoader loader) {
		if (loader == null) {
			throw new IllegalArgumentException("null input: loader");
		}
		if (CLASSES_VECTOR_FIELD == null) {
			throw new RuntimeException(
					"ClassScope::getLoadedClasses() cannot be used in this JRE",
					CVF_FAILURE);
		}
		try {
			final Vector<Class<?>> classes = (Vector<Class<?>>) CLASSES_VECTOR_FIELD
					.get(loader);
			if (classes == null)
				return EMPTY_CLASS_ARRAY;
			final Class<?>[] result;
			synchronized (classes) {
				result = new Class<?>[classes.size()];
				classes.toArray(result);
			}
			return result;
		} catch (IllegalAccessException e) {
			e.printStackTrace(System.out);
			return EMPTY_CLASS_ARRAY;
		}
	}

	public static Class<?>[] getLoadedClasses(final ClassLoader[] loaders) {
		if (loaders == null)
			throw new IllegalArgumentException("null input: loaders");
		final List<Class<?>> resultList = new LinkedList<Class<?>>();
		for (int l = 0; l < loaders.length; ++l) {
			final ClassLoader loader = loaders[l];
			if (loader != null) {
				final Class<?>[] classes = getLoadedClasses(loaders[l]);
				resultList.addAll(Arrays.asList(classes));
			}
		}
		final Class<?>[] result = new Class<?>[resultList.size()];
		resultList.toArray(result);
		return result;
	}

	public static ClassLoader[] getCallerClassLoaderTree() {
		if (CALLER_RESOLVER == null)
			throw new RuntimeException(
					"Class<?>Scope::getCallerClassLoaderTree() cannot be used in this JRE",
					CR_FAILURE);
		final Class<?>[] callContext = CALLER_RESOLVER.getClassContext();
		final Set<ClassLoader> resultSet = new HashSet<ClassLoader>();
		for (int c = 2; c < callContext.length; ++c) {
			getClassLoaderTree(callContext[c], resultSet);
		}
		final ClassLoader[] result = new ClassLoader[resultSet.size()];
		resultSet.toArray(result);
		return result;

	}

	public static URL getClassLocation(final Class<?> cls) {
		if (cls == null)
			throw new IllegalArgumentException("null input: cls");
		URL result = null;
		final String clsAsResource = cls.getName().replace('.', '/')
				.concat(".class");
		final ProtectionDomain pd = cls.getProtectionDomain();
		if (pd != null) {
			final CodeSource cs = pd.getCodeSource();
			if (cs != null)
				result = cs.getLocation();
			if (result != null) {
				if ("file".equals(result.getProtocol())) {
					try {
						if (result.toExternalForm().endsWith(".jar")
								|| result.toExternalForm().endsWith(".zip"))
							result = new URL("jar:"
									.concat(result.toExternalForm())
									.concat("!/").concat(clsAsResource));
						else if (new File(result.getFile()).isDirectory())
							result = new URL(result, clsAsResource);
					} catch (MalformedURLException ignore) {
					}
				}
			}
		}
		if (result == null) {
			final ClassLoader clsLoader = cls.getClassLoader();
			result = clsLoader != null ? clsLoader.getResource(clsAsResource)
					: ClassLoader.getSystemResource(clsAsResource);
		}
		return result;

	}

	private static final class CallerResolver extends SecurityManager {
		protected Class<?>[] getClassContext() {
			return super.getClassContext();

		}

	}

	private ClassScope() {

	}

	private static void getClassLoaderTree(final Class<?> cls,

	final Set<ClassLoader> resultSet) {
		if ((cls != null) && (resultSet != null)) {
			for (ClassLoader loader = cls.getClassLoader(); loader != null; loader = loader
					.getParent()) {
				resultSet.add(loader);
			}
		}
	}

}
