module hilichurl.mondstadtadvanture {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires javafx.media;
    requires jdk.compiler;
    requires annotations;

    opens hilichurl.mondstadtadvanture to javafx.fxml;
    exports hilichurl.mondstadtadvanture;
    exports hilichurl.mondstadtadvanture.enums;
    exports hilichurl.mondstadtadvanture.fxmlcontroller;
    opens hilichurl.mondstadtadvanture.fxmlcontroller to javafx.fxml;
    exports hilichurl.mondstadtadvanture.scenes;
    opens hilichurl.mondstadtadvanture.scenes to javafx.fxml;
    exports hilichurl.mondstadtadvanture.jsonpojo;
    opens hilichurl.mondstadtadvanture.jsonpojo to javafx.fxml;
    exports hilichurl.mondstadtadvanture.preload;
    opens hilichurl.mondstadtadvanture.preload to javafx.fxml;
    exports hilichurl.mondstadtadvanture.jsonpojo.plots;
    opens hilichurl.mondstadtadvanture.jsonpojo.plots to javafx.fxml;
    exports hilichurl.mondstadtadvanture.jsonpojo.spots;
    opens hilichurl.mondstadtadvanture.jsonpojo.spots to javafx.fxml;
}