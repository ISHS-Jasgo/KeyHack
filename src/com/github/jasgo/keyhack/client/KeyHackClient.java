package com.github.jasgo.keyhack.client;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;

public class KeyHackClient implements NativeKeyListener {

    public static Socket socket;
    public static BufferedReader reader;
    public static PrintWriter writer;

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        GlobalScreen.addNativeKeyListener(new KeyHackClient());
        try {
            socket = new Socket("localhost", 3000);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
            System.out.println("서버에 연결되었습니다.");
            String line;
            while ((line = reader.readLine()) != null) {
                writeKey(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeEvent) {
        writer.println(NativeKeyEvent.getKeyText(nativeEvent.getKeyCode()));
    }
    public static void writeKey(String key1) {
        try {
            Robot robot = new Robot();
            String[] keys1 = key1.split(" ");
            int[] keycodes1 = new int[keys1.length];
            for (int i = 0; i < keys1.length; i++) {
                keycodes1[i] = KeyEvent.class.getDeclaredField("VK_" + keys1[i].toUpperCase()).getInt(int.class);
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
            timer.schedule(task1, 1000);
        } catch (AWTException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}