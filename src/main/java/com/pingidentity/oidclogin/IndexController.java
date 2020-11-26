package com.pingidentity.oidclogin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for the index page.
 */
@Controller
public class IndexController {
	
	Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Value("${ping-config.profile-page}")
	private String profilePage;
	
    @GetMapping("/")
    public String index (Model model, @AuthenticationPrincipal OidcUser principal) {
    	logger.debug("Starting index with user " + principal);
        if (principal != null) {
            model.addAttribute("profile", principal.getClaims());
            model.addAttribute("profilePage", profilePage);
        }
        return "index";
    }
}