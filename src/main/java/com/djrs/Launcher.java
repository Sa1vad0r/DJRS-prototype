package com.djrs;

import com.djrs.view.gui.ThemePreview;
import javafx.application.Application;

/**
 * Run THIS class from IntelliJ (right-click > Run 'Launcher.main()').
 *
 * It deliberately does not extend Application. A main class that extends
 * Application makes the JVM demand JavaFX on the module path and fails with
 * "JavaFX runtime components are missing". Starting from a plain class lets
 * the Maven-provided JavaFX jars work from the classpath.
 *
 *   (no args)  -> the real app (MainApplication)
 *   preview    -> the theme demo (ThemePreview)
 *   cli        -> the old console flow
 */
public class Launcher {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("preview")) {
            Application.launch(ThemePreview.class, args);
        } else {
            MainApplication.main(args);
        }
    }
}
