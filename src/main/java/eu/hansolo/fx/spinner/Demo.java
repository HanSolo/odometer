/*
 * Copyright (c) 2018 by Gerrit Grunwald
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.hansolo.fx.spinner;

import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalTime;
import java.util.Locale;


/**
 * User: hansolo
 * Date: 26.11.18
 * Time: 15:29
 */
public class Demo extends Application {
    private CanvasSpinner  canvasSpinner0;
    private CanvasSpinner  canvasSpinner1;
    private CanvasSpinner  canvasSpinner2;
    private CanvasSpinner  canvasSpinner3;
    private CanvasSpinner  canvasSpinner4;
    private CanvasSpinner  canvasSpinner5;
    private CanvasSpinner  canvasSpinner6;

    private ImageSpinner   imageSpinner0;
    private ImageSpinner   imageSpinner1;
    private ImageSpinner   imageSpinner2;
    private ImageSpinner   imageSpinner3;
    private ImageSpinner   imageSpinner4;
    private ImageSpinner   imageSpinner5;
    private ImageSpinner   imageSpinner6;

    private ImageSpinner   hhLeft;
    private ImageSpinner   hhRight;
    private ImageSpinner   mmLeft;
    private ImageSpinner   mmRight;
    private ImageSpinner   ssLeft;
    private ImageSpinner   ssRight;

    private boolean        ssLeftShouldSpin;
    private boolean        mmRightShouldSpin;
    private boolean        mmLeftShouldSpin;
    private boolean        hhRightShouldSpin;
    private boolean        hhLeftShouldSpin;

    private char[]         characters;
    private char[]         javafx  = new char[] { 'J', 'A', 'V', 'A', ' ', 'F', 'X' };
    private char[]         hansolo = new char[] { 'H', 'A', 'N', 'S', 'O', 'L', 'O' };

    private long           lastTimerCall;
    private AnimationTimer timer;

    private Timeline       timeline;


    @Override public void init() {
        canvasSpinner0 = SpinnerBuilder.create().type(SpinnerType.ALPHABETIC).buildCanvasSpinner();
        canvasSpinner0 = SpinnerBuilder.create().type(SpinnerType.ALPHABETIC).buildCanvasSpinner();
        canvasSpinner1 = SpinnerBuilder.create().type(SpinnerType.ALPHABETIC).buildCanvasSpinner();
        canvasSpinner2 = SpinnerBuilder.create().type(SpinnerType.ALPHABETIC).buildCanvasSpinner();
        canvasSpinner3 = SpinnerBuilder.create().type(SpinnerType.ALPHABETIC).buildCanvasSpinner();
        canvasSpinner4 = SpinnerBuilder.create().type(SpinnerType.ALPHABETIC).buildCanvasSpinner();
        canvasSpinner5 = SpinnerBuilder.create().type(SpinnerType.ALPHABETIC).buildCanvasSpinner();
        canvasSpinner6 = SpinnerBuilder.create().type(SpinnerType.ALPHABETIC).buildCanvasSpinner();

        imageSpinner0  = new ImageSpinner(SpinnerType.ALPHABETIC);
        imageSpinner1  = new ImageSpinner(SpinnerType.ALPHABETIC);
        imageSpinner2  = new ImageSpinner(SpinnerType.ALPHABETIC);
        imageSpinner3  = new ImageSpinner(SpinnerType.ALPHABETIC);
        imageSpinner4  = new ImageSpinner(SpinnerType.ALPHABETIC);
        imageSpinner5  = new ImageSpinner(SpinnerType.ALPHABETIC);
        imageSpinner6  = new ImageSpinner(SpinnerType.ALPHABETIC);

        hhLeft  = new ImageSpinner(SpinnerType.NUMERIC_0_2);
        hhRight = new ImageSpinner(SpinnerType.NUMERIC_0_9);

        mmLeft  = new ImageSpinner(SpinnerType.NUMERIC_0_5);
        mmRight = new ImageSpinner(SpinnerType.NUMERIC_0_9);

        ssLeft  = new ImageSpinner(SpinnerType.NUMERIC_0_5);
        ssRight = new ImageSpinner(SpinnerType.NUMERIC_0_9);

        LocalTime time = LocalTime.now();
        String hh = String.format(Locale.US, "%02d", time.getHour());
        String mm = String.format(Locale.US, "%02d", time.getMinute());
        String ss = String.format(Locale.US, "%02d", time.getSecond());

        hhLeft.setValue(Double.parseDouble(hh.substring(0, 1)));
        hhRight.setValue(Double.parseDouble(hh.substring(1)));

        mmLeft.setValue(Double.parseDouble(mm.substring(0, 1)));
        mmRight.setValue(Double.parseDouble(mm.substring(1)));

        ssLeft.setValue(Double.parseDouble(ss.substring(0, 1)));
        ssRight.setValue(Double.parseDouble(ss.substring(1)));

        // Observers
        ssRight.setOnValueChanged(e -> {
            int value    = (int) e.getSource().getValue();
            int oldValue = (int) e.getSource().getOldValue();
            if (oldValue == 8 && value == 9) {
                ssLeftShouldSpin = true;
                if ((int) ssLeft.getValue() == 5) {
                    mmRightShouldSpin = true;
                    if ((int) mmRight.getValue() == 9) {
                        mmLeftShouldSpin = true;
                        if ((int) mmLeft.getValue() == 5) {
                            if ((int) hhRight.getValue() == 3) {
                                hhRight.setValue(0);
                                hhLeft.setValue(0);
                            } else {
                                hhRightShouldSpin = true;
                            }
                            if ((int) hhRight.getValue() == 9) {
                                hhLeftShouldSpin = true;
                            }
                        }
                    }
                }
            }
        });

        lastTimerCall = System.nanoTime();
        timer = new AnimationTimer() {
            @Override public void handle(final long now) {
                if (now > lastTimerCall + 1_000_000_000l) {
                    ssRight.spinUp();
                    if (ssLeftShouldSpin) {
                        ssLeft.spinUp();
                        ssLeftShouldSpin = false;
                    }
                    if (mmRightShouldSpin) {
                        mmRight.spinUp();
                        mmRightShouldSpin = false;
                    }
                    if (mmLeftShouldSpin) {
                        mmLeft.spinUp();
                        mmLeftShouldSpin = false;
                    }
                    if (hhRightShouldSpin) {
                        hhRight.spinUp();
                        hhRightShouldSpin = false;
                    }
                    if (hhLeftShouldSpin) {
                        hhLeft.spinUp();
                        hhLeftShouldSpin = false;
                    }
                    lastTimerCall = now;
                }
            }
        };

        timeline = new Timeline();

        characters = javafx;
        set(characters);

        timeline.setOnFinished(e -> {
            if (characters.equals(javafx)) {
                characters = hansolo;
            } else {
                characters = javafx;
            }
            set(characters);
            timeline.play();
        });
    }

    private void set(final char[] characters) {
        if (characters.length != 7) { throw new IllegalArgumentException("7 characters needed"); }

        int v0 = (int) characters[0] == 32 ? 27 : (int) characters[0] - 65;
        int v1 = (int) characters[1] == 32 ? 27 : (int) characters[1] - 65;
        int v2 = (int) characters[2] == 32 ? 27 : (int) characters[2] - 65;
        int v3 = (int) characters[3] == 32 ? 27 : (int) characters[3] - 65;
        int v4 = (int) characters[4] == 32 ? 27 : (int) characters[4] - 65;
        int v5 = (int) characters[5] == 32 ? 27 : (int) characters[5] - 65;
        int v6 = (int) characters[6] == 32 ? 27 : (int) characters[6] - 65;

        KeyValue kvSP0_C0 = new KeyValue(canvasSpinner0.valueProperty(), canvasSpinner0.getValue(), Interpolator.LINEAR);
        KeyValue kvSP0_C1 = new KeyValue(canvasSpinner0.valueProperty(), v0, Interpolator.LINEAR);
        KeyValue kvSP1_C0 = new KeyValue(canvasSpinner1.valueProperty(), canvasSpinner1.getValue(), Interpolator.LINEAR);
        KeyValue kvSP1_C1 = new KeyValue(canvasSpinner1.valueProperty(), v1, Interpolator.LINEAR);
        KeyValue kvSP2_C0 = new KeyValue(canvasSpinner2.valueProperty(), canvasSpinner2.getValue(), Interpolator.LINEAR);
        KeyValue kvSP2_C1 = new KeyValue(canvasSpinner2.valueProperty(), v2, Interpolator.LINEAR);
        KeyValue kvSP3_C0 = new KeyValue(canvasSpinner3.valueProperty(), canvasSpinner3.getValue(), Interpolator.LINEAR);
        KeyValue kvSP3_C1 = new KeyValue(canvasSpinner3.valueProperty(), v3, Interpolator.LINEAR);
        KeyValue kvSP4_C0 = new KeyValue(canvasSpinner4.valueProperty(), canvasSpinner4.getValue(), Interpolator.LINEAR);
        KeyValue kvSP4_C1 = new KeyValue(canvasSpinner4.valueProperty(), v4, Interpolator.LINEAR);
        KeyValue kvSP5_C0 = new KeyValue(canvasSpinner5.valueProperty(), canvasSpinner5.getValue(), Interpolator.LINEAR);
        KeyValue kvSP5_C1 = new KeyValue(canvasSpinner5.valueProperty(), v5, Interpolator.LINEAR);
        KeyValue kvSP6_C0 = new KeyValue(canvasSpinner6.valueProperty(), canvasSpinner6.getValue(), Interpolator.LINEAR);
        KeyValue kvSP6_C1 = new KeyValue(canvasSpinner6.valueProperty(), v6, Interpolator.LINEAR);

        KeyValue kvSP0_I0 = new KeyValue(imageSpinner0.valueProperty(), imageSpinner0.getValue(), Interpolator.LINEAR);
        KeyValue kvSP0_I1 = new KeyValue(imageSpinner0.valueProperty(), v0, Interpolator.LINEAR);
        KeyValue kvSP1_I0 = new KeyValue(imageSpinner1.valueProperty(), imageSpinner1.getValue(), Interpolator.LINEAR);
        KeyValue kvSP1_I1 = new KeyValue(imageSpinner1.valueProperty(), v1, Interpolator.LINEAR);
        KeyValue kvSP2_I0 = new KeyValue(imageSpinner2.valueProperty(), imageSpinner2.getValue(), Interpolator.LINEAR);
        KeyValue kvSP2_I1 = new KeyValue(imageSpinner2.valueProperty(), v2, Interpolator.LINEAR);
        KeyValue kvSP3_I0 = new KeyValue(imageSpinner3.valueProperty(), imageSpinner3.getValue(), Interpolator.LINEAR);
        KeyValue kvSP3_I1 = new KeyValue(imageSpinner3.valueProperty(), v3, Interpolator.LINEAR);
        KeyValue kvSP4_I0 = new KeyValue(imageSpinner4.valueProperty(), imageSpinner4.getValue(), Interpolator.LINEAR);
        KeyValue kvSP4_I1 = new KeyValue(imageSpinner4.valueProperty(), v4, Interpolator.LINEAR);
        KeyValue kvSP5_I0 = new KeyValue(imageSpinner5.valueProperty(), imageSpinner5.getValue(), Interpolator.LINEAR);
        KeyValue kvSP5_I1 = new KeyValue(imageSpinner5.valueProperty(), v5, Interpolator.LINEAR);
        KeyValue kvSP6_I0 = new KeyValue(imageSpinner6.valueProperty(), imageSpinner6.getValue(), Interpolator.LINEAR);
        KeyValue kvSP6_I1 = new KeyValue(imageSpinner6.valueProperty(), v6, Interpolator.LINEAR);

        KeyFrame kf0   = new KeyFrame(Duration.ZERO,
                                      kvSP0_C0, kvSP1_C0, kvSP2_C0, kvSP3_C0, kvSP4_C0, kvSP5_C0, kvSP6_C0,
                                      kvSP0_I0, kvSP1_I0, kvSP2_I0, kvSP3_I0, kvSP4_I0, kvSP5_I0, kvSP6_I0);
        KeyFrame kf1   = new KeyFrame(Duration.seconds(5),
                                      kvSP0_C1, kvSP1_C1, kvSP2_C1, kvSP3_C1, kvSP4_C1, kvSP5_C1, kvSP6_C1,
                                      kvSP0_I1, kvSP1_I1, kvSP2_I1, kvSP3_I1, kvSP4_I1, kvSP5_I1, kvSP6_I1);
        KeyFrame pause = new KeyFrame(Duration.seconds(8));

        timeline.getKeyFrames().setAll(kf0, kf1, pause);
    }

    @Override public void start(Stage stage) {
        //HBox canvasSpinners = new HBox(canvasSpinner0, canvasSpinner1, canvasSpinner2, canvasSpinner3, canvasSpinner4, canvasSpinner5, canvasSpinner6);
        HBox imageSpinners  = new HBox(imageSpinner0, imageSpinner1, imageSpinner2, imageSpinner3, imageSpinner4, imageSpinner5, imageSpinner6);

        HBox hhSpinners = new HBox(hhLeft, hhRight);
        HBox mmSpinners = new HBox(mmLeft, mmRight);
        HBox ssSpinners = new HBox(ssLeft, ssRight);

        HBox clockSpinners = new HBox(10, hhSpinners, mmSpinners, ssSpinners);
        clockSpinners.setAlignment(Pos.CENTER);

        VBox spinners = new VBox(20, /*canvasSpinners,*/ imageSpinners, clockSpinners);
        spinners.setPadding(new Insets(10));
        spinners.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(spinners);

        stage.setTitle("Spinner");
        stage.setScene(scene);
        stage.show();

        timer.start();
        timeline.play();
    }

    @Override public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}