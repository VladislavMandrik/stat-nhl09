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

        for (Object[] objects : o) {  //��� �� �������
            System.out.print("{\"");
            for (int j = 0; j < 5; j++) {//��� �� ��������
                System.out.print(objects[j]); //����� ��������
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
                        {"Korobov Sergei (����(ASTAF))", 6, 4, 1, "60,0",},
                        {"Shilin Oleg (C����(FORWARDS))", 499, 70, 25, "87,7",},
                        {"Sinyagin Denis (������ ���(AK46))", 321, 46, 20, "87,5",},
                        {"Skovronsky Andrei (�����(Muscovite))", 52, 5, 2, "91,2",},
                        {"Bogdanov Nikita (������ ���(AK46))", 39, 10, 3, "79,6",},
                        {"Perevozchikov Denis (�����(Gagarin))", 156, 21, 9, "88,1",},
                        {"Kiselyov Yevgeny (C����(FORWARDS))", 54, 10, 4, "84,4",},
                        {"Sukhanov Andrei (������(matey))", 312, 47, 15, "86,9",},
                        {"Tyalo Igor (�����(IIp9n1k))", 55, 4, 4, "93,2",},
                        {"Khomchenko Pavel (�����(Muscovite))", 139, 25, 5, "84,8",},
                        {"Kostin Denis (������(matey))", 88, 17, 5, "83,8",},
                        {"Mylnikov Sergei (������(TRAKTORIST))", 227, 29, 18, "88,7",},
                        {"Podskrebalin Nikita (������(TRAKTORIST))", 30, 6, 5, "83,3",},
                        {"Trushkov Alexander A. (�����(Muscovite))", 40, 6, 2, "87,0",},
                        {"Nikolayev Dmitry (���-����(Pablo))", 346, 39, 19, "89,9",},
                        {"Miftakhov Amir (����(TRAKTOR))", 336, 57, 16, "85,5",},
                        {"Artamkin Alexei (�����(NO PASSARAN))", 238, 27, 16, "89,8",},
                        {"Litvinov Andrei (�� ������(YarLoc))", 127, 18, 5, "87,6",},
                        {"Lysenkov Nikita (���-����(Pablo))", 54, 12, 4, "81,8",},
                        {"Molkov Nikolai (�� ������(Arcanys))", 303, 28, 16, "91,5",},
                        {"Denisov Sergei (��� ���(pROxORd))", 243, 37, 12, "86,8",},
                        {"Gross Vladislav (������(Kulyash28))", 253, 30, 11, "89,4",},
                        {"Bespalov Nikita V. (�����(Muscovite))", 81, 20, 3, "80,2",},
                        {"Korotayev Fyodor (��� ���(pROxORd))", 388, 61, 16, "86,4",},
                        {"Volkov Konstantin L. (����(Maksik17))", 321, 35, 19, "90,2",},
                        {"Nikitin Vadim (��������(Ilyuxa))", 82, 8, 6, "91,1",},
                        {"Smiryagin Roman (����(ASTAF))", 61, 2, 4, "96,8",},
                        {"Kudashev Danil (������(Darlove))", 541, 79, 24, "87,3",},
                        {"Sokhatsky Vladimir (����(Maksik17))", 59, 8, 3, "88,1",},
                        {"Skotnikov Vsevolod (������(NINTENDO))", 269, 31, 12, "89,7",},
                        {"Shostak Konstantin (�����-��������(Deverter))", 24, 6, 2, "80,0",},
                        {"Sukhachyov Vladislav (���������(Maxer))", 28, 2, 3, "93,3",},
                        {"Ustinsky Igor (�����-��������(Deverter))", 487, 81, 22, "85,7",},
                };

        // Column Names
        String[] columnNames = {"�����(�������)", "��", "�", "�", "%��"};

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
