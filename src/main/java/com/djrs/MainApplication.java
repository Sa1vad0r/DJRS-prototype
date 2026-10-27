package com.djrs;

import com.djrs.model.UserSession;
import com.djrs.view.cli.DashboardCli;
import com.djrs.view.cli.LoginCli;
import com.djrs.view.cli.menuoption.CreateEditCli;
import com.djrs.controller.CreateEditController.PageState;

public class MainApplication {
    public static void main(String[] args) {
        // Sample flow of main
        // UserSession is a singleton - everyone shares the same instance via getInstance()
        UserSession currentSession = UserSession.getInstance();
        LoginCli userLogin = new LoginCli(currentSession);

        boolean loggedIn = userLogin.runLoginPrompt();

        if (!loggedIn) {
            System.out.println("[System] Exiting.");
            return;
        }

        // Added this for test purposes; Uncomment/Remove after
        CreateEditCli createEdit = new CreateEditCli(currentSession, PageState.CREATE);
        createEdit.run();

        // Now that the session exists, we can load the main dashboard scene
        DashboardCli dashboard = new DashboardCli(currentSession);
        dashboard.setSession(currentSession);
        dashboard.run();
    }
}
