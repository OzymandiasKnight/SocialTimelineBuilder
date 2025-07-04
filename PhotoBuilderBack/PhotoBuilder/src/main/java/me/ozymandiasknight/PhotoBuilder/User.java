package me.ozymandiasknight.PhotoBuilder;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    private String name = "";
    private String mail = "@gmail.com";
    private String password = "";
    private Boolean verified = false;
    private String verification_token = null;

    private String birthday = "";

	public void setMail(String p_email) {
		mail = p_email;
		name = p_email.split("@")[0];
	}

	public String getMail() {
		return mail;
	}
	
	public void setPassword(String p_password) {
		password = p_password;
	}
	
	public void setBirthday(String p_birthday) {
		birthday = p_birthday;
	}
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	
	public String getBirthday() {
		return birthday;
	}
}
