module hilichurl.mondstadtadvanture {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens hilichurl.mondstadtadvanture to javafx.fxml;
    exports hilichurl.mondstadtadvanture;
}