package com.github.jasgo.keyhack;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

public class KeyWrite {
    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String s;
            String[] keys1 = reader.readLine().split(" ");
            String[] keys2 = reader.readLine().split(" ");
            int[] keycodes1 = new int[keys1.length];
            for (int i = 0; i < keys1.length; i++) {
                keycodes1[i] = KeyEvent.class.getDeclaredField("VK_" + keys1[i].toUpperCase()).getInt(int.class);
            }
            int[] keycodes2 = new int[keys2.length];
            for (int i = 0; i < keys2.length; i++) {
                keycodes2[i] = KeyEvent.class.getDeclaredField("VK_" + keys2[i].toUpperCase()).getInt(int.class);
            }
            Timer timer = new Timer();
            TimerTask task1 = new TimerTask() {
                @Override
                public void run() {
                    for (int keycode : keycodes1) {
                        robot.keyPress(keycode);
                    }
                    for (int keycode : keycodes1) {
                        robot.keyRelease(keycode);
                    }
                }
            };
            TimerTask task2 = new TimerTask() {
                @Override
                public void run() {
                    for (int keycode : keycodes2) {
                        robot.keyPress(keycode);
                    }
                    for (int keycode : keycodes2) {
                        robot.keyRelease(keycode);
                    }
                    timer.cancel();
                }
            };
            timer.schedule(task1, 3000);
            timer.schedule(task2, 3500);
        } catch (AWTException|IOException|NoSuchFieldException|IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
