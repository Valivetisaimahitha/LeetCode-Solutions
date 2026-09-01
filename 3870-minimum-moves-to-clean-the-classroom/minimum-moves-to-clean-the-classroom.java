import java.util.*;

class Solution {
    public int minMoves(String[] classroom, int energy) {

        int m = classroom.length;
        int n = classroom[0].length();

        int[][] litter = new int[m][n];

        int startX = 0;
        int startY = 0;
        int count = 0;

        // Find S and assign an index to every L
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                char ch = classroom[i].charAt(j);

                if (ch == 'S') {
                    startX = i;
                    startY = j;
                } 
                else if (ch == 'L') {
                    litter[i][j] = count;
                    count++;
                }
            }
        }

        // No litter
        if (count == 0) {
            return 0;
        }

        /*
         * mask:
         * 1 = litter still needs to be collected
         * 0 = litter already collected
         *
         * Example: 3 litter
         * 111 -> all remaining
         * 101 -> middle one collected
         * 000 -> all collected
         */
        int fullMask = (1 << count) - 1;

        /*
         * visited[row][col][energy][mask]
         */
        boolean[][][][] visited =
            new boolean[m][n][energy + 1][1 << count];

        Queue<int[]> queue = new LinkedList<>();

        // {row, col, currentEnergy, mask}
        queue.offer(new int[]{
            startX, startY, energy, fullMask
        });

        visited[startX][startY][energy][fullMask] = true;

        int[] directions = {-1, 0, 1, 0, -1};

        int moves = 0;

        while (!queue.isEmpty()) {

            int size = queue.size();

            // Process one BFS level
            while (size-- > 0) {

                int[] state = queue.poll();

                int x = state[0];
                int y = state[1];
                int currentEnergy = state[2];
                int mask = state[3];

                // All litter collected
                if (mask == 0) {
                    return moves;
                }

                // Cannot move without energy
                if (currentEnergy == 0) {
                    continue;
                }

                for (int k = 0; k < 4; k++) {

                    int nx = x + directions[k];
                    int ny = y + directions[k + 1];

                    // Outside grid
                    if (nx < 0 || nx >= m ||
                        ny < 0 || ny >= n) {
                        continue;
                    }

                    // Obstacle
                    if (classroom[nx].charAt(ny) == 'X') {
                        continue;
                    }

                    // Moving costs 1 energy
                    int newEnergy = currentEnergy - 1;

                    // R restores energy completely
                    if (classroom[nx].charAt(ny) == 'R') {
                        newEnergy = energy;
                    }

                    int newMask = mask;

                    // Collect litter
                    if (classroom[nx].charAt(ny) == 'L') {
                        int bit = litter[nx][ny];

                        newMask = newMask & ~(1 << bit);
                    }

                    // Avoid visiting same state again
                    if (!visited[nx][ny][newEnergy][newMask]) {

                        visited[nx][ny][newEnergy][newMask] = true;

                        queue.offer(new int[]{
                            nx, ny, newEnergy, newMask
                        });
                    }
                }
            }

            moves++;
        }

        return -1;
    }
}