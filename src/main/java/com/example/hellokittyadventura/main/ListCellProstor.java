package com.example.hellokittyadventura.main;

import com.example.hellokittyadventura.logika.Vec;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import com.example.hellokittyadventura.logika.Prostor;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ListCellProstor extends ListCell<Prostor> {
    @Override
    protected void updateItem(Prostor prostor, boolean empty) {
        super.updateItem(prostor, empty);
        if(empty){setText(null);
        setGraphic(null);
        }else{
            Label label = new Label(prostor.getNazev());
            label.setFont(Font.font("System", FontWeight.BOLD, 20));
            label.setTextFill(Color.WHITE);
            label.setStyle("-fx-text-fill: white; " +
                    "-fx-font-weight: bold; " +
                    "-fx-font-size: 20px; " +
                    "-fx-effect: dropshadow( one-pass-box , black , 2 , 2 , 2 , 2 );");
            DropShadow ds = new DropShadow();
            ds.setOffsetY(3.0f);
            ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
            label.setEffect(ds);
            ImageView iw = new ImageView(getClass().getResource("prostory/" + prostor.getNazev() + ".jpg").toExternalForm());
            iw.setFitWidth(150);
            iw.setPreserveRatio(true);

            StackPane stackPane = new StackPane();
            stackPane.getChildren().addAll(iw, label);
            stackPane.setAlignment(label, Pos.CENTER);

            setText(null);
            setGraphic(stackPane);
        }
    }
}
