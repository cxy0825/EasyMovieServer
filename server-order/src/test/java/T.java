import java.util.Arrays;

public class T {
    public static void main(String[] args) {
        int[] arr = {5, 6, 8, 1, 2, 44, 6, 7};
//        bubble(arr);
//        bubble_V2(arr);
        insert(arr);
//        quick(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 冒泡排序
     *
     * @param arr 数组
     */
    public static void bubble(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            boolean flag = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {

                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    flag = true;
                }

            }

            if (!flag) {
                break;
            }
            System.out.println("第" + i + 1 + "轮");
        }

    }

    /**
     * 冒泡排序V2 动态规划
     *
     * @param arr 数组
     */
    public static void bubble_V2(int[] arr) {
        int n = arr.length - 1;
        while (true) {
            int last = 0;//最后一次交换索引的位置
            for (int i = 0; i < n; i++) {
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                    last = i;
                }
            }
            n = last;
            if (n == 0) {
                break;
            }
        }


    }

    /**
     * 选择排序
     *
     * @param arr 数组
     */
    public static void selection(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;//最小数的索引
            for (int j = min + 1; j < arr.length - 1; j++) {
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }

            if (min != i) {
                swap(arr, min, i);
            }
        }
    }

    /**
     * 插入排序
     *
     * @param arr 数组
     */
    public static void insert(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int d = arr[i];//等待插入的元素值
            int n = i - 1;//已经排序区域的索引
            //从后往前面找
            while (n >= 0) {
                //当待插入元素的值小于的时候就往后移动一个位置空出来
                if (d < arr[n]) {
                    arr[n + 1] = arr[n];
                } else {
                    break;
                }
                n--;
            }
            arr[n + 1] = d;
        }
    }

    /**
     * 快速排序
     *
     * @param arr 数组
     */
    public static void quick(int[] arr, int l, int h) {
        if (l >= h) {
            return;
        }
        int d = partition(arr, l, h);
        quick(arr, l, d - 1);
        quick(arr, d + 1, h);
    }

    public static int partition(int arr[], int l, int h) {
        int pv = arr[l];
        int i = l;
        int j = h;
        while (i < j) {
            //j从右边找比pv小
            while (i < j && arr[j] > pv) {
                j--;
            }
            //j从左边找比pv大
            while (i < j && arr[i] <= pv) {
                i++;
            }
//            交换
            swap(arr, i, j);
        }
        //交换中枢位置
        swap(arr, l, j);
        return j;
    }
}
