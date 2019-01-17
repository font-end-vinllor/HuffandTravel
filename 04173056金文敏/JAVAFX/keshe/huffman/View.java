package huffman;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class View extends Application {

	/* 界面的操作 */
	public void start(Stage stage) {
		Tree tree = new Tree();
		tree.Huffman();
		VBox pane = new VBox();
		pane.setAlignment(Pos.CENTER);
		Scene scane = new Scene(pane, 300, 200);
		stage.setScene(scane);
		stage.setTitle("huffman");
		Label text = new Label();
		text.setText("----编码 译码 ----");
		text.setTextFill(Color.WHITE);
		text.setFont(new Font("Arial", 30));
		pane.getChildren().add(text);
		Image img =  new Image("http://p0.so.qhimgs1.com/bdr/_240_/t019fba5645b56ce63c.jpg");
		BackgroundImage backimg = new BackgroundImage(img, null, null, BackgroundPosition.CENTER, null);
		Background bac = new  Background(backimg);
		pane.setBackground(bac);
		
		ToggleGroup group = new ToggleGroup();
		RadioButton encode = new RadioButton("encode");
		encode.setToggleGroup(group);
	//	 encode.setSelected(true);
		encode.setUserData("encode");
	
		
		RadioButton decode = new RadioButton("decode");
		decode.setToggleGroup(group);
	 //   decode.setSelected(true);
		decode.setUserData("decode");
				group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> o, Toggle old, Toggle nnew) {
				if (group.getSelectedToggle() != null) {
					if (group.getSelectedToggle().getUserData().toString().equals("decode")) {
						tree.decode();
						
			} else 
						tree.encode();
	
				}
			}
		});
		pane.getChildren().add(encode);
		pane.getChildren().add(decode);
		scane.setRoot(pane);
		stage.show();
	}

	public static void main(String[] args) {

		launch(args);
	}
}
