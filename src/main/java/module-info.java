module me.vmachohan.assignment6 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens me.vmachohan.assignment6 to javafx.fxml;
    exports me.vmachohan.assignment6;
}