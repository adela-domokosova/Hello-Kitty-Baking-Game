package com.example.hellokittyadventura.main;

import com.example.hellokittyadventura.logika.Vec;
import javafx.scene.control.ListCell;
import com.example.hellokittyadventura.logika.Prostor;
import javafx.scene.image.ImageView;

public class ListCellProstor extends ListCell<Prostor> {
    @Override
    protected void updateItem(Prostor prostor, boolean empty) {
        super.updateItem(prostor, empty);
        if(empty){setText(null);
        setGraphic(null);}else{
        setText(prostor.getNazev());
            ImageView iw = new ImageView(getClass().getResource("prostory/"+prostor.getNazev()+".jpg").toExternalForm());
            iw.setFitWidth(150);
            iw.setPreserveRatio(true);
        setGraphic(iw);
        }
    }
}
