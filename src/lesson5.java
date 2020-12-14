public class lesson5 {
    public static void main(String[] args) {
        final int size = 10000000;
        final int h = size / 2;
        float[] arr = new float[size];


        for (int k = 0; k < arr.length; k++) {
            arr[k] = 1;
        }
        float[] arr1Th = arr;

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < arr1Th.length; i++) {
            arr1Th[i] = (float) (arr1Th[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        System.out.println("Время операции в 1 поток (мс): " + (System.currentTimeMillis() - startTime));

        float[] a1 = new float[h];
        float[] a2 = new float[h];

        startTime = System.currentTimeMillis();

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < a1.length; i++) {
                    a1[i] = (float) (a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < a2.length; i++) {
                    a2[i] = (float) (a2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        System.out.println("Время операции в 2 потока (мс): " + (System.currentTimeMillis() - startTime));
        System.out.println("");
        System.out.println("Контрольные значения");
        System.out.println("Массив arr1Th проход в один поток");
        System.out.println("Первый элемент: " + arr1Th[0]);
        System.out.println("Последний элемент: " + arr1Th[9999999]);
        System.out.println("");
        System.out.println("Массив arr проход в два потока");
        System.out.println("Первый элемент: " + arr[0]);
        System.out.println("Последний элемент: " + arr[9999999]);
    }
}
