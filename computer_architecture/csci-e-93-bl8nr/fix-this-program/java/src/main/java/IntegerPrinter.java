class IntegerPrinter {
    int from;
    int to;

    IntegerPrinter(int from, int to) {
        this.from = from;
        this.to = to;
    }

    void printIntegers() {
        for(int i = from; i < to; i++) {
            System.out.print(i + " ");
        }

        System.out.println();
    }
}
