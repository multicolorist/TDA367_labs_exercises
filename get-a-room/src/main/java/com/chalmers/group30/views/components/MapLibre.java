package com.chalmers.group30.views.components;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.CssImport.Container;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;

import java.io.Serializable;
import java.util.ArrayList;

@Tag("maplibre-gl-js")
@JsModule("./maplibre/maplibre.js")
@StyleSheet("https://unpkg.com/maplibre-gl@2.4.0/dist/maplibre-gl.css")
public class MapLibre extends Component implements HasSize, HasStyle, HasComponents {

    public MapLibre() {
    }
}
