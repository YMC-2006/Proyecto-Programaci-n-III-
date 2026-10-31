/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.Evento;
import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.service.EventoService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.Respuesta;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class AgendaController extends Controller implements Initializable {

    @FXML
    private GridPane gridCalendario; 
    private YearMonth mesActual = YearMonth.now(); // navegar entre meses es solo cambiar el mes actual
    private int columnaSemanaInicio = 0;
    private LocalDate diaSemanaDiaUno = LocalDate.now();
    /*
    mi columna de inicio sera igual a al dia de la semana de inicio menos uno
    por ejemplo una mes donde el dia 1 cae un lunes, si le resto 1, queda en 0 esa sera mi primera coluna
    */
    @FXML
    private Button btnAnterior;
    @FXML
    private Label lblMesAnio;
    @FXML
    private Button btnSiguiente;
    
    UsuarioDto usuarioActivo = (UsuarioDto) AppContext.getInstance().get("Usuario");
    Long bufeteId = usuarioActivo.getUsrBufeteId();
    
    private EventoService eventoService = new EventoService();
    List<Evento> eventosMes = new ArrayList<>();
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEventos();
    }    

    @Override
    public void initialize() {
        cargarEventos();
    }
    
    private void cargarEventos(){
        LocalDate inicioMes = mesActual.atDay(1);
        LocalDate finMes = mesActual.atEndOfMonth();
        
        String desde = inicioMes.toString();
        String hasta = finMes.toString();
        
        Respuesta respuestaEve = eventoService.getEventos(bufeteId, null, null, desde, hasta);
        if(respuestaEve.getEstado()){
            eventosMes = (List<Evento>) respuestaEve.getResultado("Eventos");
            if(eventosMes == null){
                eventosMes = new ArrayList<>();
            }
        }
        generarCalendario();
    }
 
    private void generarCalendario(){
        gridCalendario.getChildren().clear();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("MMMM yyyy");
        lblMesAnio.setText(mesActual.format(formato).toUpperCase());
        
        LocalDate primerDiaMes = mesActual.atDay(1);
        int diaMes = mesActual.lengthOfMonth();
        
        int inicioSemana = primerDiaMes.getDayOfWeek().getValue();
        int inicioCol = inicioSemana - 1;
        
        int fila = 0;
        int columna = inicioCol;
        
        for(int i = 1; i <= diaMes; i++){
            LocalDate fecha = mesActual.atDay(i);
            
            VBox celda = new VBox();
            celda.setStyle("-fx-border-color: #e0e0e0; -fx-background-color: #ffffff; -fx-padding: 5;");
            celda.setAlignment(Pos.TOP_LEFT);
            
            Label num = new Label(String.valueOf(i));
            num.setStyle("-fx-font-weight: bold; -fx-text-fill: #333333;");
            celda.getChildren().add(num);
            
            List<Evento> eventosDia = eventosMes.stream().filter(e-> e.getEveFechaHora() != null && e.getEveFechaHora().toLocalDate().equals(fecha)).toList();
            for(Evento e : eventosDia){
                Label lblEvento = new Label(formatoHora(e)+" "+e.getEveDetalle());
                lblEvento.setMaxWidth(Double.MAX_VALUE);
                lblEvento.setWrapText(true);
                lblEvento.setStyle(colorEstado(e.getEveAtendido()));
                celda.getChildren().add(lblEvento);
            }
            
            GridPane.setHgrow(celda, Priority.ALWAYS);
            GridPane.setVgrow(celda, Priority.ALWAYS);
            
            gridCalendario.add(celda, columna, fila);
            columna++;
            if(columna > 6){
                columna = 0;
                fila++;
            }
        }
        
    }
    
    private String formatoHora(Evento e){
        try{
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("hh:mm a");
            return e.getEveFechaHora().format(formato);
        }catch(Exception ex){
            return " ";
        }
    }
    
    private String colorEstado(Boolean atendido){
        if(atendido){
            return "-fx-background-color: #d4edda; -fx-text-fill: #155724; -fx-padding: 3; -fx-background-radius: 3; -fx-font-size: 11px;";
        }else{
            return "-fx-background-color: #f8d7da; -fx-text-fill: #721c24; -fx-padding: 3; -fx-background-radius: 3; -fx-font-size: 11px;";
        }
    }

    @FXML
    private void onActionMesAnterior(ActionEvent event) {
        mesActual = mesActual.minusMonths(1);
        cargarEventos();
    }

    @FXML
    private void onActionMesSiguiente(ActionEvent event) {
        mesActual = mesActual.plusMonths(1);
        cargarEventos();
    }
    
}
