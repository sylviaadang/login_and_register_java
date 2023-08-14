package com.sylviadang.loginandreg.controlles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sylviadang.loginandreg.models.LoginUser;
import com.sylviadang.loginandreg.models.User;
import com.sylviadang.loginandreg.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login/register")
	public String loginReg(@ModelAttribute("newUser") User user, @ModelAttribute("loginUser") LoginUser loginUser) {
		return "user/loginReg.jsp";
	}
	
	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("newUser") User user, BindingResult result, Model model, HttpSession session) {
		//reject value if email is taken
		// reject if pw dont match
		if (userService.getUser(user.getEmail()) != null) {
			result.rejectValue("email", "Unique", "Email already in use!");
		}
		if(!user.getPassword().equals(user.getConfirm())) {
			result.rejectValue("password", "Match", "Confirm password must match!");
		}
		
		if(result.hasErrors()) {
			model.addAttribute("loginUser", new LoginUser());
			return "user/loginReg.jsp";
		}
		User newUser = userService.registerUser(user);
		session.setAttribute("user_id", newUser.getId());
		return "redirect:/";
	}
	
	@PostMapping("/login")
	public String loginUser(@Valid @ModelAttribute("loginUser") LoginUser loginUser, BindingResult result, Model model, HttpSession session) {
		User loggingUser = userService.login(loginUser, result);
		if(result.hasErrors()) {
			model.addAttribute("newUser", new User());
			return "user/loginReg.jsp";
		}
		session.setAttribute("user_id", loggingUser.getId());
		return "redirect:/";
	}
}
