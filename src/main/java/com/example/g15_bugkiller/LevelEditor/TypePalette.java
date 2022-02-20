package com.example.g15_bugkiller.LevelEditor;

import com.example.g15_bugkiller.Field;
import com.example.g15_bugkiller.Gegenstand;
import com.example.g15_bugkiller.Type;
import com.example.g15_bugkiller.Values;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class TypePalette extends TilePane{

    private static FieldKachel selectedFieldKachel = new FieldKachel(40, 40, new Field(new Gegenstand(Type.WALL, new Values())));
    public static FieldKachel getSelectedKachel(){ return selectedFieldKachel; }

    public TypePalette(){
        drawPalette();
        setHgap(10);
        setVgap(10);
    }

    private void drawPalette(){
        for (Type type : drawableTypes) {
            FieldKachel typeFieldKachel = new FieldKachel(40, 40, new Field(new Gegenstand(type, new Values())));
            typeFieldKachel.setStroke(Color.BLACK);

            typeFieldKachel.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                setSelectedKachel(typeFieldKachel);
            });

            getChildren().add(typeFieldKachel);
        }
    }

    private void setSelectedKachel(FieldKachel typeFieldKachel){

        //normalState
        selectedFieldKachel.setStrokeWidth(2);
        selectedFieldKachel.setWidth(38);
        selectedFieldKachel.setHeight(38);

        selectedFieldKachel = typeFieldKachel;

        //selectedState
        selectedFieldKachel.setStrokeWidth(4);
        selectedFieldKachel.setHeight(36);
        selectedFieldKachel.setWidth(36);

        FieldSettings.updateFieldSettings();
    }

    private Type[] drawableTypes = {
            Type.ME,
            Type.GEM,
            Type.MUD,
            Type.STONE,
            Type.BRICKS,
            Type.PATH,
            Type.EXIT,
            Type.WALL,
            Type.EXPLOSION,
            Type.FIRE,
            Type.POT,
            Type.SIEVE,
            Type.SAND,
            Type.SLIME,
            Type.SWAPLING,
            Type.BLOCKLING,
            Type.XLING,
            Type.GHOSTLING,
            Type.NORTHTHING,
            Type.EASTTHING,
            Type.SOUTHTHING,
            Type.WESTTHING,
            Type.LOCH,
            Type.STOPBUTTON,
    };
}