public class Testt {
    public static void main(String[] args) {
        int[][] arr = new int[10][15];
        //假设0为可以购买的座位
        //1为已经购买的座位
        //2为暂时不可购买的座位
        arr[0][1] = 2;
        arr[0][2] = 2;
        arr[0][14] = 2;
        arr[0][13] = 2;
        arr[1][2] = 2;
        arr[1][14] = 2;
        arr[1][13] = 2;

        arr[0][3] = 1;
        arr[0][4] = 1;
        arr[0][5] = 1;
        arr[0][6] = 1;
        arr[1][12] = 1;
        arr[1][11] = 1;
        arr[1][10] = 1;
        for (int[] ints : arr) {
            for (int j : ints) {
                System.out.print(j + "   ");
            }
            System.out.println("");
        }
        //压缩存储
        System.out.println("==========================");
        int sum = 0;
        int[][] arr2 = new int[10 * 15][3];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] != 0) {
                    //总数++
                    sum++;
                    //第0项是 有多少行  多少列  有多少个有效值
                    //放入压缩数组中
                    arr2[sum][0] = i;
                    arr2[sum][1] = j;
                    arr2[sum][2] = arr[i][j];
                }
            }
        }
        arr2[0][0] = arr.length;
        arr2[0][1] = arr[0].length;
        arr2[0][2] = sum;


        for (int[] ints : arr2) {
            for (int j : ints) {
                System.out.print(j + "   ");
            }
            System.out.println("");
        }
    }
}
