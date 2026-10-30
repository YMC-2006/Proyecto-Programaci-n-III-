/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package cr.ac.una.proyectoprogra.controller;

import cr.ac.una.proyectoprogra.model.Instrumento;
import cr.ac.una.proyectoprogra.model.MovimientoDto;
import cr.ac.una.proyectoprogra.model.UsuarioDto;
import cr.ac.una.proyectoprogra.service.InstrumentoService;
import cr.ac.una.proyectoprogra.service.MovimientoService;
import cr.ac.una.proyectoprogra.util.AppContext;
import cr.ac.una.proyectoprogra.util.Respuesta;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    @FXML
    private TableView<?> lvAgenda;
    
    private MovimientoService movimientoService = new MovimientoService();
    private InstrumentoService instrumentoService = new InstrumentoService();
    UsuarioDto usuarioActivo = (UsuarioDto) AppContext.getInstance().get("Usuario");
    Long bufeteId = usuarioActivo.getUsrBufeteId();
    @FXML
    private Label txtInstrumentos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cardAgenda.prefHeightProperty().bind(filaCentral.heightProperty());
        cargarIndicadores();
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
        
        txtIngresos.setText(ingMes.toString());
        txtGastos.setText(gasMes.toString());
        txtInstrumentos.setText(String.valueOf(insActivos));
        txtEventos.setText("0");
        
        cargarGraficoBarras(ingMes, gasMes);
        cargarBarraProgreso(tributado, total);
    }
    
    private void cargarGraficoBarras(BigDecimal ing, BigDecimal gas){
        grBarras.getData().clear();
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Mes Actual");
        serie.getData().add(new XYChart.Data<>("Ingresos", ing));
        serie.getData().add(new XYChart.Data<>("Gastos", gas));
        grBarras.getData().add(serie);
    }
    
    private void cargarBarraProgreso(BigDecimal tributado, BigDecimal total){
        if(total.compareTo(BigDecimal.ZERO) == 0){
            prBarra.setProgress(0.0);
            return;
        }
        BigDecimal pTributado = tributado.divide(total,2,RoundingMode.HALF_UP);
        prBarra.setProgress(pTributado.doubleValue());
    }

    @Override
    public void initialize() {
        cargarIndicadores();
    }
    
}
