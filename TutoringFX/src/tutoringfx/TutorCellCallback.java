package tutoringfx;
 
import java.util.Collection;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import models.Tutor;

/**
 * TutorCellCallback
 * 
 * @author Jacob
 */
class TutorCellCallback implements Callback<ListView<Tutor>, ListCell<Tutor>> {
 
    private Collection<Integer> highlightedIds = null;
 
    void setHighlightedIds(Collection<Integer> highlightedIds) {
        this.highlightedIds = highlightedIds;
    }
    
    @Override
    public ListCell<Tutor> call(ListView<Tutor> p) {
        ListCell<Tutor> cell = new ListCell<Tutor>() {
            @Override
            protected void updateItem(Tutor tutor, boolean empty) {
                super.updateItem(tutor, empty);
                if (empty) {
                    this.setText(null);
                    return;
                }
                this.setText( tutor.getName() );
  
                if (highlightedIds == null) {
                    return;
                }
 
                String css = ""
                    + "-fx-text-fill: #c00;"
                    + "-fx-font-weight: bold;"
                    + "-fx-font-style: italic;"
                    ;
 
                if (highlightedIds.contains(tutor.getId())) {
                    this.setStyle(css);
                }
                else {
                    this.setStyle(null);
                }
            }
        };
        return cell;
    }
}