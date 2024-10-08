package com.example.hellokittyadventura.main;

import com.example.hellokittyadventura.logika.Vec;
import javafx.scene.control.ListCell;

public class ListCellVec extends ListCell<Vec> {
    @Override
    protected void updateItem(Vec vec, boolean empty) {
        super.updateItem(vec, empty);
        if(empty){setText(null);
            setGraphic(null);}else{
            setText(vec.getNazev());
        }
    }
}
