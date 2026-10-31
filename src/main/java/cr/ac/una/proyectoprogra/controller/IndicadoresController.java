/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.Evento;
import cr.ac.una.proyectoprogra.model.Instrumento;
import cr.ac.una.proyectoprogra.model.MovimientoDto;
import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.service.EventoService;
import cr.ac.una.proyectoprogra.service.InstrumentoService;
import cr.ac.una.proyectoprogra.service.MovimientoService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.Respuesta;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.swing.text.DateFormatter;

/**
 * FXML Controller class
 *
 * @author vjjcu
 */
public class IndicadoresController extends Controller implements Initializable {

    @FXML
    private VBox cardAgenda;
    @FXML
    private HBox filaCentral;
    @FXML
    private Label txtIngresos;
    @FXML
    private Label txtGastos;
    @FXML
    private Label txtEventos;
    @FXML
    private BarChart<String, Number> grBarras;
    @FXML
    private ProgressBar prBarra;
    
    private MovimientoService movimientoService = new MovimientoService();
    private InstrumentoService instrumentoService = new InstrumentoService();
    private EventoService eventoService = new EventoService();
    
    UsuarioDto usuarioActivo = (UsuarioDto) AppContext.getInstance().get("Usuario");
    Long bufeteId = usuarioActivo.getUsrBufeteId();
    
    @FXML
    private Label txtInstrumentos;
    @FXML
    private TableColumn<Evento, String> colHora;
    @FXML
    private TableColumn<Evento, String> colEstado;
    
    private ObservableList<Evento> listaEventos = FXCollections.observableArrayList();
    @FXML
    private TableView<Evento> tblAgenda;
    @FXML
    private TableColumn<Evento, String> colDescripcion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cardAgenda.prefHeightProperty().bind(filaCentral.heightProperty());
        inicializarAgenda();
        cargarIndicadores();
    } 
    
    private void inicializarAgenda(){
        tblAgenda.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
 
        colHora.setCellValueFactory(cellData->{
            if(cellData.getValue() != null){
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("hh:mm a");
                return new SimpleStringProperty(cellData.getValue().getEveFechaHora().format(formato));
            }
            return new SimpleStringProperty("-");
        });
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("eveDetalle"));
        colEstado.setCellValueFactory(cellData->{
            SimpleStringProperty estado = new SimpleStringProperty();
            if(cellData.getValue().getEveAtendido() == true){
                estado.set("Atendido");
            }else{
                estado.set("Pendiente");
            }
            return estado;
        });
 
        this.tblAgenda.setPlaceholder(new Label("No hay eventos pendietes por el momento"));
 
        tblAgenda.setItems(listaEventos);
    }
    
    private void cargarIndicadores(){
        LocalDate fechaActual = LocalDate.now();
        int mesActual = fechaActual.getMonthValue();
        int anioActual = fechaActual.getYear();
        
        BigDecimal ingMes = BigDecimal.ZERO;
        BigDecimal gasMes = BigDecimal.ZERO;
        BigDecimal tributado = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        int insActivos = 0;
        
        //Instrumentos
        Respuesta respuestaIns = instrumentoService.getInstrumentos(bufeteId, null, null, null);
        if(respuestaIns.getEstado().equals(true)){
            List<Instrumento> instrumentos = (List<Instrumento>) respuestaIns.getResultado("Instrumentos");
            if(instrumentos != null){
                for(Instrumento i : instrumentos){
                    if(i.getInsEstadoFinal().equals(false)){
                        insActivos++;
                    }
                    LocalDate insFecha = i.getInsFechaHora() != null ? i.getInsFechaHora().toLocalDate() : null;
                    if(insFecha != null && insFecha.getMonthValue() == mesActual && insFecha.getYear() == anioActual){
                        BigDecimal monto = i.getInsMontoFacturado() != null ? i.getInsMontoFacturado() : i.getInsMontoReal();
                        if(monto != null){
                            ingMes = ingMes.add(monto);
                            total = total.add(monto);
                            if(i.getInsTributa()){
                                BigDecimal t = monto.multiply(new BigDecimal("0.13"));
                                tributado = tributado.add(t);
                            }
                        }
                    }
                }
            }
        }
        
        //Movimientos
        Respuesta respuestaMov = movimientoService.getMovimientos(bufeteId);
        if(respuestaMov.getEstado().equals(true)){
            List<MovimientoDto> movimientos = (List<MovimientoDto>) respuestaMov.getResultado("Movimientos");
            if(movimientos != null){
                for(MovimientoDto m : movimientos){
                    LocalDate movFecha = m.getMovFecha() != null ? m.getMovFecha() : null;
                    if(movFecha != null && movFecha.getMonthValue() == mesActual && movFecha.getYear() == anioActual){
                        BigDecimal monto = m.getMovMonto() != null ? m.getMovMonto() : BigDecimal.ZERO;
                        String tipo = m.getMovTipo();

                        if(tipo.equals("I")){
                            ingMes = ingMes.add(monto);
                        }else if(tipo.equals("G")){
                            gasMes = gasMes.add(monto);
                        }
                        total = total.add(monto);

                        if(m.getMovTributa()){
                            BigDecimal t = monto.multiply(new BigDecimal("0.13"));
                            tributado = tributado.add(t);
                        }
                    }
                }
            }
        }
        
        //Eventos
        String hoy = fechaActual.toString();
        Respuesta respuestaEve = eventoService.getEventos(bufeteId, null, false, hoy, hoy);
        int cantEventos = 0;
        if(respuestaEve.getEstado().equals(true)){
            List<Evento> eventos = (List<Evento>) respuestaEve.getResultado("Eventos");
            if(eventos != null){
                listaEventos.setAll(eventos);
                cantEventos = eventos.size();
            }else{
                listaEventos.clear();
            }
        }
        
        txtIngresos.setText(ingMes.toString());
        txtGastos.setText(gasMes.toString());
        txtInstrumentos.setText(String.valueOf(insActivos));
        txtEventos.setText(String.valueOf(cantEventos));
        
        cargarGraficoBarras(ingMes, gasMes);
        cargarBarraProgreso(tributado, total);
    }
    
    private void cargarGraficoBarras(BigDecimal ing, BigDecimal gas){
        grBarras.getData().clear();
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Mes Actual");
        XYChart.Data<String, Number> ings = new XYChart.Data<>("Ingresos", ing);
        XYChart.Data<String, Number> gast = new XYChart.Data<>("Gastos", gas);
        serie.getData().add(ings);
        serie.getData().add(gast);
        
        grBarras.getData().add(serie);
        ings.getNode().setStyle(" -fx-bar-fill: #3F6084;");
        gast.getNode().setStyle(" -fx-bar-fill: #B5C2D2;");
        grBarras.setLegendVisible(false);
    }
    
    private void cargarBarraProgreso(BigDecimal tributado, BigDecimal total){
        if(total.compareTo(BigDecimal.ZERO) == 0){
            prBarra.setProgress(0.0);
            return;
        }
        BigDecimal pTributado = tributado.divide(total,2,RoundingMode.HALF_UP);
        Double pr = Math.min(Math.max(pTributado.doubleValue(), 0.0), 1.0);
        prBarra.setProgress(0.7);
    }

    @Override
    public void initialize() {
        cargarIndicadores();
    }
    
}
