package com.bb.AutomationUtils.learningComponentAndLinking;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersRestController {
	private UserDaoService daoService ;

	public UsersRestController(UserDaoService daoService) {
		this.daoService = daoService;
	}

	@GetMapping("/users/{name}")
	public Users getUser(@PathVariable String name) {
		return daoService.getUserDetails(name);
	}
	@GetMapping("/users")
	public List<Users> getUser() {
		return daoService.getUsers();
	}
}
