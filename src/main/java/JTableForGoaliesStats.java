import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

public class JTableForGoaliesStats {
    JTableForGoaliesStats(Object[][] o) throws FileNotFoundException {
        // Frame initialization

        JFrame frame = new JFrame();

        // Frame Title
        frame.setTitle("JTable Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        for (Object[] objects : o) {  //идём по строкам
//            System.out.print("{\"");
//            for (int j = 0; j < 5; j++) {//идём по столбцам
//                System.out.print(objects[j]); //вывод элемента
//                if (j == 0 || j == 4) {
//                    System.out.print("\"");
//                }
//                System.out.print(", ");
//                if (j == 3) {
//                    System.out.print("\"");
//                }
//
//            }
//            System.out.print("},\n");
//        }
        // Data to be displayed in the JTable
        Object[][] data =
                {
                        {"Korobov Sergei (Лада(ASTAF))", 6, 4, 1, "60,0",},
                        {"Shilin Oleg (Cокол(FORWARDS))", 584, 86, 32, "87,2",},
                };

        // Column Names
        String[] columnNames = {"Игрок(команда)", "И", "ОШ", "ПШ", "%ОШ"};

        try (PrintWriter writer = new PrintWriter("D:/Замена жесткого/testGoalieSTAT.csv")) {
            for (String s : columnNames) {
                writer.write(s + "; ");
            }
            writer.write("\n");
            for (Object[] objects : o) {  //идём по строкам
                for (int j = 0; j < 5; j++) {//идём по столбцам
                    writer.write(String.valueOf(objects[j])); //вывод элемента
                    writer.write("; ");
                }
                writer.write("\n");
            }
        }

        TableModel model = new DefaultTableModel(data, columnNames) {
//            public Class getColumnClass(int column) {
//                Class returnValue;
//                if ((column >= 0) && (column < getColumnCount())) {
//                    returnValue = getValueAt(0, column).getClass();
//                } else {
//                    returnValue = Object.class;
//                }
//                return returnValue;
//            }
        };
        // Initializing the JTable
        JTable table = new JTable(model);
//        table.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer());
//        table.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer());
//        table.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer());
//        table.getColumnModel().getColumn(3).setCellRenderer(new CustomRenderer());
//        table.getColumnModel().getColumn(4).setCellRenderer(new CustomRenderer());
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(
                model);
        table.setRowSorter(sorter);

        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(table);
        frame.add(sp, BorderLayout.CENTER);
        frame.setSize(300, 150);
        frame.setVisible(true);
    }

//    class CustomRenderer extends DefaultTableCellRenderer {
//        private static final long serialVersionUID = 6703872492730589499L;
//
//        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//
//            if (row >= 0 && row <= 15) {
//                cellComponent.setBackground(Color.YELLOW);
//            } else {
//                cellComponent.setBackground(Color.CYAN);
//            }
//            return cellComponent;
//        }
//    }
}
