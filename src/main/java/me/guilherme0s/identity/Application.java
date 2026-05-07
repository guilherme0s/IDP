package me.guilherme0s.identity;

import io.micronaut.runtime.Micronaut;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class Application {

    static void main(String[] args) {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        Micronaut.run(Application.class, args);
    }
}
