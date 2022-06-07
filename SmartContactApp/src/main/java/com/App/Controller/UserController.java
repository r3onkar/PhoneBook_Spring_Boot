package com.App.Controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.App.Dao.ContactRepository;
import com.App.Dao.UserRepository;
import com.App.helper.MessageHelper;
import com.App.model.Contact;
import com.App.model.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
	@ModelAttribute
	public void AddCommonData(Model model,Principal principal,HttpSession session) {
		String usernameString = principal.getName();
		User user = userRepository.getUserByUserName(usernameString);
		model.addAttribute("user", user);
	}
	
	@RequestMapping("/disp")
	public String dispPage(Model model,Principal principal) {
		String usernameString = principal.getName();
		User user = userRepository.getUserByUserName(usernameString);
		model.addAttribute("user", user);
		return"normal/USER_DISP";
	}
	
	@RequestMapping("/addcontact")
	public String addController(Model model) {
		model.addAttribute("title", "Add-Contact");
		model.addAttribute("contact", new Contact());
		return"normal/addcontact";
	}
	
	@PostMapping("/procressContact")
	public String procressContact(@ModelAttribute Contact contact,@RequestParam("img") MultipartFile imgFile,
			Model model,Principal principal,HttpSession session) {
		
	
		try {
			
		String usernameString = principal.getName();
		User user = userRepository.getUserByUserName(usernameString);
		contact.setUser(user);
		user.getContacts().add(contact);
	
		//uploading and processing file 
		
		if (imgFile.isEmpty()) {
			System.out.println("file is empty");
			contact.setPhoto("null.png");
		}else {
	
			contact.setPhoto(imgFile.getOriginalFilename());
			File file = new ClassPathResource("static/imgs").getFile();
			Path path = Paths.get(file.getAbsolutePath()+File.separator+imgFile.getOriginalFilename());
			Files.copy(imgFile.getInputStream(),path , StandardCopyOption.REPLACE_EXISTING);
		}
		
		User save = this.userRepository.save(user);
		System.out.println("Added to contact..");
		System.out.println(save);
		System.out.println(contact);
		
		model.addAttribute("contacts", contact);
		
		session.setAttribute("msg1", new MessageHelper("Added successfully","success"));
		
		}
			catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("msg1", new MessageHelper("someting went wrong","danger"));
			}
		return"normal/addcontact";
		}
	
	@RequestMapping("/ViewContact/{page}")
	public String ViewContact(@PathVariable("page") Integer page,Model model,Principal principal) {
		model.addAttribute("title", "View Contacts");
		Pageable pageRequest = PageRequest.of(page,5);
		String name = principal.getName();
		User user = this.userRepository.getUserByUserName(name);
		Page<Contact> contactsByUser = this.contactRepository.findContactsByUser(user.getId(),pageRequest);
		model.addAttribute("AllContacts", contactsByUser);
		model.addAttribute("CurrentPage", page);
		model.addAttribute("totalPages", contactsByUser.getTotalPages());
		
		return"normal/ViewContact";
	}
	
	@RequestMapping("/contact/{cid}")
	public String Contact(@PathVariable("cid") Integer cid,Model model,Principal principal,HttpSession session) {
		model.addAttribute("title","Contact info");
		Optional<com.App.model.Contact> contactopOptional = this.contactRepository.findById(cid);
		com.App.model.Contact contact = contactopOptional.get();
		
		
		
		User userByUserName = userRepository.getUserByUserName(principal.getName());
		if (userByUserName.getId()==contact.getUser().getId()) {
			model.addAttribute("contact", contact);
			return"normal/contact";
		}else {
			session.setAttribute("msg1", new MessageHelper("you Dont have access to view contact","danger"));
			model.addAttribute("contact", new Contact());
			return"normal/contact";
		}
		
		
	}
	
	@RequestMapping("/deleteContact/{id}")
	public String deleteContact(@PathVariable ("id") int id,Model model,HttpSession session,Principal principal) {
		
		Optional<com.App.model.Contact> contactopOptional = this.contactRepository.findById(id);
		com.App.model.Contact contact = contactopOptional.get();
		
		User userByUserName = userRepository.getUserByUserName(principal.getName());
		if (userByUserName.getId()==contact.getUser().getId()) {
//			contact.setUser(null);
			userByUserName.getContacts().remove(contact);
			this.contactRepository.delete(contact);
			session.setAttribute("msg1", new MessageHelper("successfully deleted..","success"));
			return"redirect:/user/ViewContact/0";
		}else {
			session.setAttribute("msg1", new MessageHelper("unauthorized selection","danger"));
			return"redirect:/user/ViewContact/0";
		}
	}
	
	@PostMapping("/update/{id}")
	public String UpdateForm(@PathVariable("id") Integer idInteger, Model model ) {
		model.addAttribute("title","update");
		Contact contact = this.contactRepository.findById(idInteger).get();
		model.addAttribute("contact", contact);
		System.out.println("update method one :"+ contact);
		return"normal/update";
	}
	
	@PostMapping("/finalUpdate")
	public String finalUpdate(@ModelAttribute Contact contact,Model model,HttpSession session,Principal principal) {
			
		User user = this.userRepository.getUserByUserName(principal.getName());
		contact.setUser(user);
		System.out.println("update method two :" +  contact);
		try {
			this.contactRepository.save(contact);
			session.setAttribute("msg1", new MessageHelper("successfully Updated..","success"));
			model.addAttribute("contact", contact);
			
		} catch (Exception e) {
			session.setAttribute("msg1", new MessageHelper("Something went Wrong","danger"));
			model.addAttribute("contact", contact);
			e.printStackTrace();
		}

		return"normal/update";
	}
	
	
}
