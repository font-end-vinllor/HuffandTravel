package maze;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
		Scanner sc = new Scanner(System.in);
		Create.createGraph("travel.sc", "path.sc");
		Path path = new Path();

		VBox pane = new VBox();
		pane.setAlignment(Pos.CENTER);

		Scene scane = new Scene(pane, 300, 200);
		stage.setScene(scane);
		stage.setTitle("校园导游图");
		Label text = new Label();
		text.setText("---- Menu ----");
		text.setTextFill(Color.WHITE);
		text.setFont(new Font("Arial", 30));
		pane.getChildren().add(text);
		Image img = new Image("http://p0.so.qhimgs1.com/bdr/_240_/t019fba5645b56ce63c.jpg");
		BackgroundImage backimg = new BackgroundImage(img, null, null, BackgroundPosition.CENTER, null);
		Background bac = new Background(backimg);
		pane.setBackground(bac);

		ToggleGroup group = new ToggleGroup();
		RadioButton bt0 = new RadioButton("queryView");
		bt0.setToggleGroup(group);
		bt0.setUserData("queryView");
		pane.getChildren().add(bt0);

		RadioButton bt1 = new RadioButton("addView");
		bt1.setToggleGroup(group);
		bt1.setUserData("addView");
		pane.getChildren().add(bt1);

		RadioButton bt2 = new RadioButton("removeView");
		bt2.setToggleGroup(group);
		//bt2.setSelected(true);
		bt2.setUserData("removeView");
		pane.getChildren().add(bt2);

		RadioButton bt3 = new RadioButton("queryPath");
		bt3.setToggleGroup(group);
		bt3.setUserData("queryPath");
		pane.getChildren().add(bt3);

		RadioButton bt4 = new RadioButton("addPath");
		bt4.setToggleGroup(group);
		bt4.setUserData("addPath");
		pane.getChildren().add(bt4);

		RadioButton bt5 = new RadioButton("removePath");
		bt5.setToggleGroup(group);
		bt5.setUserData("removePath");
		pane.getChildren().add(bt5);

		RadioButton bt6 = new RadioButton("modifyView");
		bt6.setToggleGroup(group);
		bt6.setUserData("modifyView");
		pane.getChildren().add(bt6);

		RadioButton bt7 = new RadioButton("modifyPath");
		bt7.setToggleGroup(group);
		bt7.setUserData("modifyPath");
		pane.getChildren().add(bt7);

		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> o, Toggle old, Toggle nnew) {
				while (group.getSelectedToggle() != null) {
					String src = group.getSelectedToggle().getUserData().toString();
					if (src.equals("addView")) {
						String name = sc.next();
						System.out.println("景点是否增加成功 :"+path.addView(name));

					} else if (src.equals("removeView")) {
						String name = sc.next();
						System.out.println("景点是否删除成功 : "+path.removeView(name));
					} else if (src.equals("modifyView")) {
						String aim = sc.next();
						String s = sc.next();
						System.out.println("景点是否修改成功:"+path.modifyView(aim, s));
					} else if (src.equals("queryView")) {
						String s = sc.next();
						path.queryView(s);
					} else if (src.equals("addPath")) {
						String start = sc.next();
						String end = sc.next();
						int weight = sc.nextInt();
						System.out.println("路径是否添加成功 :"+path.addPath(start, end, weight));
					} else if (src.equals("removePath")) {
						String name = sc.nextLine();
						System.out.println("路径是否删除成功 :"+path.removePath(name));
					} else if (src.equals("modifyPath")) {
						String aim = sc.nextLine();
						String s = sc.nextLine();
						System.out.println(" 是否修改成功 : "+path.modifyPath(aim, s));
					} else if (src.equals("queryPath")) {
						List<Integer> pat = new ArrayList<>();
//						String start = sc.next();
//						String end = sc.next();
						int start = sc.nextInt();
						int end  =sc.nextInt();
						path.queryPath(start, end, pat);
						if(pat.size() == 0) System.out.println(start+"不能到达"+end);
						else 
							for (int i = pat.size() - 1; i >= 0; i--)
							System.out.print(Create.vexs.get(pat.get(i)).name + "-----");
					}

				}
			}
		});

		scane.setRoot(pane);
		stage.show();
	}

	public static void main(String[] args) {

		launch(args);
	}
}
