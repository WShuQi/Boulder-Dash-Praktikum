package com.example.g15_bugkiller.LevelEditor;

import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;

public class FieldSettings {

    static VBox fieldSettings = new VBox();


    public static void updateFieldSettings(){

        //field settings editor
        Label fieldSettingsLabel = new Label("Current Field: " + TypePalette.getSelectedKachel().field.getType().name());
        FieldKachel currentlySelectedKachel = new FieldKachel(50, 50, TypePalette.getSelectedKachel().field);

        //values
        Label valueMovedLabel = new Label("Moved");
        Spinner<Integer> valueMovedInput = new Spinner<>(0, 1, 0);
        VBox valueMovedBox = new VBox();
        valueMovedBox.getChildren().addAll(valueMovedLabel, valueMovedInput);

        Label valueFallingLabel = new Label("Falling");
        Spinner<Integer> valueFallingInput = new Spinner<>(0, 1, 0);
        VBox valueFallingBox = new VBox();
        valueFallingBox.getChildren().addAll(valueFallingLabel, valueFallingInput);

        Label valueLooseLabel = new Label("Loose");
        Spinner<Integer> valueLooseInput = new Spinner<>(0, 1, 0);
        VBox valueLooseBox = new VBox();
        valueLooseBox.getChildren().addAll(valueLooseLabel, valueLooseInput);

        Label valueSlipperyLabel = new Label("Slippery");
        Spinner<Integer> valueSlipperyInput = new Spinner<>(0, 1, 0);
        VBox valueSlipperyBox = new VBox();
        valueSlipperyBox.getChildren().addAll(valueSlipperyLabel, valueSlipperyInput);

        Label valuePushableLabel = new Label("Pushable");
        Spinner<Integer> valuePushableInput = new Spinner<>(0, 1, 0);
        VBox valuePushableBox = new VBox();
        valuePushableBox.getChildren().addAll(valuePushableLabel, valuePushableInput);

        Label valueBamLabel = new Label("Bam");
        Spinner<Integer> valueBamInput = new Spinner<>(0, 1, 0);
        VBox valueBamBox = new VBox();
        valueBamBox.getChildren().addAll(valueBamLabel, valueBamInput);

        Label valueBamrichLabel = new Label("Bamrich");
        Spinner<Integer> valueBamrichInput = new Spinner<>(0, 1, 0);
        VBox valueBamrichBox = new VBox();
        valueBamrichBox.getChildren().addAll(valueBamrichLabel, valueBamrichInput);

        Label valueDirectionLabel = new Label("Direction");
        Spinner<Integer> valueDirectionInput = new Spinner<>(0, 4, 0);
        VBox valueDirectionBox = new VBox();
        valueDirectionBox.getChildren().addAll(valueDirectionLabel, valueDirectionInput);

        Label valueALabel = new Label("A");
        Spinner<Integer> valueAInput = new Spinner<>(0, 100, 0);
        VBox valueABox = new VBox();
        valueABox.getChildren().addAll(valueALabel, valueAInput);

        Label valueBLabel = new Label("B");
        Spinner<Integer> valueBInput = new Spinner<>(0, 100, 0);
        VBox valueBBox = new VBox();
        valueBBox.getChildren().addAll(valueBLabel, valueBInput);

        Label valueCLabel = new Label("C");
        Spinner<Integer> valueCInput = new Spinner<>(0, 100, 0);
        VBox valueCBox = new VBox();
        valueCBox.getChildren().addAll(valueCLabel, valueCInput);

        Label valueDLabel = new Label("D");
        Spinner<Integer> valueDInput = new Spinner<>(0, 100, 0);
        VBox valueDBox = new VBox();
        valueDBox.getChildren().addAll(valueDLabel, valueDInput);

        Label valueXLabel = new Label("X");
        Spinner<Integer> valueXInput = new Spinner<>(0, 100, 0);
        VBox valueXBox = new VBox();
        valueXBox.getChildren().addAll(valueXLabel, valueXInput);

        Label valueYLabel = new Label("Y");
        Spinner<Integer> valueYInput = new Spinner<>(0, 100, 0);
        VBox valueYBox = new VBox();
        valueYBox.getChildren().addAll(valueYLabel, valueYInput);

        Label valueZLabel = new Label("Z");
        Spinner<Integer> valueZInput = new Spinner<>(0, 100, 0);
        VBox valueZBox = new VBox();
        valueZBox.getChildren().addAll(valueZLabel, valueZInput);

        Label valueGemsLabel = new Label("Gems");
        Spinner<Integer> valueGemsInput = new Spinner<>(0, 100, 0);
        VBox valueGemsBox = new VBox();
        valueGemsBox.getChildren().addAll(valueGemsLabel, valueGemsInput);

        Label valueTicksLabel = new Label("Ticks");
        Spinner<Integer> valueTicksInput = new Spinner<>(0, 100, 0);
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