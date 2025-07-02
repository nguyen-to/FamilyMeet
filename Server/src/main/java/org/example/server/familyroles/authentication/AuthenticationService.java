package org.example.server.familyroles.authentication;

import org.example.server.service.userservice.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {
   public CustomUserDetails getUserEntity(){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       if(authentication == null && !authentication.isAuthenticated()){
           throw new RuntimeException("Unauthenticated");
       }
       CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
       return userDetails;
   }
}
