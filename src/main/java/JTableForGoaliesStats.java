import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class JTableForGoaliesStats {
    JTableForGoaliesStats(Object[][] o) {
        // Frame initialization

        JFrame frame = new JFrame();

        // Frame Title
        frame.setTitle("JTable Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for (Object[] objects : o) {  //идём по строкам
            System.out.print("{\"");
            for (int j = 0; j < 5; j++) {//идём по столбцам
                System.out.print(objects[j]); //вывод элемента
                if (j == 0 || j == 4) {
                    System.out.print("\"");
                }
                System.out.print(", ");
                if (j == 3) {
                    System.out.print("\"");
                }

            }
            System.out.print("},\n");
        }
        // Data to be displayed in the JTable
        Object[][] data =
                {
                        {"Korobov Sergei (Лада(ASTAF))", 6, 4, 1, "60,0",},
                        {"Shilin Oleg (Cокол(FORWARDS))", 499, 70, 25, "87,7",},
                        {"Sinyagin Denis (Динамо СПб(AK46))", 321, 46, 20, "87,5",},
                        {"Skovronsky Andrei (Химик(Muscovite))", 52, 5, 2, "91,2",},
                        {"Bogdanov Nikita (Динамо СПб(AK46))", 39, 10, 3, "79,6",},
                        {"Perevozchikov Denis (Буран(Gagarin))", 156, 21, 9, "88,1",},
                        {"Kiselyov Yevgeny (Cокол(FORWARDS))", 54, 10, 4, "84,4",},
                        {"Sukhanov Andrei (Дизель(matey))", 312, 47, 15, "86,9",},
                        {"Tyalo Igor (Ермак(IIp9n1k))", 55, 4, 4, "93,2",},
                        {"Khomchenko Pavel (Химик(Muscovite))", 139, 25, 5, "84,8",},
                        {"Kostin Denis (Дизель(matey))", 88, 17, 5, "83,8",},
                        {"Mylnikov Sergei (Челмет(TRAKTORIST))", 227, 29, 18, "88,7",},
                        {"Podskrebalin Nikita (Челмет(TRAKTORIST))", 30, 6, 5, "83,3",},
                        {"Trushkov Alexander A. (Химик(Muscovite))", 40, 6, 2, "87,0",},
                        {"Nikolayev Dmitry (СКА-Нева(Pablo))", 346, 39, 19, "89,9",},
                        {"Miftakhov Amir (Барс(TRAKTOR))", 336, 57, 16, "85,5",},
                        {"Artamkin Alexei (Рубин(NO PASSARAN))", 238, 27, 16, "89,8",},
                        {"Litvinov Andrei (ХК Рязань(YarLoc))", 127, 18, 5, "87,6",},
                        {"Lysenkov Nikita (СКА-Нева(Pablo))", 54, 12, 4, "81,8",},
                        {"Molkov Nikolai (ХК Тамбов(Arcanys))", 303, 28, 16, "91,5",},
                        {"Denisov Sergei (ЦСК ВВС(pROxORd))", 243, 37, 12, "86,8",},
                        {"Gross Vladislav (Горняк(Kulyash28))", 253, 30, 11, "89,4",},
                        {"Bespalov Nikita V. (Химик(Muscovite))", 81, 20, 3, "80,2",},
                        {"Korotayev Fyodor (ЦСК ВВС(pROxORd))", 388, 61, 16, "86,4",},
                        {"Volkov Konstantin L. (Югра(Maksik17))", 321, 35, 19, "90,2",},
                        {"Nikitin Vadim (Нефтяник(Ilyuxa))", 82, 8, 6, "91,1",},
                        {"Smiryagin Roman (Лада(ASTAF))", 61, 2, 4, "96,8",},
                        {"Kudashev Danil (Ростов(Darlove))", 541, 79, 24, "87,3",},
                        {"Sokhatsky Vladimir (Югра(Maksik17))", 59, 8, 3, "88,1",},
                        {"Skotnikov Vsevolod (Звезда(NINTENDO))", 269, 31, 12, "89,7",},
                        {"Shostak Konstantin (Молот-Прикамье(Deverter))", 24, 6, 2, "80,0",},
                        {"Sukhachyov Vladislav (Металлург(Maxer))", 28, 2, 3, "93,3",},
                        {"Ustinsky Igor (Молот-Прикамье(Deverter))", 487, 81, 22, "85,7",},
                };

        // Column Names
        String[] columnNames = {"Игрок(команда)", "ОШ", "П", "И", "%ОШ"};

        TableModel model = new DefaultTableModel(data, columnNames) {
            public Class getColumnClass(int column) {
                Class returnValue;
                if ((column >= 0) && (column < getColumnCount())) {
                    returnValue = getValueAt(0, column).getClass();
                } else {
                    returnValue = Object.class;
                }
                return returnValue;
            }
        };
        // Initializing the JTable
        JTable table = new JTable(model);
        table.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer());
        table.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer());
        table.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer());
        table.getColumnModel().getColumn(3).setCellRenderer(new CustomRenderer());
        table.getColumnModel().getColumn(4).setCellRenderer(new CustomRenderer());
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(
                model);
        table.setRowSorter(sorter);

        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(table);
        frame.add(sp, BorderLayout.CENTER);
        frame.setSize(300, 150);
        frame.setVisible(true);
    }

    class CustomRenderer extends DefaultTableCellRenderer {
        private static final long serialVersionUID = 6703872492730589499L;

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (row >= 0 && row <= 15) {
                cellComponent.setBackground(Color.YELLOW);
            } else {
                cellComponent.setBackground(Color.CYAN);
            }
            return cellComponent;
        }
    }
}
