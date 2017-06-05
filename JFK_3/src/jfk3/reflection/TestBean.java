package jfk3.reflection;

/**
 * 
 * @author Damian Fr¹szczak
 *
 */
public class TestBean {

	int value;

	public TestBean() {
		super();
	}

	public TestBean(int value) {
		super();
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void increaseValue() {
		value++;
	}
}
