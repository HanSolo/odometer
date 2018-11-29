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

import eu.hansolo.fx.spinner.event.SpinnerEvent;
import eu.hansolo.fx.spinner.event.SpinnerObserver;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Color;


public interface Spinner {
    Color DEFAULT_BACKGROUND_COLOR = Color.web("#050505");
    Color DEFAULT_FOREGROUND_COLOR = Color.web("#f8f8f8");

    double getOldValue();

    double getValue();
    void setValue(final double VALUE);
    DoubleProperty valueProperty();

    void spinUp();
    void spinDown();

    Color getBackgroundColor();
    void setBackgroundColor(final Color COLOR);
    ObjectProperty<Color> backgroundColorProperty();

    Color getForegroundColor();
    void setForegroundColor(final Color COLOR);
    ObjectProperty<Color> foregroundColorProperty();

    SpinnerType getSpinnerType();
    void setSpinnerType(final SpinnerType SpinnerTYPE);

    boolean isShowing();

    void setOnValueChanged(final SpinnerObserver OBSERVER);
    void setOnZeroPassed(final SpinnerObserver OBSERVER);
    void setOnSpinnerEvent(final SpinnerObserver OBSERVER);
    void removeSpinnerObserver(final SpinnerObserver OBSERVER);
    void removeAllObservers();
    void fireSpinnerEvent(final SpinnerEvent EVT);
}
