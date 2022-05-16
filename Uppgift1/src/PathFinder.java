import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Optional;

public class PathFinder extends Application{

    private BorderPane root;
    private Stage stage;
    private Pane center;
    private Button newPlaceButton;
    private ListGraph<Node> listGraph = new ListGraph<>();

    @Override
    public void start(Stage primaryStage) {
        stage = new Stage();
        root = new BorderPane();

        VBox vbox = new VBox();
        root.setTop(vbox);
        root.setStyle("-fx-font-size:14");

        MenuBar menuBar = new MenuBar();
        vbox.getChildren().add(menuBar);

        Menu fileMenu = new Menu("File");
        menuBar.getMenus().add(fileMenu);

        MenuItem newMapMenu = new MenuItem("New Map");
        fileMenu.getItems().add(newMapMenu);
        newMapMenu.setOnAction(new newMapHandler());

        MenuItem openMenu = new MenuItem("Open");
        fileMenu.getItems().add(openMenu);
        openMenu.setOnAction(new openHandler());

        MenuItem saveMenu = new MenuItem("Save");
        fileMenu.getItems().add(saveMenu);

        MenuItem saveImgMenu = new MenuItem("Save Image");
        fileMenu.getItems().add(saveImgMenu);

        MenuItem exitMenu = new MenuItem("Exit");
        fileMenu.getItems().add(exitMenu);

        FlowPane controls = new FlowPane();
        vbox.getChildren().add(controls);
        controls.setAlignment(Pos.CENTER);
        controls.setPadding(new Insets(5));
        controls.setHgap(10);

        newPlaceButton = new Button("New Place");
        newPlaceButton.setOnAction(new newPlaceHandler());

        Button newConButton = new Button("New Connection");

        Button showConButton = new Button("Show Connection");

        Button changeConButton = new Button("Change Connection");

        Button findPathButton = new Button("Find Path");
        controls.getChildren().addAll(newPlaceButton, newConButton, showConButton, changeConButton, findPathButton);

        Scene scene = new Scene(root, 618, 82);
        stage.setTitle("PathFinder");
        stage.setScene(scene);
        stage.setResizable(false);
        //stage.setOnCloseRequest(new ExitHandler());
        stage.show();
    }

    class newMapHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent actionEvent) {
            showImg();
        }
    }

    class openHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent actionEvent) {
            showImg();
        }
    }

    class newPlaceHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent actionEvent) {
            center.setOnMouseClicked(new ClickHandler());
            center.setCursor(Cursor.CROSSHAIR);
            newPlaceButton.setDisable(true);
        }
    }

    class ClickHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent mouseEvent) {
            double x = mouseEvent.getX();
            double y = mouseEvent.getY();
            try {
                NodeForm form = new NodeForm();
                Optional<ButtonType> answer = form.showAndWait();
                if (answer.isPresent() && answer.get() != ButtonType.OK)
                    return;
                String name = form.getName();
                Node node = new Node(name, x, y);
                listGraph.add(node);
                center.getChildren().add(node);
                center.setOnMouseClicked(null);
                center.setCursor(Cursor.DEFAULT);
                newPlaceButton.setDisable(false);
            } catch (Exception e) {
                System.out.println(e);
            }

        }
    }

    private void showImg(){
        center = new Pane();
        Image image = new Image("europa.gif");
        ImageView imageView = new ImageView(image);
        center.getChildren().add(imageView);
        double height = 112 + image.getHeight();
        root.setCenter(center);
        stage.setHeight(height);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
