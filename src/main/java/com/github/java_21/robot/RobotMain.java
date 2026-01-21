package com.github.java_21.robot;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class RobotMain {
    public static void main(String[] args) throws AWTException, InterruptedException {
        Robot robot = new Robot();

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        DisplayMode displayMode = gd.getDisplayMode();
        int screenWidth = displayMode.getWidth();
        int pos = 0;
        while (true) {
            pos = pos + 100;
            if (pos >= screenWidth) {
                pos = 0;
            }
            System.out.println("x-pos: " + pos);
            robot.mouseMove(pos, 100);
            TimeUnit.SECONDS.sleep(10);
        }
    }
}
