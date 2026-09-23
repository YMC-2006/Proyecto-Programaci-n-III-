module cr.ac.una.proyectoprogra {
    // JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.graphics;
    requires javafx.base;
    requires MaterialFX;
    
     requires java.logging;

    
    opens cr.ac.una.proyectoprogra to javafx.fxml, MaterialFX;
    opens cr.ac.una.proyectoprogra.controller to javafx.fxml, javafx.graphics, MaterialFX;
    opens cr.ac.una.proyectoprogra.model;
    exports cr.ac.una.proyectoprogra;
}
