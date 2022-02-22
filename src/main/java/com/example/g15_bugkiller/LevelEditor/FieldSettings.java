package com.example.g15_bugkiller.LevelEditor;

import com.example.g15_bugkiller.Field;
import com.example.g15_bugkiller.Gegenstand;
import com.example.g15_bugkiller.Values;
import com.example.g15_bugkiller.ValuesNames;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;

public class FieldSettings {

    static VBox fieldSettings = new VBox();

    static Spinner<Integer> valueMovedInput;
    static Spinner<Integer> valueFallingInput;
    static Spinner<Integer> valueLooseInput;
    static Spinner<Integer> valueSlipperyInput;
    static Spinner<Integer> valuePushableInput;
    static Spinner<Integer> valueBamInput;
    static Spinner<Integer> valueBamrichInput;
    static Spinner<Integer> valueDirectionInput;
    static Spinner<Integer> valueAInput;
    static Spinner<Integer> valueBInput;
    static Spinner<Integer> valueCInput;
    static Spinner<Integer> valueDInput;
    static Spinner<Integer> valueXInput;
    static Spinner<Integer> valueYInput;
    static Spinner<Integer> valueZInput;
    static Spinner<Integer> valueGemsInput;
    static Spinner<Integer> valueTicksInput;


    public static Field getField(){
        Values values = new Values();

        if(valueMovedInput.getValue() != 0) values.setSpecificValue(ValuesNames.MOVED, valueMovedInput.getValue());
        if(valueFallingInput.getValue() != 0) values.setSpecificValue(ValuesNames.FALLING, valueFallingInput.getValue());
        if(valueLooseInput.getValue() != 0) values.setSpecificValue(ValuesNames.LOOSE, valueLooseInput.getValue());
        if(valueSlipperyInput.getValue() != 0) values.setSpecificValue(ValuesNames.SLIPPERY, valueSlipperyInput.getValue());
        if(valuePushableInput.getValue() != 0) values.setSpecificValue(ValuesNames.PUSHABLE, valuePushableInput.getValue());
        if(valueBamInput.getValue() != 0) values.setSpecificValue(ValuesNames.BAM, valueBamInput.getValue());
        if(valueBamrichInput.getValue() != 0) values.setSpecificValue(ValuesNames.BAMRICH, valueBamrichInput.getValue());
        if(valueDirectionInput.getValue() != 0) values.setSpecificValue(ValuesNames.DIRECTION, valueDirectionInput.getValue());
        if(valueAInput.getValue() != 0) values.setSpecificValue(ValuesNames.A, valueAInput.getValue());
        if(valueBInput.getValue() != 0) values.setSpecificValue(ValuesNames.B, valueBInput.getValue());
        if(valueCInput.getValue() != 0) values.setSpecificValue(ValuesNames.C, valueCInput.getValue());
        if(valueDInput.getValue() != 0) values.setSpecificValue(ValuesNames.D, valueDInput.getValue());
        if(valueXInput.getValue() != 0) values.setSpecificValue(ValuesNames.X, valueXInput.getValue());
        if(valueYInput.getValue() != 0) values.setSpecificValue(ValuesNames.Y, valueYInput.getValue());
        if(valueZInput.getValue() != 0) values.setSpecificValue(ValuesNames.Z, valueZInput.getValue());
        if(valueGemsInput.getValue() != 0) values.setSpecificValue(ValuesNames.GEMS, valueGemsInput.getValue());
        if(valueTicksInput.getValue() != 0) values.setSpecificValue(ValuesNames.TICKS, valueTicksInput.getValue());

        Gegenstand gegenstand = new Gegenstand(TypePalette.getSelectedKachel().field.getType(), values);
        return new Field(gegenstand);
    }

    public static void updateFieldSettings(){

        //field settings editor
        Label fieldSettingsLabel = new Label("Current Field: " + TypePalette.getSelectedKachel().field.getType().name());
        FieldKachel currentlySelectedKachel = new FieldKachel(50, 50, TypePalette.getSelectedKachel().field);

        //values
        Label valueMovedLabel = new Label("Moved");
        valueMovedInput = new Spinner<>(0, 1, 0);
        VBox valueMovedBox = new VBox();
        valueMovedBox.getChildren().addAll(valueMovedLabel, valueMovedInput);

        Label valueFallingLabel = new Label("Falling");
        valueFallingInput = new Spinner<>(0, 1, 0);
        VBox valueFallingBox = new VBox();
        valueFallingBox.getChildren().addAll(valueFallingLabel, valueFallingInput);

        Label valueLooseLabel = new Label("Loose");
        valueLooseInput = new Spinner<>(0, 1, 0);
        VBox valueLooseBox = new VBox();
        valueLooseBox.getChildren().addAll(valueLooseLabel, valueLooseInput);

        Label valueSlipperyLabel = new Label("Slippery");
        valueSlipperyInput = new Spinner<>(0, 1, 0);
        VBox valueSlipperyBox = new VBox();
        valueSlipperyBox.getChildren().addAll(valueSlipperyLabel, valueSlipperyInput);

        Label valuePushableLabel = new Label("Pushable");
        valuePushableInput = new Spinner<>(0, 1, 0);
        VBox valuePushableBox = new VBox();
        valuePushableBox.getChildren().addAll(valuePushableLabel, valuePushableInput);

        Label valueBamLabel = new Label("Bam");
        valueBamInput = new Spinner<>(0, 1, 0);
        VBox valueBamBox = new VBox();
        valueBamBox.getChildren().addAll(valueBamLabel, valueBamInput);

        Label valueBamrichLabel = new Label("Bamrich");
        valueBamrichInput = new Spinner<>(0, 1, 0);
        VBox valueBamrichBox = new VBox();
        valueBamrichBox.getChildren().addAll(valueBamrichLabel, valueBamrichInput);

        Label valueDirectionLabel = new Label("Direction");
        valueDirectionInput = new Spinner<>(0, 4, 0);
        VBox valueDirectionBox = new VBox();
        valueDirectionBox.getChildren().addAll(valueDirectionLabel, valueDirectionInput);

        Label valueALabel = new Label("A");
        valueAInput = new Spinner<>(0, 100, 0);
        VBox valueABox = new VBox();
        valueABox.getChildren().addAll(valueALabel, valueAInput);

        Label valueBLabel = new Label("B");
        valueBInput = new Spinner<>(0, 100, 0);
        VBox valueBBox = new VBox();
        valueBBox.getChildren().addAll(valueBLabel, valueBInput);

        Label valueCLabel = new Label("C");
        valueCInput = new Spinner<>(0, 100, 0);
        VBox valueCBox = new VBox();
        valueCBox.getChildren().addAll(valueCLabel, valueCInput);

        Label valueDLabel = new Label("D");
        valueDInput = new Spinner<>(0, 100, 0);
        VBox valueDBox = new VBox();
        valueDBox.getChildren().addAll(valueDLabel, valueDInput);

        Label valueXLabel = new Label("X");
        valueXInput = new Spinner<>(0, 100, 0);
        VBox valueXBox = new VBox();
        valueXBox.getChildren().addAll(valueXLabel, valueXInput);

        Label valueYLabel = new Label("Y");
        valueYInput = new Spinner<>(0, 100, 0);
        VBox valueYBox = new VBox();
        valueYBox.getChildren().addAll(valueYLabel, valueYInput);

        Label valueZLabel = new Label("Z");
        valueZInput = new Spinner<>(0, 100, 0);
        VBox valueZBox = new VBox();
        valueZBox.getChildren().addAll(valueZLabel, valueZInput);

        Label valueGemsLabel = new Label("Gems");
        valueGemsInput = new Spinner<>(0, 100, 0);
        VBox valueGemsBox = new VBox();
        valueGemsBox.getChildren().addAll(valueGemsLabel, valueGemsInput);

        Label valueTicksLabel = new Label("Ticks");
        valueTicksInput = new Spinner<>(0, 100, 0);
        VBox valueTicksBox = new VBox();
        valueTicksBox.getChildren().addAll(valueTicksLabel, valueTicksInput);

        fieldSettings.getChildren().clear();
        fieldSettings.getChildren().addAll(fieldSettingsLabel, currentlySelectedKachel, valueMovedBox, valueFallingBox,
                valueLooseBox, valueSlipperyBox, valuePushableBox, valueBamBox, valueBamrichBox, valueDirectionBox,
                valueABox, valueBBox, valueCBox, valueDBox, valueXBox, valueYBox, valueZBox, valueGemsBox, valueTicksBox);
    }

    public static VBox getFieldSettingsVBox(){
        updateFieldSettings();
        return fieldSettings;
    }
}