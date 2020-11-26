package com.pingidentity.oidclogin;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Controller for the home page.
 */
@Controller
public class HomeController {
	
	Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Value("${ping-config.profile-page}")
	private String profilePage;
	
    @GetMapping("/home")
    public String home(Model model, @AuthenticationPrincipal OidcUser principal) {
    	logger.debug("Starting home with user " + principal);
        if (principal != null) {
            model.addAttribute("profile", principal.getClaims());
            model.addAttribute("profileJson", toJson(principal.getClaims()));
            model.addAttribute("profilePage", profilePage);
        }
        return "home";
    }
    
    private String toJson(Map<String, Object> claims) {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(claims);
        } catch (JsonProcessingException jpe) {
            logger.error("Error parsing claims to JSON", jpe);
        }
        return "Error parsing claims to JSON.";
    }
}