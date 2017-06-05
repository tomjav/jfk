package jfk3.dynamiccode;

import java.io.IOException;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

/**
 * 
 * @author Damian Fr�szczak
 *
 */
public class InMemoryJavaFileObject extends SimpleJavaFileObject {
	private String contents = null;

	public InMemoryJavaFileObject(String className, String contents)
			throws Exception {
		super(URI.create("string:///" + className.replace('.', '/')
				+ Kind.SOURCE.extension), Kind.SOURCE);
		this.contents = contents;
	}

	public CharSequence getCharContent(boolean ignoreEncodingErrors)
			throws IOException {
		return contents;
	}
}