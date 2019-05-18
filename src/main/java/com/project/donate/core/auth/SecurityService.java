package com.project.donate.core.auth;


public interface SecurityService {
    String findLoggedInUsername();
    String loginUser(String username, String password);
}
