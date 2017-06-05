package zad3;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Locale;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * 
 * @author Damian Fr¹szczak
 *
 */
public class DynamicCodeCompiler {

	public static JavaFileObject createJavaFileObjectFromString(
			String className, String code) {
		JavaFileObject jfo = null;
		try {
			jfo = new InMemoryJavaFileObject(className, code);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return jfo;
	}

	public static boolean compile(Iterable<? extends JavaFileObject> files,
			String classOutputDir) {
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DynamicCodeCompilationDiagnosticListener c = new DynamicCodeCompilationDiagnosticListener();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				c, Locale.ENGLISH, null);
		Iterable options = Arrays.asList("-d", classOutputDir);
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager,
				c, options, null, files);
		return task.call();

	}

	public static void createObjectAndInvokeMethod(String classOutputFolder,
			String classToLoad, String methodName, Class[] params,
			Object[] paramsObj) {
		File file = new File(classOutputFolder);
		try {
			URL url = file.toURI().toURL();
			URL[] urls = new URL[] { url };
			ClassLoader loader = new URLClassLoader(urls);
			Class thisClass = loader.loadClass(classToLoad);
			Object instance = thisClass.newInstance();
			Method thisMethod = thisClass.getDeclaredMethod(methodName, params);
			thisMethod.invoke(instance, paramsObj);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
