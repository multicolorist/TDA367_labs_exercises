package com.chalmers.group30.views;

import com.vaadin.flow.component.icon.Icon;

/**
 * Interface for components that can change their icon
 */
public interface HasChangeableIcon {
    /**
     * Sets the icon of the component
     * @param icon The icon to set
     */
    void setIcon(Icon icon);
}
