package com.djrs;

import com.djrs.controller.LoginController;
import com.djrs.model.UserSession;
import com.djrs.view.cli.DashboardCli;
import com.djrs.view.cli.LoginCli;

public class MainApplication {
    public static void main(String[] args) {
        // Sample flow of main
        // Uses state machine to pass down UserSession
        UserSession currentSession = null;
        LoginCli userLogin = new LoginCli(currentSession);

        currentSession = userLogin.runLoginPrompt();

        if (currentSession == null) {
            if (currentSession.getCurrentUser() != null) {
                System.out.println("[System] Failed to initialize user session. Exiting.");
            }
            else {
                System.out.println("[System] Exiting.");
            }
            return;
        }

        // Now that the session exists, we can load the main dashboard scene
        DashboardCli dashboard = new DashboardCli(currentSession);
        dashboard.setSession(currentSession);
        dashboard.run();
    }
}
