class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            int j = i;
            int k = i + 1;
            int l = i + 2;
            if (i + j + k + l > 5) {
                System.out.println(i);
            }
        }
    }
}