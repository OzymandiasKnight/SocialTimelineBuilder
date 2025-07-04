package me.ozymandiasknight.PhotoBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;	
@RestController
public class EndpointController {
    

    @GetMapping("/api/status")
    public ResponseEntity<?> check_State(HttpSession session) {
        Object user_obj = session.getAttribute("user");
        Map<String, String> response = new HashMap<>();
        System.out.println(user_obj);
        Boolean is_connected = user_obj!=null;
        response.put("connected", String.valueOf(is_connected));
        
        if (is_connected) {
        	if (user_obj instanceof User) {
        		User user = (User) user_obj;
        		response.put("username", user.getName());
        	}
        }
        
        return ResponseEntity.ok(response);
    }
    
    
    @Autowired
    private DatabaseController databaseController;
    
    private String hash_password(String password) {
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	String hashed_password = encoder.encode(password);
    	return hashed_password;
    }
    
    private Boolean is_hashed_password(String db_password, String try_password) {
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    	boolean match = encoder.matches(try_password, db_password);
    	return match;
    }
    
    @PostMapping("/api/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RequestRegister request) {
    	System.out.println("Email: " + request.getEmail());
    	Map<String, String> response = new HashMap<>();
    	
    	//Check for valid mail format "@" and "."
    	boolean valid_mail = request.getEmail().split("@").length == 2;
    	valid_mail = valid_mail && request.getEmail().split("\\.").length>=2;
    	valid_mail = valid_mail && request.getEmail().length() > 5;
    	
    	//Check for password
    	String check_pass = request.getPassword();
    	String message_password = "";
    	
    	message_password = check_pass.matches("[A-Za-z0-9*#€]+") ? "successfull" : "Password contains an invalid character";
    	message_password = check_pass.matches(".*[*#€].*") ? message_password : "Password must contain a special character: *, # or €";
    	message_password = check_pass.matches(".*[a-z].*") ? message_password : "Password must contain a lowercase character";
    	message_password = check_pass.matches(".*[A-Z].*") ? message_password : "Password must contain a uppercase character";
    	message_password = check_pass.matches(".*[0-9].*") ? message_password : "Password must contain a number";
    	message_password = check_pass.length()>12 ? message_password : "Password must be 12 characters or more";
    	message_password = check_pass.length()<25 ? message_password : "Password must less than 25 characters";
    	message_password = (check_pass!="") ? message_password : "Please enter a password";
    	
    	
    	//Check if email exist in database
    	boolean taken_mail = databaseController.existsByMail(check_pass);
    	System.out.println(check_pass);

    	boolean valid_birthday = !(request.getBirthday().equals(""));
  
    	
    	//Add response
    	response.put("valid_email", String.valueOf(valid_mail));
    	response.put("valid_birthday", String.valueOf(valid_birthday));
    	response.put("taken_email", String.valueOf(taken_mail));
    	response.put("password_message", message_password);
    	
    	if (valid_mail && !taken_mail && valid_birthday && valid_birthday && (message_password=="successfull")) {
    		User user = new User();
    		user.setMail(request.getEmail());

    		String hashed_password = hash_password(request.getPassword());
    		
    		user.setPassword(hashed_password);
    		user.setBirthday(request.getBirthday());
    		//Save into SQL
    		databaseController.save(user);
    	}
    	
    	
    	//Respond to Front
    	 
    	 return ResponseEntity.ok(response);
    }
    
    
    @PostMapping("api/connect")
    public ResponseEntity<?> connect(@RequestBody LoginRequest request, HttpSession session) {
    	Map<String, String> response = new HashMap<>();

    	
    	System.out.println(request.getPassword());
    	
    	//Check if Mail is in Database
    	Boolean found_email = databaseController.existsByMail(request.getEmail());
    	response.put("valid_email",String.valueOf(found_email));
    	
    	if (found_email) {
    		Optional<User> user_opt = databaseController.findByMail(request.getEmail());
    		Boolean valid_account = user_opt.isPresent();
    		response.put("valid_account",String.valueOf(valid_account));
    		//If account is not a NULL value
    		if (valid_account) {
    			User user = user_opt.get();
    			//Hash Password
    			Boolean correct_password = is_hashed_password(user.getPassword(), request.getPassword());
    			response.put("valid_password", String.valueOf(correct_password));
    					
    			if (correct_password) {
    				response.put("login", "successfull");
    				session.setAttribute("user", user);
    			}
    		}
    		else {
    			System.out.println("Unknown User");
    		}
    	}
    	
    	
    	return ResponseEntity.ok(response);
    }
    

    @PostMapping("/api/profile")
    public ResponseEntity<?> get_Profile(@RequestBody ProfileRequest request) {
    	System.out.println("username");
    	System.out.println(request);
    	System.out.println(request.getName());
    	Map<String, String> response = new HashMap<>();
    	String username = request.getName();
    	Optional<User> user_opt = databaseController.findByName(username);
    	Boolean is_found = user_opt.isPresent();
    	response.put("is_found", String.valueOf(is_found));
    	response.put("username", null);
    	response.put("mail", null);
    	response.put("birthdate", null);
    	
    	if (is_found) {
    		User user = user_opt.get();
    		response.put("username", user.getName());
    		response.put("mail", user.getMail());
    		response.put("birthdate", user.getBirthday());
    	}
    	
    	return ResponseEntity.ok(response);
    }
    
}
