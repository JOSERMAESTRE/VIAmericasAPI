package Helpers;

import java.util.ArrayList;
import java.util.List;

public class SortingHelper {

    public static List<String> bubbleSortNames(List<String> names) {
        List<String> sortedList = new ArrayList<>(names);
        int n = sortedList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (sortedList.get(j).compareTo(sortedList.get(j + 1)) > 0) {
                    String temp = sortedList.get(j);
                    sortedList.set(j, sortedList.get(j + 1));
                    sortedList.set(j + 1, temp);
                }
            }
        }
        return sortedList;
    }
}
