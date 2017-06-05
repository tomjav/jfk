package jfk3.javassist;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Loader;
import javassist.NotFoundException;

/**
 * 
 * @author Damian Fr¹szczak
 *
 */
public class ChangeMethodBody {
	static ClassPool pool = ClassPool.getDefault();
	static Loader cl = new Loader(pool);

	public static void main(String[] args) throws NotFoundException,
			CannotCompileException, IOException, ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException,
			InvocationTargetException {

		modifyClass();
		testInvokeMethod();
	}

	private static void modifyClass() throws NotFoundException,
			CannotCompileException, IOException {
		
		Method[] z1 = A.class.getDeclaredMethods();
		
		CtClass cc = pool.get(A.class.getCanonicalName());
		CtMethod printMethod = cc.getDeclaredMethod("print");
		printMethod.insertBefore("System.out.println(\"Logg \" +$1);");
		cc.writeFile();
		
		Method[] z2 = A.class.getDeclaredMethods();
		System.out.println("sdsd");

	}

	private static void testInvokeMethod() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException,
			InvocationTargetException {
		Class cls = cl.loadClass(A.class.getCanonicalName());
		Object obj = cls.newInstance();
		Method m = cls.getDeclaredMethod("print", String.class);
		m.invoke(obj, "Testowy");
	}
}
