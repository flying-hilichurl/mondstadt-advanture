module hilichurl.mondstadtadvanture {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    opens hilichurl.mondstadtadvanture to javafx.fxml;
    exports hilichurl.mondstadtadvanture;
    exports hilichurl.mondstadtadvanture.enums;
    exports hilichurl.mondstadtadvanture.fxmlcontroller;
    opens hilichurl.mondstadtadvanture.fxmlcontroller to javafx.fxml;
    exports hilichurl.mondstadtadvanture.scenes;
    opens hilichurl.mondstadtadvanture.scenes to javafx.fxml;
    exports hilichurl.mondstadtadvanture.json;
    opens hilichurl.mondstadtadvanture.json to javafx.fxml;
    exports hilichurl.mondstadtadvanture.preload;
    opens hilichurl.mondstadtadvanture.preload to javafx.fxml;
}