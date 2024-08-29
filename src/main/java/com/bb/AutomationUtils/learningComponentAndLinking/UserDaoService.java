package com.bb.AutomationUtils.learningComponentAndLinking;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {

	private static List<Users> userData = new ArrayList<>();
	
	static {
		userData.add(new Users("Ankur", "Bellandur", 30));
		userData.add(new Users("DJ", "Sarjapur", 26));
		userData.add(new Users("AJ", "Bellandur", 45));
	}

	public Users getUserDetails(String name) {
		Predicate<? super Users> predicate =  users -> users.getName().equalsIgnoreCase(name) ;
		return userData.stream().filter(predicate).findFirst().get();

	}
	
	public List<Users> getUsers(){
		return userData;
	}
}
