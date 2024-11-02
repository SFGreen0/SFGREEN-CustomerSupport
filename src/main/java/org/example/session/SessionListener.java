package org.example.servlets;

import org.example.utils.SessionRegistry;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.*;

@WebListener
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        SessionRegistry.addSession(se.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        SessionRegistry.removeSession(se.getSession().getId());
    }
}