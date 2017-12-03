package com.gitrekt.resort.controller;

import com.gitrekt.resort.model.entities.Guest;
import com.gitrekt.resort.model.services.GuestService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;

/**
 * The FXML controller class for the guest registry screen.
 */
public class GuestRegistryScreenController implements Initializable {

    @FXML
    private TableView<Guest> registryTable;

    @FXML
    private TableColumn<Guest, String> guestNameColumn;

    @FXML
    private TableColumn<Guest, Boolean> checkedInColumn;

    @FXML
    private TableColumn<Guest, Long> bookingNumberColumn;

    private ObservableList<Guest> guests;

    /**
     * Initializes the FXML controller class.
     *
     * Called by JavaFX.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Prepare to display the data
        guests = FXCollections.observableArrayList();
        registryTable.setItems(guests);
        guestNameColumn.setCellValueFactory(
            (param) -> {
                return new SimpleStringProperty(
                    String.valueOf(param.getValue().getLastName() + " , "
                        + param.getValue().getFirstName())
                );
            }
        );

        checkedInColumn.setCellValueFactory((param) -> {
            return new SimpleBooleanProperty(param.getValue().isCheckedIn());
        });

        // Use a check box to display booleans rather than a string
        checkedInColumn.setCellFactory(
            (param) -> {
                return new CheckBoxTableCell<>();
            }
        );

        bookingNumberColumn.setCellValueFactory(
            (param) -> {
                return new SimpleLongProperty(
                    param.getValue().getId()
                ).asObject();
            }
        );

        // Load the registry data from the database
        GuestService guestService = new GuestService();
        guests.addAll(guestService.getDailyGuestRegistry());
    }

    /**
     * Checks in the selected guest.
     */
    public void onCheckInButtonClicked() {
        Guest selectedGuest = getSelectedGuest();
        GuestService guestService = new GuestService();
        selectedGuest.setCheckedIn(true);
        guestService.updateGuest(selectedGuest);
        registryTable.refresh();
    }

    /**
     * Checks out the selected guest.
     */
    public void onCheckOutButtonClicked() {
        Guest selectedGuest = getSelectedGuest();
        GuestService guestService = new GuestService();
        selectedGuest.setCheckedIn(false);
        guestService.updateGuest(selectedGuest);
        registryTable.refresh();
    }

    /**
     * Returns to the staff home screen.
     */
    public void onBackButtonClicked() {
        ScreenManager.getInstance().switchToScreen("/fxml/StaffHomeScreen.fxml");
    }

    /**
     * @return The currently selected guest in the registry table view.
     */
    private Guest getSelectedGuest() {
        return registryTable.getSelectionModel().getSelectedItem();
    }
}
