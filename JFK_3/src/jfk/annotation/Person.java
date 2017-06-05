package jfk.annotation;

import java.util.UUID;
/**
 * 
 * @author Damian Fr¹szczak
 *
 */
public class Person {

	private String uuid = UUID.randomUUID().toString();
	@Info
	private String name;
	@Info
	private String surname;

	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Person(String name, String surname) {
		super();
		this.name = name;
		this.surname = surname;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "Person [uuid=" + uuid + ", name=" + name + ", surname="
				+ surname + "]";
	}

}
