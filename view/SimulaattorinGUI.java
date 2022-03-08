package view;

import controller.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import simu.model.Asiakas;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.*;

/**
 * 
 * Luokka luo käyttöliittymän ja käyttöliittymässä olevan visualisoinnin.
 * 
 * @author Aivan Vo, Elsa Rajala, Olli Ruuskanen
 *
 */
public class SimulaattorinGUI extends Application implements ISimulaattorinUI {

	private IKontrolleri kontrolleri;
	private IVisualisointi naytto;
	private Dialog<String> dialogi;

	// Käyttöliittymäkomponentit:
	/**
	 * Käyttöliittymän komponentti ajansyöttöteksiosa, johon käyttäjä voi syöttää
	 * haluamansa simulointiajan.
	 */
	private TextField aika;
	private TextField viive;

	/**
	 * Käyttöliittymässä näkyvä otsikko simulointiajan syöttämiselle.
	 */
	private Label aikaLabel;
	/**
	 * Käyttöliittymän komponentti Käynnistä -nappi,joka käynnistää simuloinnin.
	 */
	private Button kaynnistaButton;
	/**
	 * Käyttöliittymän komponentti Hidasta -nappi,joka hidastaa simulointia
	 * suurentamalla viiveen arvoa.
	 */
	private Button hidastaButton;
	/**
	 * Käyttöliittymän komponentti Nopeuta -nappi,joka nopeuttaa simulointia
	 * pienentämällä viiveen arvoa.
	 */
	private Button nopeutaButton;

	/**
	 * Muuttuja <code>jakaumatList</code> listaa jakaumat
	 */
	private ListView<String> jakaumatList;

	/**
	 * Luo kontrollerin
	 */
	@Override
	public void init() {

		Trace.setTraceLevel(Level.INFO);

		kontrolleri = new Kontrolleri(this);
	}

	/**
	 * JavaFX-sovelluksen (käyttöliittymän) käynnistäminen
	 */
	// JavaFX-sovelluksen (käyttöliittymän) käynnistäminen
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Metodi <code>start</code> rakentaa käyttöliittymän sovellukselle
	 */
	@Override
	public void start(Stage primaryStage) {

		// Käyttöliittymän rakentaminen
		try {

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent t) {
					Platform.exit();
					System.exit(0);
				}
			});

			primaryStage.setTitle("Apteekkisimulaattori");

			kaynnistaButton = new Button("Käynnistä");
			kaynnistaButton.setPrefWidth(130);

			kaynnistaButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {

					boolean virhe = false;

					if (getJakaumaIndex() == -1) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Simulaattori");
						alert.setHeaderText("Valitse jakauma!");

						alert.showAndWait();
						virhe = true;
					}

					try {
						if (!virhe) {
							kontrolleri.kaynnistaSimulointi();
						}
					} catch (NumberFormatException e) {
						virhe = true;

						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Simulaattori");
						alert.setHeaderText("Virheellinen simulointiaika!");

						alert.showAndWait();
					} catch (NullPointerException e) {

					}

				}
			});

			hidastaButton = new Button("Hidasta");
			hidastaButton.setPrefWidth(130);

			hidastaButton.setOnAction(e -> kontrolleri.hidasta());

			nopeutaButton = new Button("Nopeuta");
			nopeutaButton.setPrefWidth(130);
			nopeutaButton.setOnAction(e -> kontrolleri.nopeuta());

			aikaLabel = new Label("Simulointiaika:");
			aikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
			aika = new TextField();
			aika.setPromptText("Syötä aika"); // asettaa haamutekstin
			aika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
			aika.setPrefWidth(150);

			VBox sisaltoBoksi = new VBox();
			sisaltoBoksi.setPadding(new Insets(15, 12, 15, 12));
			sisaltoBoksi.setSpacing(10); // noodien välimatka 10 pikseliä

			// erillisille jakaumatiedostoille varattu valintaboksi
			VBox jakaumatVbox = new VBox();
			jakaumatVbox.setPadding(new Insets(15, 12, 15, 12));
			jakaumatVbox.setSpacing(10);

			Label jakaumatLabel = new Label("Jakaumat");
			jakaumatList = new ListView<String>();
			jakaumatList.setPrefSize(150, 130);
			ObservableList<String> jakaumatObsList = FXCollections.observableArrayList("Rauhallinen aamu", "Tasainen",
					"Iltapäiväruuhka");
			jakaumatList.setItems(jakaumatObsList);

			jakaumatVbox.getChildren().addAll(jakaumatLabel, jakaumatList);

			// vbox kolmelle napille
			VBox napitBox = new VBox();
			napitBox.setPadding(new Insets(15, 12, 15, 12));
			napitBox.setSpacing(10);

			napitBox.getChildren().addAll(nopeutaButton, hidastaButton, kaynnistaButton);
			napitBox.setAlignment(Pos.CENTER_LEFT);

			// jakaumaListView, ajan syöttö ja napit gridpaneen
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setVgap(10);
			grid.setHgap(5);

			grid.add(jakaumatVbox, 0, 0, 1, 2);
			grid.add(aikaLabel, 1, 0, 1, 2);
			grid.add(aika, 2, 0, 1, 2);
			grid.add(napitBox, 3, 0, 1, 2);

			Image img = new Image("/images/taustakuva.JPG");
			ImageView tausta = new ImageView();
			tausta.setImage(img);

			// luodaan uusi Visualisointi-olio
			naytto = new Visualisointi(600, 400);

			dialogi = tulosdialog();

			// kuva ja naytto päällekkäin (jotta saadaan kuva alle)
			StackPane pane = new StackPane();
			pane.getChildren().addAll(tausta, (Canvas) naytto);

			// menubar
			MenuBar menuBar = new MenuBar();
			Menu historiaMenu = new Menu("Näytä");
			MenuItem menuItem1 = new MenuItem("Näytä kaikki aiemmat asiakkaat");
			menuItem1.setOnAction(event -> {
				showFileScreen();
			});
			historiaMenu.getItems().add(menuItem1);

			menuBar.getMenus().add(historiaMenu);
			VBox menullinenIkkuna = new VBox();

			sisaltoBoksi.getChildren().addAll(pane, grid);
			menullinenIkkuna.getChildren().addAll(menuBar, sisaltoBoksi);

			Scene scene = new Scene(menullinenIkkuna);
			scene.getStylesheets().add(getClass().getResource("Stylesheet.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Käyttöliittymän rajapintametodit (kutsutaan kontrollerista)

	/**
	 * Palauttaa käyttäjän syöttämän ajan käyttöliittymästä. Palautettu arvo vastaa
	 * simuloinnin kestoa.
	 * 
	 * @return käyttäjän syöttämä aika doublena, jolla määrätään simuloinnin kesto.
	 */
	@Override
	public double getAika() {
		return Double.parseDouble(aika.getText());
	}

	/**
	 * Palauttaa simulaattorille kontrollerissa asetetun viiveen
	 * 
	 * @return simulaattorin viive
	 */
	@Override
	public long getViive() {
		return Long.parseLong(viive.getText());
	}

	/**
	 * Palauttaa naytto-olion, joka kuvaa simulaattorin visualisointia
	 * 
	 * @return näytto-olio
	 */
	@Override
	public IVisualisointi getVisualisointi() {
		return naytto;
	}

	/**
	 * Palauttaa valitun jakauman lista-indeksin
	 * 
	 * @return valitun jakauman lista-indeksi
	 */
	@Override
	public int getJakaumaIndex() {
		return jakaumatList.getSelectionModel().getSelectedIndex();
	}

	/**
	 * Suorituskykysuureiden tulostus dialogiin
	 */
	// Suorituskykysuureiden tulostus dialogiin
	@Override
	public void tulokset() {
		dialogi.setContentText("Asiakkaiden läpimenoaikojen keskiarvo " + kontrolleri.keskiarvo()
				+ "\n Apteekin suoritusteho: " + kontrolleri.suoritusteho());
		dialogi.showAndWait();
		System.out.println("tuloksettt");

	}

	/**
	 * Metodi joka palauttaa dialogin
	 * 
	 * @return dialogi
	 */
	private Dialog<String> tulosdialog() {

		Dialog<String> dialog = new Dialog<String>();
		dialog.setTitle("Tulokset");
		ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(type);

		return dialog;
	}

	/**
	 * Luo uuden ikkunan, jossa esitellään tietokantaan tallennetut asiakastiedot
	 */
	private void showFileScreen() {
		Stage stage = new Stage();
		stage.setTitle("Tietokantatiedosto");

		Asiakas[] as;
		as = kontrolleri.getAsiakkaat();

		GridPane pane = new GridPane();
		for (int i = 0; i < as.length; i++) {
			Label label = new Label(as[i].toString());
			pane.add(label, 0, i);
		}

		VBox root = new VBox();
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(pane);
		root.getChildren().addAll(scrollPane);

		Scene scene = new Scene(root, 500, 600);
		stage.setScene(scene);
		stage.show();
	}

}
