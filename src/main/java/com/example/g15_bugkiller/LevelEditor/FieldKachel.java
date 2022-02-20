package com.example.g15_bugkiller.LevelEditor;

import com.example.g15_bugkiller.Field;
import com.example.g15_bugkiller.PictureRepo;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class FieldKachel extends Rectangle {
    Field field;

    public FieldKachel(double x, double y, Field field) {
        super(x, y);
        this.field = field;
        this.setFill(new ImagePattern(PictureRepo.getImage(field.getType().name())));
    }

    public Field getField() { return field; }
}