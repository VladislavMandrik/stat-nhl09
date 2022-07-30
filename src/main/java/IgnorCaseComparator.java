import java.util.Comparator;

class IgnoreCaseComparator implements Comparator<String> {
    public int compare(String strA, String strB) {
        //���������� �� ������ �������, �� ������ ������ �� 4 �������
        return strA.substring(0,4).compareToIgnoreCase(strB.substring(0,4));
    }
}