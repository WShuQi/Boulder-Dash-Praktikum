package com.example.g15_bugkiller.LevelEditor;

import com.example.g15_bugkiller.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class WritableFieldKachel extends FieldKachel {

    public WritableFieldKachel(){
        super (30, 30, new Field(new Gegenstand(Type.PATH, new Values())));
        setStroke(Color.WHITE);

        addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if(mouseEvent.getButton().toString() == "PRIMARY"){
                setField(FieldSettings.getField());
            }
        });
    }

    public void setField(Field field){
        this.field = field;
        this.setFill(new ImagePattern(PictureRepo.getImage(field.getType().name())));
    }
}