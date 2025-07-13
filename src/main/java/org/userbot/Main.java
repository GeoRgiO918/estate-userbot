package org.userbot;


import java.io.IOException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {


        ApplicationInit.init();

        try {
            Thread.currentThread().join();
        } catch (Exception ignored) {

        }

    }
}



