package com.chalmers.group30.views;

/**
 * Mediator for the map view
 */
public class MapMediator implements Mediator {
    private final HasOpenableDrawer hasOpenableDrawer;

    // @Autowired
    public MapMediator(HasOpenableDrawer hasOpenableDrawer) {
        this.hasOpenableDrawer = hasOpenableDrawer;
    }

    @Override
    public void notify(String event) {
        if (event.equals("mapCalled")) {
            hasOpenableDrawer.openDrawer();
        }
    }
}
