class RotatingSquare {
    companion object {
        val grid = Array(4) { BooleanArray(4) }

        fun encode(painText: String) {
            grid[0][0] = true
            grid[1][3] = true
            grid[2][2] = true
            grid[3][1] = true

            rotateGrid(grid)
        }

        fun rotateGrid(grid: Array<BooleanArray>): Array<BooleanArray> {
            val mat = grid.clone()
            val size = grid.size
            for (i in 0 until size / 2) {
                for (j in i until size - i - 1) {

                    val temp = mat[i][j]
                    mat[i][j] = mat[size - 1 - j][i]
                    mat[size - 1 - j][i] = mat[size - 1 - i][size - 1 - j]
                    mat[size - 1 - i][size - 1 - j] = mat[j][size - 1 - i]
                    mat[j][size - 1 - i] = temp
                }
            }
            return mat
        }
    }
}