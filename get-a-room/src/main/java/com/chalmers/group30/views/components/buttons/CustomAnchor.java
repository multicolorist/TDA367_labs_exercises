package com.chalmers.group30.views.components.buttons;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

/**
  * A custom anchor that looks like a button
  * Adapted from:
  * https://github.com/vaadin/web-components/issues/1803#issuecomment-467493463
  */
public class CustomAnchor extends Composite<Anchor> implements HasTheme, HasStyle {
    /** Custom-Attribut, used to identify the `vaadin-button` as child of `CustomAnchor` */
    private static final String ATTR_ANCHOR = "anchor";

    private Button btn;

    public CustomAnchor() {
        super();
        btn = new Button();
        // btn.setSizeFull();
        btn.getElement().setAttribute(ATTR_ANCHOR, "");
    }
    public CustomAnchor(String href, String text, Icon icon, String customClassName) {
        this();
        setHref(href);
        setText(text);
        btn.setIcon(new Icon(VaadinIcon.CALENDAR_CLOCK));
        if (customClassName != null && !customClassName.isEmpty()) {
            btn.addClassName(customClassName);
        }
    }
    // ===============================
    // Other constructors, matching the ones in Anchor
    // ===============================
    protected Anchor initContent() {
        Anchor a = super.initContent();
        a.getElement().setAttribute("target", "_blank"); // open in new tab
        a.add(btn);
        return a;
    }
    public void setHref(String href) {
        getContent().setHref(href);
    }
    public void setText(String text) {
        btn.setText(text);
    }

    // Override methods from HasStyle and HasTheme
    // Only methods calling "getElement" have to be overridden

    public void setThemeName(String themeName) {
        btn.setThemeName(themeName);
    }
}