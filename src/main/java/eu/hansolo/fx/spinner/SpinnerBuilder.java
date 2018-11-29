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

import eu.hansolo.fx.spinner.event.SpinnerObserver;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

import java.util.HashMap;


public class SpinnerBuilder<B extends SpinnerBuilder<B>> {
    private HashMap<String, Property> properties         = new HashMap<>();

    // ******************** Constructors **************************************
    protected SpinnerBuilder() {}


    // ******************** Methods *******************************************
    public static final SpinnerBuilder create() {
        return new SpinnerBuilder();
    }

    public final B value(final double VALUE) {
        properties.put("value", new SimpleDoubleProperty(VALUE));
        return (B)this;
    }

    public final B backgroundColor(final Color COLOR) {
        properties.put("backgroundColor", new SimpleObjectProperty<Color>(COLOR));
        return (B)this;
    }

    public final B foregroundColor(final Color COLOR) {
        properties.put("foregroundColor", new SimpleObjectProperty<>(COLOR));
        return (B)this;
    }

    public final B type(final SpinnerType SPINNER_TYPE) {
        properties.put("spinnerType", new SimpleObjectProperty(SPINNER_TYPE));
        return (B)this;
    }

    public final B onValueChanged(final SpinnerObserver OBSERVER) {
        properties.put("onValueChanged", new SimpleObjectProperty(OBSERVER));
        return (B)this;
    }

    public final B onZeroPassed(final SpinnerObserver OBSERVER) {
        properties.put("onZeroPassed", new SimpleObjectProperty<>(OBSERVER));
        return (B)this;
    }

    public final B onSpinnerEvent(final SpinnerObserver OBSERVER) {
        properties.put("onSpinnerEvent", new SimpleObjectProperty(OBSERVER));
        return (B)this;
    }

    private final void build(final Spinner SPINNER) {
        for (String key : properties.keySet()) {
            if ("value".equals(key)) {
                SPINNER.setValue(((DoubleProperty) properties.get(key)).get());
            } else if ("backgroundColor".equals(key)) {
                SPINNER.setBackgroundColor(((ObjectProperty<Color>) properties.get(key)).get());
            } else if ("foregroundColor".equals(key)) {
                SPINNER.setForegroundColor(((ObjectProperty<Color>) properties.get(key)).get());
            } else if ("spinnerType".equals(key)) {
                SPINNER.setSpinnerType(((ObjectProperty<SpinnerType>) properties.get(key)).get());
            } else if ("onValueChanged".equals(key)) {
                SPINNER.setOnValueChanged(((ObjectProperty<SpinnerObserver>) properties.get(key)).get());
            } else if ("onZeroPassed".equals(key)) {
                SPINNER.setOnZeroPassed(((ObjectProperty<SpinnerObserver>) properties.get(key)).get());
            } else if ("onSpinnerEvent".equals(key)) {
                SPINNER.setOnSpinnerEvent(((ObjectProperty<SpinnerObserver>) properties.get(key)).get());
            }
        }
    }

    public final ImageSpinner buildImageSpinner() {
        final ImageSpinner CONTROL = new ImageSpinner();
        build(CONTROL);
        return CONTROL;
    }
    public final CanvasSpinner buildCanvasSpinner() {
        final CanvasSpinner CONTROL = new CanvasSpinner();
        build(CONTROL);
        return CONTROL;
    }
}
