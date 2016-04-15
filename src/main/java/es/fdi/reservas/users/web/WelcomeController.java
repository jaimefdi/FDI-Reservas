package es.fdi.reservas.users.web;

import java.util.Set;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {
    @RequestMapping(value = "/welcome.html")
    protected String welcome() {

        Set<String> roles = AuthorityUtils
                .authorityListToSet(SecurityContextHolder.getContext()
                        .getAuthentication().getAuthorities());
        if (roles.contains("ROLE_ADMIN")) {
            return "redirect:/admin/administrar";
        }
        if (roles.contains("ROLE_SECRE")) {
            return "redirect:/secre/gestion-reservas/page/1";
        }
        return "redirect:/mis-reservas/page/1";
    }
}
