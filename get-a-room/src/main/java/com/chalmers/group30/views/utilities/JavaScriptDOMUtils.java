package com.chalmers.group30.views.utilities;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.page.Page;

public class JavaScriptDOMUtils {
    /**
     * Set the attribute to the value for the given HTML tag. Note that this changes every tag.
     * To change a specific tag, use another method that targets for example id.
     *
     * @param tag   The HTML tag to change
     * @param attr  The attribute to change
     * @param value The value to set the attribute to
     */
    public static void setDOMTagAttribute(String tag, String attr, String value) {
        Page page = UI.getCurrent().getPage();
        String js = String.format("document.querySelector('%s').setAttribute('%s','%s')", tag, attr, value);
        page.executeJs(js);
    }
}
