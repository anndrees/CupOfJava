package controller;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Ticket;
import model.TicketAbierto;
import model.TipoPedido;

import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SaveTicketController implements Initializable{

    @FXML
    private AnchorPane anchorCreateItem;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnCreateTicket;

    @FXML
    private ComboBox<TipoPedido> cboxTipoTicket;

    @FXML
    private ImageView imgLogo;

    @FXML
    private TextField txtNombre;

	private List<Ticket> tickets = new ArrayList<>();
	private List<TicketAbierto> ticketsAbiertos = new ArrayList<>();

    @FXML
    void cancelar(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Cancelar operación");
		alert.setHeaderText("Estás a punto cancelar la operación");
		alert.setContentText(
				"Se perderán todos los cambios que no hayas guardado.\n" +
						"¿Seguro que quieres continuar?"
		);
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			Stage stage = (Stage) btnCancelar.getScene().getWindow();
			stage.close();
		}
    }

	@FXML
	void createOpenTicket(ActionEvent event) {
		String rutaProyecto = System.getProperty("user.dir");
		String rutaJson = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "OpenTickets.json";

		String ticketName = txtNombre.getText();
		TipoPedido tipo = cboxTipoTicket.getValue();

		cargarTicketsAbiertos();

		System.out.println("Imprimiendo tickets");
		System.out.println(ticketsAbiertos);

		for (TicketAbierto ticket : ticketsAbiertos) {
			if (ticket.getNombre().equals(ticketName)) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Nombre no disponible");
				alert.setHeaderText("Ya existe un ticket con el nombre: " + ticketName);
				alert.setContentText("Por favor, elige otro nombre para el ticket.");
				alert.showAndWait();
				return;
			}
		}

		TicketAbierto ticketAbierto = new TicketAbierto(ticketName, tipo, tickets);

		try {
			// Lee el archivo JSON existente
			FileReader reader = new FileReader(rutaJson);
			JsonElement jsonElement = JsonParser.parseReader(reader);
			reader.close();

			// Convierte el elemento JSON a una lista de TicketAbierto
			Gson gson = new GsonBuilder()
					.registerTypeAdapter(DecimalFormat.class, new TypeAdapter<DecimalFormat>() {
						@Override
						public void write(JsonWriter out, DecimalFormat value) throws IOException {
							out.value(value.toPattern());
						}

						@Override
						public DecimalFormat read(JsonReader in) throws IOException {
							return new DecimalFormat(in.nextString());
						}
					})
					.setPrettyPrinting()
					.create();

			JsonArray jsonArray;
			if (jsonElement.isJsonArray()) {
				jsonArray = jsonElement.getAsJsonArray();
			} else {
				jsonArray = new JsonArray();
			}

			// Agrega el nuevo ticket a la lista
			JsonElement nuevoTicketJson = gson.toJsonTree(ticketAbierto);
			jsonArray.add(nuevoTicketJson);

			// Escribe el JSON actualizado de vuelta al archivo
			FileWriter writer = new FileWriter(rutaJson);
			gson.toJson(jsonArray, writer);
			writer.close();

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Ticket creado");
			alert.setHeaderText("Ticket creado correctamente");
			alert.setContentText("Se ha creado el ticket: " + ticketName + " con un precio de: " + ticketAbierto.getTotal());
			alert.showAndWait();

			Stage stage = (Stage) btnCreateTicket.getScene().getWindow();
			stage.close();

		} catch (IOException e) {
			e.printStackTrace();
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("No se pudo crear el ticket");
			alert.setContentText("Ocurrió un error al intentar leer o escribir en el archivo JSON.");
			alert.showAndWait();
		}
	}

	private void cargarTicketsAbiertos(){
		String rutaProyecto = System.getProperty("user.dir");
		String rutaJson = rutaProyecto + File.separator + "src" + File.separator + "app" + File.separator + "assets" + File.separator + "json" + File.separator + "OpenTickets.json";

		try {
			// lee el json , y carga todos los tickets abiertos en la lista ticketsAbiertos
			FileReader reader = new FileReader(rutaJson);
			JsonParser parser = new JsonParser();
			JsonElement json = parser.parse(reader);
			JsonArray tickets = json.getAsJsonArray();
			for (JsonElement ticketElement : tickets) {
				JsonObject ticketObj = ticketElement.getAsJsonObject();
				if (ticketObj.has("nombre") && ticketObj.has("total")) {
					String nombre = ticketObj.get("nombre").getAsString();
					String tipo = ticketObj.get("tipo").getAsString();
					double total = ticketObj.get("total").getAsDouble();
					TipoPedido tipoPedido = TipoPedido.fromString(tipo);
					TicketAbierto ticket = new TicketAbierto(nombre, tipoPedido, new ArrayList<>());
					ticket.setNombre(nombre);
					ticket.setTotal(total);
					ticketsAbiertos.add(ticket);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle){
		cboxTipoTicket.getItems().addAll(TipoPedido.values());
		cboxTipoTicket.getSelectionModel().selectFirst();
	}

	public void setTickets(List<Ticket> tickets){
		this.tickets = tickets;
	}
}
