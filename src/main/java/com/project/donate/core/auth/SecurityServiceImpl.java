package com.project.donate.core.auth;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final TokenProvider tokenProvider;

    private final Logger logger = Logger.getLogger(SecurityServiceImpl.class);

    @Autowired
    public SecurityServiceImpl(AuthenticationManager authenticationManager,
                               @Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                               TokenProvider tokenProvider) {

        Assert.notNull(authenticationManager, "AuthenticationManager should not be null");
        Assert.notNull(userDetailsService, "UserDetailsService should not be null");
        Assert.notNull(tokenProvider, "TokenProvider should not be null");

        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public String findLoggedInUsername() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userDetails.getUsername();
    }

    public UserDetails getCurrentLoggedUser() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public String loginUser(String username, String password) {

        logger.log(Level.INFO, "Login started...");

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (authenticate != null && authenticate.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.log(Level.INFO, "Login successful");
        }

        return tokenProvider.generateToken(authenticate);
    }


}
