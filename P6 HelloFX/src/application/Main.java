package application;

import java.io.FileInputStream;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This is the main class to build the JavaFX application and render the components.
 * @author Rohan Mendiratta
 * @version 1.0
 */
public class Main extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	private static final int WINDOW_WIDTH = 300;
	private static final int WINDOW_HEIGHT = 200;
	private static final String APP_TITLE = "P6 Hello FX";

	/**
	 * Entry point of the JavaFX GUI
	 * @param primaryStage stage to render primary components to
	 * @throws Exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		args = this.getParameters().getRaw();

		// application.Main layout is Border Pane example (top,left,center,right,bottom)
		BorderPane root = new BorderPane();

		// Build comboBox
		ComboBox comboBox = new ComboBox();
		comboBox.getItems().addAll(
			"option 1",
			"option 2",
			"option 3"
		);
		BorderPane.setAlignment(comboBox, Pos.CENTER);

		// Build label
		Label label = new Label("CS400 MyFirstJavaFX");
		BorderPane.setAlignment(label, Pos.CENTER);


		// Build Image
		FileInputStream input = new FileInputStream("images/pic.jpeg");
		Image image = new Image(input);
		ImageView imageView = new ImageView();
		imageView.setImage(image);
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(150);
		BorderPane.setAlignment(imageView, Pos.CENTER);

		// Build button
		Button button = new Button("Done");
		BorderPane.setAlignment(button, Pos.CENTER);

		// Build toggle button
		ToggleButton toggleButton = new ToggleButton("Toggle Me");
		BorderPane.setAlignment(toggleButton, Pos.CENTER);

		// Set the elements of the BorderPane
		root.setTop(label);
		root.setLeft(comboBox);
		root.setCenter(imageView);
		root.setBottom(button);
		root.setRight(toggleButton);
		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	/**
	 * application.Main driver to launch JavaFX App
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}