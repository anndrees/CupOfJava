package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {


	private String username;
	private String password;

	public static List<Usuario> users = new ArrayList<>();

	public Usuario(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public void setPassword(String password){
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

	public void addUser(Usuario user){
		users.add(user);
	}

	public static List<Usuario> getUsers(){
		return users;
	}
}
