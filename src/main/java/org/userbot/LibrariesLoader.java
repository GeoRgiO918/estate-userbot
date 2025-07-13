package org.userbot;


//Загружает tdjni + требуемые для работы либы
public class LibrariesLoader {

    public static void loadLibs(){
        // 1. Загружаем tdjni.dll
        System.loadLibrary("libcrypto-3-x64");
        System.loadLibrary("libssl-3-x64");
        System.loadLibrary("zlib1");
        System.loadLibrary("tdjni");
    }
}
