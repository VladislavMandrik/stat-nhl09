import java.util.Comparator;

class IgnoreCaseComparator implements Comparator<String> {
    public int compare(String strA, String strB) {
        //сравниваем не строки целиком, но только первые их 4 символа
        return strA.substring(0,4).compareToIgnoreCase(strB.substring(0,4));
    }
}