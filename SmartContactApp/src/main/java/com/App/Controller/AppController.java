package com.App.Controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.App.Dao.UserRepository;
import com.App.helper.MessageHelper;
import com.App.model.User;

@Controller
public class AppController {
	
	@Autowired
	private UserRepository userRepository; 
	
	@Autowired
	private BCryptPasswordEncoder encoder;
		
	@RequestMapping("/home")
	public String home(Model model) {
		model.addAttribute("title", "Home-Page");
		
		return"home";
	}
	
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About-Page");
		
		return"about";
	}
	
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Register");
		model.addAttribute("userObj", new User());
		
		return"signup";
	}
	
	@RequestMapping("/userDataHandler")
	public String userDataHandler(@Valid @ModelAttribute("userObj") User userObj,BindingResult result, @RequestParam(value =  "agreement" ,defaultValue = "false") boolean agreement, Model model,HttpSession session) {
	
				
				try {
					
					if(!agreement) {
						System.out.println("not checked");
						throw new Exception("box not checked");
					}
					
					if (result.hasErrors()) {
						System.out.println("**Errors : **"+result);
						model.addAttribute("userObj",userObj);
						return"signup";
					}
					
				model.addAttribute("title", "Register");
				model.addAttribute("userObj", userObj);
				model.addAttribute("agreement", agreement);
				userObj.setEnabled(true);
				userObj.setImageUrl("default.png");
				userObj.setPass(encoder.encode(userObj.getPass()));
				userObj.setRole("ROLE_USER");
				User userResultUser =  userRepository.save(userObj);
				model.addAttribute("userObj", new User());
				session.setAttribute("message", new MessageHelper("Successfully registred..!!","alert-success"));
				return"signup";
		
				} catch (Exception e) {
					model.addAttribute("userObj",userObj);
					
					session.setAttribute("message", new MessageHelper("something went wrong "+e.getMessage(),"alert-danger"));
					return"signup";
				}
		
		
		
	}
	
	@RequestMapping("/signin")
	public String CutomLogin(Model model) {
		model.addAttribute("title", "Login-Page");
		return"login";
	}
	
	@RequestMapping("/loginFail")
	public String LoginFail(Model model) {
		model.addAttribute("title", "Error!!!");
		return"loginFail";
	}
	
	
}
