package com.chalmers.group30.views.components.displays;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

/**
 * A dialog for displaying information about the site
 */
@Component
@UIScope
public class AboutDialog extends Dialog {
    public AboutDialog() {
        this.getElement().setAttribute("aria-label", "About this site");
        this.setHeaderTitle("About");
        this.add(
                new Paragraph("GetARoom was developed by Space, Goose & Matcha."),
                new Span("When you have found your room, press the booking button "),
                new Span(new Icon(VaadinIcon.CALENDAR_CLOCK)),
                new Span(" to go to TimeEdit."));

        Button closeButton = new Button(new Icon("lumo", "cross"),
                (e) -> this.close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.getHeader().add(closeButton);
    }
}