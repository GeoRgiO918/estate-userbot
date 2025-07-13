package org.userbot;

import java.io.IOException;

public class ApplicationInit {

    public static void init()  {
        try {
            LibrariesLoader.loadLibs();
            System.out.println("All libs loaded!");

            ConfigStore.initConfig();
            System.out.println("Config loaded!");

            TelegramClientInit.start();
            MessageThreadManager.initThread();
            FileThreadManager.initThread();
            System.out.println("All threads initialized!");
        } catch (Exception e) {
            // Логирование — по желанию
            System.err.println("Error while init: " + e.getMessage());
            throw new RuntimeException("Error while running application init()", e);
        }
    }

}
