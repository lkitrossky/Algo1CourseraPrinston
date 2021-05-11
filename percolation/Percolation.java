/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

public class Percolation {
    private boolean[] sites; // false - closed
    private int[] roots;
    private int[] treeSize;
    private final int squareSize;
    private int howManyCurrentOpened; // excluding virtual up. bottom
    private int lastRow; // only for debugging
    private int lastCol; // only for debugging

    // constructor
    public Percolation(int n) {
        if (n < 1)
            throw new IllegalArgumentException("Cannot be less than 1");
        squareSize = n;
        sites = new boolean[n * n + 2]; // sites[0] - virtual up, sites[n^2+1 virtual bottom
        sites[0] = true; // virtual up and bottom are opened by definition
        sites[n * n + 1] = true;
        roots = new int[n * n + 2];
        treeSize = new int[n * n + 2];
        for (int i = 0; i < n * n + 2; i++) {
            treeSize[i] = 1;
            roots[i] = i;
        }
        howManyCurrentOpened = 0;
    }

    private void checkCoordinates(int row, int column) {
        if (row < 1 || row > squareSize)
            throw new IllegalArgumentException("Row out of bounds");
        if (column < 1 || column > squareSize)
            throw new IllegalArgumentException("Column out of bounds");
    }

    private int position(int row, int column) {
        checkCoordinates(row, column);
        return (row - 1) * squareSize + column;
    }


    public void open(int row, int col) {
        if (isOpen(row, col))
            return;
        lastRow = row;  // only for debugging
        lastCol = col;  // only for debugging
        howManyCurrentOpened++;
        int pos = position(row, col);
        sites[pos] = true;
        // the idea is to connect to open neighbours, left, right, up, bottom
        // if the first or last row - connect to virtual
        if (1 == row)
            union(pos, 0); // connect to virtual up
        if (1 < row && isOpen(row - 1, col))
            union(pos, pos - squareSize);
        if (squareSize == row)
            union(pos, squareSize * squareSize + 1); // connect to virtual bottom
        if (squareSize > row && isOpen(row + 1, col))
            union(pos, pos + squareSize);
        if (1 < col && isOpen(row, col - 1))
            union(pos, pos - 1);
        if (squareSize > col && isOpen(row, col + 1))
            union(pos, pos + 1);
    }

    public boolean isOpen(int row, int col) {
        int pos = position(row, col);
        boolean res = sites[pos];
        return res;
    }

    public boolean percolates() {
        return findRoot(0) == findRoot(squareSize * squareSize + 1);
    }

    public int numberOfOpenSites() {
        return howManyCurrentOpened;
    }

    private void union(int pos1, int pos2) {
        if (pos1 < 0 ||
                pos2 < 0 ||
                pos1 > squareSize * squareSize + 1 ||
                pos2 > squareSize * squareSize + 1)
            throw new IllegalArgumentException("Position is out of boundaries");
        int root1 = findRoot(pos1);
        int root2 = findRoot(pos2);
        if (root1 == root2)
            return; // already the same root, that is the same tree, nothing to union
        int start;
        int newRoot;
        int newWeight = treeSize[root1] + treeSize[root2];
        // real union of two trees
        if (treeSize[root1] > treeSize[root2]) {
            // tree 2 is smaller, connect it to tree 1
            // 0 is virtual, always precedes, has no root except 0
            start = pos2;  // tree starting with pos2 will be updated
            newRoot = root1;
            roots[pos2] = pos1;
        }
        else {
            // tree 1 is smaller, connect it to tree 2
            start = pos1;  // tree starting with pos1 will be updated
            newRoot = root2;
            roots[pos1] = pos2;
        }
        // reconnection of one of trees to the new root
        while (roots[start] != start) {
            int next = roots[start];
            roots[start] = newRoot;
            treeSize[start] = newWeight; // not needed except to root, for debugging
            start = next;
        }
        // the main operation one former root connected to another
        // only one is updated
        roots[root1] = newRoot;
        treeSize[root1] = newWeight;
        roots[root2] = newRoot;
        treeSize[root2] = newWeight;
    }

    public boolean isFull(int row, int col) {
        checkCoordinates(row, col);
        int root = findRoot(row, col);
        int root0 = findRoot(0);
        return root0 == root;
    }

    private int findRoot(int pos) {
        if (pos < 0 || pos > squareSize * squareSize + 1)
            throw new IllegalArgumentException("Position ot of boundaries");
        while (roots[pos] != pos) { // go up till the end
            pos = roots[pos];
        }
        return roots[pos]; // the end found
    }

    private int findRoot(int row, int col) {
        int pos = position(row, col);
        while (roots[pos] != pos)
            pos = roots[pos];
        return pos;
    }

    // test functions, first of all printing
    private void printIsFull(int row, int col) {
        checkCoordinates(row, col);
        boolean res = isFull(row, col);
        if (res)
            System.out.print(String.format("row %d column %d is full\n", row, col));
        else
            System.out.print(String.format("row %d column %d is not full\n", row, col));
    }

    private void print() {
        System.out.print("\nsquareSize = " + squareSize);
        System.out.print(" Last row = " + lastRow + " Last column = " + lastCol + "\n");
        System.out.print("; Percolates? : " + percolates());
        System.out.print("\n\tOverall state:\n");

        for (int i = 1; i <= squareSize; i++)
            System.out.print(String.format("% 4d  ", i));
        System.out.print("\n");
        for (int i = 1; i <= squareSize; i++) {
            for (int j = 1; j <= squareSize; j++) {
                int pos = position(i, j);
                System.out.print(sites[pos]);
                if (sites[pos])
                    System.out.print("  ");
                else
                    System.out.print(" ");
            }
            System.out.print(String.format("% 6d\n", i));
        }
        System.out.print("\n\tRoots:\n");
        System.out.print("\tRoot of up: " + findRoot(0));
        System.out.print("\tRoot of bottom: " + findRoot(roots[roots.length - 1]) + "\n");
        for (int i = 1; i <= squareSize; i++)
            System.out.print(String.format("% d ", i));
        System.out.print("\n");
        for (int i = 1; i <= 3 * squareSize; i++)
            System.out.print("-");
        System.out.print("\n");
        for (int i = 1; i <= squareSize; i++) {
            for (int j = 1; j <= squareSize; j++) {
                int pos = position(i, j);
                System.out.print(String.format("% 3d", roots[pos]));
            }
            System.out.print(String.format("% 6d\n", i));
        }
        System.out.print("\n\tTree sizes: " + squareSize + " ");
        System.out.print("\tTree of up: " + treeSize[0]);
        System.out.print("\tTree of bottom: " + treeSize[squareSize * squareSize + 1] + "\n");
        System.out.print(" ");
        for (int i = 1; i <= squareSize; i++)
            System.out.print(String.format("% d ", i));
        System.out.print("\n");
        for (int i = 1; i <= 3 * squareSize; i++)
            System.out.print("-");
        System.out.print("\n");
        for (int i = 1; i <= squareSize; i++) {
            for (int j = 1; j <= squareSize; j++) {
                int pos = position(i, j);
                System.out.print(String.format("% 3d", treeSize[pos]));
            }
            System.out.print(String.format("% 6d\n", i));
        }
    }

    public static void main(String[] args) {
        Percolation perc7 = new Percolation(7);
        perc7.open(6, 1);
        perc7.open(7, 1);
        perc7.open(7, 2);
        perc7.open(7, 4);
        perc7.open(1, 1);
        perc7.open(1, 5);
        perc7.open(2, 5);
        perc7.open(3, 5);
        perc7.open(4, 5);
        perc7.open(5, 5);
        perc7.open(6, 5);
        perc7.print();
        perc7.printIsFull(6, 1);
        perc7.open(7, 5);
        perc7.print();
        perc7.printIsFull(6, 1);
        perc7.open(2, 1);
        perc7.open(4, 1);
        perc7.open(5, 1);
        perc7.open(3, 1);
        perc7.print();
    }
}
