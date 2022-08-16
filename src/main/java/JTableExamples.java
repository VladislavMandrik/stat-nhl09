import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

public class JTableExamples {
    JTableExamples(Object[][] o) throws FileNotFoundException {
        // Frame initialization

        JFrame frame = new JFrame();

        // Frame Title
        frame.setTitle("JTable Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        for (Object[] objects : o) {  //идём по строкам
//            System.out.print("{\"");
//            for (int j = 0; j < 6; j++) {//идём по столбцам
//                System.out.print(objects[j]); //вывод элемента
//                if (j == 0) {
//                    System.out.print("\"");
//                }
//                System.out.print(", ");
//
//            }
//            System.out.print("},\n");
//        }

        // Data to be displayed in the JTable
        Object[][] data =
                {
                        {"COL", 3, 2, 13, 10, 4,},
                        {"PHX", 1, 1, 6, 5, 2,},
                        {"NYR", 2, 0, 2, 18, 0,},
                        {"CBJ", 1, 0, 0, 9, 0,},
                        {"WSH", 2, 0, 9, 13, 0,},
                        {"DAL", 3, 0, 5, 36, 0,},
                        {"OTT", 2, 0, 3, 12, 0,},
                        {"TB", 2, 1, 20, 20, 2,},
                        {"EDM", 44, 35, 318, 145, 70,},
                        {"PHI", 2, 0, 6, 15, 0,},
                        {"BUF", 2, 0, 6, 15, 0,},
                        {"MIN", 3, 0, 14, 30, 0,},
                        {"ANA", 3, 0, 9, 30, 0,},
                        {"CGY", 1, 1, 2, 1, 2,},
                        {"LA", 3, 0, 10, 26, 0,},
                        {"TOR", 2, 0, 4, 11, 0,},
                        {"SJ", 2, 1, 9, 9, 2,},
                        {"ATL", 3, 0, 3, 23, 0,},
                        {"FLA", 2, 2, 14, 10, 4,},
                        {"PIT", 1, 0, 1, 7, 0,},
                        {"NJ", 2, 0, 2, 10, 0,},
                        {"NYI", 2, 1, 7, 8, 2,},
                };

        // Column Names
        String[] columnNames = {"Команда", "И", "В", "ОТ/Б", "П", "ШЗ", "ШП", "О"};

        try (PrintWriter writer = new PrintWriter("D:/Замена жесткого/test.csv")) {
            writer.write(Arrays.toString(columnNames) + "\n");
            for (Object[] objects : o) {  //идём по строкам
                for (int j = 0; j < 8; j++) {//идём по столбцам
                    writer.write(String.valueOf(objects[j])); //вывод элемента
                    writer.write(", ");
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
//        table.getColumnModel().getColumn(5).setCellRenderer(new CustomRenderer());
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