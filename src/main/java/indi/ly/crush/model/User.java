package indi.ly.crush.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * <h2>用户</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public class User
		implements Serializable {
	@Serial
	private static final long serialVersionUID = -6849794470754667710L;
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		User user = (User) o;
		return Objects.equals(name, user.name);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
	
	@Override
	public String toString() {
		return "User{" + "name='" + name + '\'' + '}';
	}
}
