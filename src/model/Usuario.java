package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {


	private final String username;
	private final String password;

	public static List<Usuario> users = new ArrayList<>();

	public Usuario(String username, String password) {
		this.username = username;
		this.password = password;
	}


	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public boolean equals(Object o){
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		Usuario usuario = (Usuario) o;
		return Objects.equals(username, usuario.username);
	}

	@Override
	public int hashCode(){
		return Objects.hash(username, password);
	}


	public static List<Usuario> getUsers(){
		return users;
	}
}
