class Main {
    public static void main(String[] args) {
        for (int z = 0; z < 10; z++) {
            int j = z;
            int k = z + 1;
            int l = z + 2;
            if (z + j + k + l > 5) {
                System.out.println(z);
            }
        }
    }
}