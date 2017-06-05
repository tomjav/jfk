package jfk3.javassist;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.Loader;
import javassist.NotFoundException;

/**
 * 
 * @author Damian Fr¹szczak
 *
 */
public class ChangeSuperclass {
	static ClassPool pool = ClassPool.getDefault();
	static Loader cl = new Loader(pool);

	public static void main(String[] args) throws NotFoundException,
			CannotCompileException, ClassNotFoundException, IOException {
		modifyClassHierachy(A.class, B.class);
		printClassHierarchy(cl.loadClass(A.class.getCanonicalName()), true);
		printClassHierarchy(cl.loadClass(B.class.getCanonicalName()), true);
	}

	private static void modifyClassHierachy(Class<?> mainClass, Class<?> child)
			throws NotFoundException, CannotCompileException, IOException {
		CtClass ct = pool.get(child.getCanonicalName());
		ct.setSuperclass(pool.get(mainClass.getCanonicalName()));
		ct.writeFile();

	}

	private static void printClassHierarchy(Class<?> cls, boolean initial) {
		if (initial) {
			System.out.println("Hierarchia klasy " + cls);
			printClassHierarchy(cls.getSuperclass(), false);
		} else if (cls != null) {
			System.out.println(cls);
			printClassHierarchy(cls.getSuperclass(), false);
		} else {
			System.out.println("################################");
		}

	}
}
