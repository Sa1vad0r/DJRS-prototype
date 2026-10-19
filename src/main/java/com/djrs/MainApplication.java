package com.djrs;

import com.djrs.controller.LoginController;
import com.djrs.model.UserSession;
import com.djrs.view.LoginCli;
import com.djrs.view.MusicCli;

import java.io.File;
import java.util.Scanner;

public class MainApplication {
    public static void main(String[] args) {
        LoginController loginController = new LoginController();
        Scanner input = new Scanner(System.in);

        UserSession session = new LoginCli(loginController).run(input);

        new MusicCli(session).run(input);
    }
}
