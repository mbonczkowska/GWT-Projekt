package package1.shared;

import java.io.Serializable;

import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.Persistent;

public class Clients implements Serializable{
	@NotPersistent
	private static final long ID = 1L;

	private Long id;
	@Persistent
	private String name;
	@Persistent
	private String surname;
	
	public Clients(String name,String surname){
		this.name = name;
		this.surname = surname;
	}
	public Clients(){
	
	}
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
}
