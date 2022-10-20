package com.chalmers.getaroom;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The entry point of the Spring Boot application.
 * <p>
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@SpringBootApplication
@NpmPackage(value = "@fontsource/inter", version = "4.5.0")
@Theme(value = "getaroom", variant = Lumo.LIGHT)
@PWA(name = "Get A Room", shortName = "Get A Room", offlineResources = {})
@Push
@NpmPackage(value = "line-awesome", version = "1.3.0")
@EnableScheduling
public class Application implements AppShellConfigurator {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
