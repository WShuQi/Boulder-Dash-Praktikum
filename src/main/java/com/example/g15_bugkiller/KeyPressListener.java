package com.example.g15_bugkiller;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyPressListener {

    boolean upPressed = false;
    boolean rightPressed = false;
    boolean downPressed = false;
    boolean leftPressed = false;
    boolean shiftPressed = false;

    public KeyPressListener(boolean upPressed, boolean rightPressed, boolean downPressed, boolean leftPressed, boolean shiftPressed) {
        this.upPressed = upPressed;
        this.rightPressed = rightPressed;
        this.downPressed = downPressed;
        this.leftPressed = leftPressed;
        this.shiftPressed = shiftPressed;
    }

    public KeyPressListener(){}

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isShiftPressed() {
        return shiftPressed ;
    }

    public boolean isMetaUpPressed() {
        return upPressed && shiftPressed;
    }

    public boolean isMetaRightPressed() {
        return rightPressed && shiftPressed;
    }

    public boolean isMetaDownPressed() {
        return downPressed && shiftPressed;
    }

    public boolean isMetaLeftPressed() {
        return leftPressed && shiftPressed;
    }

    public EventHandler<KeyEvent> keyPressed  = keyEvent-> {
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

        public EventHandler<KeyEvent> keyReleased = keyEvent -> {
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

        private void resetButtons(){
            upPressed = false;
            rightPressed = false;
            downPressed = false;
            leftPressed = false;
        }

        public KeyPressListener getClone(){
            return new KeyPressListener(this.upPressed, this.rightPressed, this.downPressed, this.leftPressed, this.shiftPressed);
        };


}
