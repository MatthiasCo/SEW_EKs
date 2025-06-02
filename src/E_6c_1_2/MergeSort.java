package E_6c_1_2;

public class MergeSort<T extends Comparable<? super T>> {
    private final java.util.List<T> data;

    public MergeSort(java.util.List<T> data) {
        this.data = data;
    }

    public java.util.List<T> start() {
        java.util.List<T> copy = new java.util.ArrayList<>(data);
        mergeSort(copy, 0, copy.size() - 1);
        return copy;
    }

    private void mergeSort(java.util.List<T> arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(arr, left, mid);
            mergeSort(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private void merge(java.util.List<T> arr, int left, int mid, int right) {
        java.util.List<T> temp = new java.util.ArrayList<>(right - left + 1);
        int i = left, j = mid + 1;
        while (i <= mid && j <= right) {
            if (arr.get(i).compareTo(arr.get(j)) <= 0) {
                temp.add(arr.get(i++));
            } else {
                temp.add(arr.get(j++));
            }
        }
        while (i <= mid) {
            temp.add(arr.get(i++));
        }
        while (j <= right) {
            temp.add(arr.get(j++));
        }
        for (int k = 0; k < temp.size(); k++) {
            arr.set(left + k, temp.get(k));
        }
    }
}