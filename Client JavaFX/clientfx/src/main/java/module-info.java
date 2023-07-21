module com.arkjj {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive spring.websocket;
    requires transitive spring.boot.starter.websocket;
    requires transitive spring.messaging;
    requires transitive spring.web;
    requires transitive spring.boot.starter;
    requires transitive spring.boot.autoconfigure;
    requires transitive spring.boot;
    requires spring.context;
    requires spring.core;

    opens com.arkjj to javafx.fxml;

    exports com.arkjj;
    exports com.arkjj.model;
}
