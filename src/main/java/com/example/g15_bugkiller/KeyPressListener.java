package com.example.g15_bugkiller;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class KeyPressListener {

    static boolean upPressed;
    static boolean rightPressed;
    static boolean downPressed;
    static boolean leftPressed;
    static boolean shiftPressed;

    public static boolean isUpPressed() {
        return upPressed;
    }

    public static boolean isRightPressed() {
        return rightPressed;
    }

    public static boolean isDownPressed() {
        return downPressed;
    }

    public static boolean isLeftPressed() {
        return leftPressed;
    }

    public static boolean isShiftPressed() {
        return shiftPressed ;
    }

    public static boolean isMetaUpPressed() {
        return upPressed && shiftPressed;
    }

    public static boolean isMetaRightPressed() {
        return rightPressed && shiftPressed;
    }

    public static boolean isMetaDownPressed() {
        return downPressed && shiftPressed;
    }

    public static boolean isMetaLeftPressed() {
        return leftPressed && shiftPressed;
    }

    public static EventHandler<KeyEvent> keyPressed  = keyEvent-> {
        System.out.println("pressed Key: " + keyEvent.getCode());
            switch (keyEvent.getCode()){
                case UP:
                    resetButtons();
                    upPressed = true;
                    break;
                case RIGHT:
                    resetButtons();
                    rightPressed = true;
                    break;
                case DOWN:
                    resetButtons();
                    downPressed = true;
                    break;
                case LEFT:
                    resetButtons();
                    leftPressed = true;
                    break;
                case SHIFT:
                    shiftPressed = true;
                    break;
            }
        };

        public static EventHandler<KeyEvent> keyReleased = keyEvent -> {
            System.out.println("released Key: " + keyEvent.getCode());
                switch (keyEvent.getCode()){
                    case UP:
                        upPressed = false;
                        break;
                    case RIGHT:
                        rightPressed = false;
                        break;
                    case DOWN:
                        downPressed = false;
                        break;
                    case LEFT:
                        leftPressed = false;
                        break;
                    case SHIFT:
                        shiftPressed = false;
                        break;
                }
        };

        private static void resetButtons(){
            upPressed = false;
            rightPressed = false;
            downPressed = false;
            leftPressed = false;
        }
}
