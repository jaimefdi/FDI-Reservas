package es.fdi.reservas.users.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import es.fdi.reservas.users.business.boundary.UserService;
import es.fdi.reservas.users.business.entity.User;
import es.fdi.reservas.users.business.entity.UserRole;

@Controller
public class WelcomeController {
    

	private UserService user_service;
	
	@Autowired
	public WelcomeController(UserService us){
		user_service = us;
	}
	
	@RequestMapping(value = "/welcome")
    public String welcome() {

        User user = user_service.getCurrentUser();
        List<String> roles = user.getRoles();

        if (roles.contains("ROLE_ADMIN")) {
            return "redirect:/admin/administrar";
        }
        else if (roles.contains("ROLE_GESTOR")) {
            return "redirect:/gestor/gestion-reservas/page/1";
        }
        else{
        	return "redirect:/mis-reservas/page/1";
        }
    }
}
