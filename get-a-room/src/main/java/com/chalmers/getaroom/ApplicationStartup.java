package com.chalmers.getaroom;

import com.chalmers.getaroom.models.GetARoomFacadeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
/**
 * Used to initialize the application
 */
public class ApplicationStartup
        implements ApplicationListener<ApplicationReadyEvent> {

    private final Logger logger = Logger.getLogger(ApplicationStartup.class.getName());

    // Services to initialize on startup
    @Autowired
    public ApplicationStartup(GetARoomFacadeInterface getARoomFacadeInterface) {
    }

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        logger.info("Application ready");
    }
}