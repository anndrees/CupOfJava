package controller;

import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

import java.io.*;
import java.net.URL;
import java.util.*;

public class OpenListController implements Initializable{

    @FXML
    private AnchorPane anchorCreateItem;

    @FXML
    private TableView<TicketAbierto> tblOpenTickets;

    @FXML
    private TableColumn<TicketAbierto, String> colNombre;

    @FXML
    private TableColumn<TicketAbierto, Double> colTotal;

    @FXML
    private Button btnRemove;

    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<TipoPedido> cboxTipo;

    @FXML
    private TextField txtName;

    private static ObservableList<TicketAbierto> ticketsAbiertos;

    Stage mainStage;

    @FXML
    private void handleTableClick(MouseEvent event) {
        /*
        MÉTODO PARA CONTROLAR EL DOBLE CLICK
        TicketAbierto selectedTicket = tblOpenTickets.getSelectionModel().getSelectedItem();

        if(selectedTicket != null){
            if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
                //abrir el ticket

            }
        }

         */

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        ticketsAbiertos = FXCollections.observableArrayList();
        cargarTicketsAbiertos();
        tblOpenTickets.setItems(ticketsAbiertos);

        tblOpenTickets.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if(newValue != null){
                btnRemove.setDisable(false);
                btnSave.setDisable(false);
                txtName.setText(newValue.getNombre());
                txtName.setDisable(false);
                cboxTipo.setValue(newValue.getTipoPedido());
                cboxTipo.setDisable(false);
            }else{
                btnRemove.setDisable(true);
            }

        });

        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        cboxTipo.getItems().addAll(TipoPedido.values());
    }

    private void cargarTicketsAbiertos() {
        // Método que carga desde el JSON todos los tickets abiertos
        try {
            FileReader reader = new FileReader("src/app/assets/json/openTickets.json");
            JsonParser parser = new JsonParser();
            JsonElement json = parser.parse(reader);
            JsonArray tickets = json.getAsJsonArray();
            for (JsonElement ticketElement : tickets) {
                JsonObject ticketObj = ticketElement.getAsJsonObject();
                if (ticketObj.has("nombre") && ticketObj.has("total")) {
                    String nombre = ticketObj.get("nombre").getAsString();
                    String tipo = ticketObj.get("tipo").getAsString();
                    double total = ticketObj.get("total").getAsDouble();
                    // Usar el método fromString para obtener el TipoPedido
                    TipoPedido tipoPedido = TipoPedido.fromString(tipo);
                    TicketAbierto ticket = new TicketAbierto(nombre, tipoPedido, new ArrayList<>());
                    ticket.setNombre(nombre);
                    ticket.setTotal(total);
                    ticketsAbiertos.add(ticket);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void setStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    void removeTicket(MouseEvent event) {
        TicketAbierto selectedTicket = tblOpenTickets.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar ticket");
        alert.setHeaderText("¿Estás seguro de eliminar este ticket?");
        alert.setContentText("Se eliminará el siguiente ticket: " + selectedTicket.getNombre());
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            String rutaProyecto = System.getProperty("user.dir");
            String rutaJson = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator;

            // Determinar el nombre del archivo JSON según el tipo de ticket
            String rutaJsonTipo = rutaJson + "openTickets.json";

            try {
                File file = new File(rutaJsonTipo);
                if (file.exists() && file.length() > 0) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();

                    // Cargar el JSON y eliminar el ticket correspondiente
                    FileReader reader = new FileReader(file);
                    JsonElement jsonElement = JsonParser.parseReader(reader);
                    JsonArray jsonArray = jsonElement.getAsJsonArray();

                    for (JsonElement ticketElement : jsonArray) {
                        JsonObject ticketObj = ticketElement.getAsJsonObject();
                        String nombre = ticketObj.get("nombre").getAsString();
                        if (nombre.equals(selectedTicket.getNombre())) {
                            jsonArray.remove(ticketElement);
                            break;
                        }
                    }

                    // Escribir el JSON modificado de vuelta al archivo
                    FileWriter writer = new FileWriter(file);
                    gson.toJson(jsonArray, writer);
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            // Eliminar el ticket de la lista observable
            ticketsAbiertos.remove(selectedTicket);
            txtName.setText("");
            txtName.setDisable(true);
            cboxTipo.setValue(null);
            cboxTipo.setDisable(true);
            btnRemove.setDisable(true);
            btnSave.setDisable(true);
            tblOpenTickets.getSelectionModel().clearSelection();
        }
    }


    @FXML
    void saveTicket(MouseEvent event) {
        // comprueba si se ha seleccionado un tipo del combo box
        if(cboxTipo.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Debe seleccionar un tipo de pedido");
            alert.showAndWait();
        }else{
            TicketAbierto selectedTicket = tblOpenTickets.getSelectionModel().getSelectedItem();
            if(selectedTicket == null)
                return; // Verificamos que se haya seleccionado un ticket

            String nombreOriginal = selectedTicket.getNombre(); // Guardamos el nombre original antes de cualquier cambio
            String nombreNuevo = txtName.getText();
            String tipoStr = cboxTipo.getValue().toString();
            TipoPedido tipoNuevo;

			switch(tipoStr){
				case "Comer aquí" -> tipoNuevo = TipoPedido.AQUI;
				case "Para llevar" -> tipoNuevo = TipoPedido.LLEVAR;
				case "Domicilio" -> tipoNuevo = TipoPedido.DOMICILIO;
				case "Para recoger" -> tipoNuevo = TipoPedido.RECOGER;
				default -> {
					System.out.println("Tipo de pedido no válido");
					return;
				}
			}

            selectedTicket.setNombre(nombreNuevo);
            selectedTicket.setTipo(tipoNuevo);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String rutaProyecto = System.getProperty("user.dir");
            String rutaJson = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "openTickets.json";

            try{
                File file = new File(rutaJson);
                if(file.exists() && file.length() > 0){
                    // Cargar el JSON y modificar el ticket correspondiente
                    FileReader reader = new FileReader(file);
                    JsonElement jsonElement = JsonParser.parseReader(reader);
                    JsonArray jsonArray = jsonElement.getAsJsonArray();

                    for(JsonElement ticketElement : jsonArray){
                        JsonObject ticketObj = ticketElement.getAsJsonObject();
                        String ticketNombre = ticketObj.get("nombre").getAsString();
                        if(ticketNombre.equals(nombreOriginal)){ // Comparar con el nombre original
                            ticketObj.addProperty("nombre", nombreNuevo);
                            ticketObj.addProperty("tipo", tipoNuevo.toString());
                            break;
                        }
                    }

                    // Escribir el JSON modificado de vuelta al archivo
                    FileWriter writer = new FileWriter(file);
                    gson.toJson(jsonArray, writer);
                    writer.close();
                }
            }catch(IOException e){
                System.out.println(e.getMessage());
            }

            // Actualizar la tabla si es necesario
            tblOpenTickets.refresh();

            // Cerrar la vista
            Stage stage = (Stage) btnSave.getScene().getWindow();
            stage.close();
        }
    }

}
