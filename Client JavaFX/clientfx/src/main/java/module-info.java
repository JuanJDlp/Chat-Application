// I dont understand this module thing in the javaFX but when you have a
// dependency it has to go in here
// so when you compile and run evrything loads correctkly
module com.arkjj {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires spring.websocket;
    requires spring.boot.starter.websocket;
    requires spring.messaging;
    requires spring.web;
    requires spring.boot.starter;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.context;
    requires spring.core;
    requires com.google.gson;
    requires java.net.http;
    requires lombok;

    opens com.arkjj to javafx.fxml;

    exports com.arkjj;
    exports com.arkjj.model;
}
