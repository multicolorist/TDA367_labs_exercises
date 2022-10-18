package com.chalmers.group30.views.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.page.PendingJavaScriptResult;
import com.vaadin.flow.spring.annotation.UIScope;

/**
 * A custom component that is used to get the client's browser's preferred theme. Needed to execute JavaScript on the client side
 */
@Tag("preferred-client-theme")
@org.springframework.stereotype.Component
@UIScope
public class PreferredClientThemeComponent extends Component {
    /**
     * Gets the JavaScript result of if the client's browser prefers dark mode
     *
     * @return A JavaScript result indication if the client's browser prefers dark mode
     */
    public PendingJavaScriptResult getClientPrefersDarkModeJavaScriptResult() {
        return this.getElement().executeJs("return window.matchMedia(\"(prefers-color-scheme: dark)\").matches;");
    }
}
