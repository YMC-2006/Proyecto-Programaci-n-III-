/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cr.ac.una.proyectoprogra.util;

import java.util.Locale;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 *
 * @author vjjcu
 */
public class ToastNotification {
 
    private static final double MARGEN_DERECHO = 20;
    private static final double MARGEN_INFERIOR = 20;
    private static final double ALTO_TOAST = 60;
    private static final double ESPACIADO = 10;
 
    public enum ToastType { EXITO, ERROR, INFO, ADVERTENCIA }
 
    public static void mostrar(AnchorPane root, String mensaje, ToastType tipo) {
 
        String colorIcono = switch (tipo) {
            case EXITO -> "#4caf50";
            case ERROR -> "#f44336";
            case INFO -> "#2196f3";
            case ADVERTENCIA -> "#ff9800";
        };
 
        // Contenedor del toast
        HBox toast = new HBox(10);
        toast.setMaxWidth(300);
        toast.setMaxHeight(ALTO_TOAST);
        toast.setPrefHeight(ALTO_TOAST);
        toast.setMinHeight(Region.USE_PREF_SIZE);
        toast.setAlignment(Pos.CENTER_LEFT);
        toast.setPadding(new Insets(12, 16, 12, 16));
        toast.getStyleClass().addAll("toast", tipo.name().toLowerCase(Locale.ROOT));
 
        // Para que sea bonito :p
        Label icono = new Label("●");
        icono.setStyle("-fx-text-fill: " + colorIcono + "; -fx-font-size: 20px;");
        Label texto = new Label(mensaje);
        texto.setStyle("-fx-text-fill: #000000; -fx-font-size: 13px;");
        texto.setWrapText(true);
        toast.getChildren().addAll(icono, texto);
 
       
 
        // La pos -> esquina inferior derecha, usando anchors (root es un AnchorPane)
        AnchorPane.setRightAnchor(toast, MARGEN_DERECHO);
        AnchorPane.setBottomAnchor(toast, MARGEN_INFERIOR);
        root.getChildren().add(toast);
        toast.toFront();
 
        FadeTransition entrada = new FadeTransition(Duration.millis(300), toast);
        entrada.setFromValue(0);
        entrada.setToValue(1);
        TranslateTransition slide = new TranslateTransition(Duration.millis(300), toast);
        slide.setFromX(50);
        slide.setToX(0);
        new ParallelTransition(entrada, slide).play();
 
        // y cerramos después de 3 segundos
        PauseTransition pausa = new PauseTransition(Duration.seconds(3));
        pausa.setOnFinished(e -> cerrar(root, toast));
        pausa.play();
    }
    
    
 
    private static void cerrar(AnchorPane root, HBox toast) {
        FadeTransition salida = new FadeTransition(Duration.millis(300), toast);
        salida.setFromValue(1);
        salida.setToValue(0);
        salida.setOnFinished(e -> root.getChildren().remove(toast));
        salida.play();
    }
 
}
 