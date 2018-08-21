import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        c.setForeground(Color.BLACK);

        if (row == 0 || column == 0) {
            c.setFont(new Font("Arial",Font.BOLD,15));
        }

        if(row == column) {
            c.setForeground(Color.BLUE);
        }

        return c;
    }
}
