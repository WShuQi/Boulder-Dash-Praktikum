package com.example.g15_bugkiller;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;

public class MouseScrollListener implements EventHandler<ScrollEvent> {

    double deltaY;

    @Override
    public void handle(ScrollEvent scrollEvent) {
        deltaY = scrollEvent.getDeltaY();
    }

}
