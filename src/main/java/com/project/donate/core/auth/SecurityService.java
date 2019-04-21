package com.project.donate.core.auth;

import org.springframework.security.core.Authentication;

public interface SecurityService {
    String findLoggedInUsername();
    Authentication login(String username, String password);
}
