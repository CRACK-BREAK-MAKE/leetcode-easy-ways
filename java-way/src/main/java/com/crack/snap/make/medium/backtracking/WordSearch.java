package com.crack.snap.make.medium.backtracking;

/**
 * @author Mohan Sharma
 */
public class WordSearch {

    public boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (searchRecursively(board, word, i, j, 0))
                    return true;
            }
        }
        return false;
    }

    private boolean searchRecursively(char[][] board, String word, int r, int c, int index) {
        if (word.length() == index)
            return true;
        if (r < 0 || c < 0 || r >= board.length || c >= board[0].length || board[r][c] != word.charAt(index))
            return false;
        //tracking visited node e.g. from r0c2 we can do 4 adjacent nodes r-1c2, r1c2, r0c3, r0c1.
        // Now from r1c2 it will again go to r0c2 which will result in loop hence we need to track visited.
        // In this case we set r0c2 to '*' hence when next time roc2 is called board[r][c] != word.charAt(index) returns false
        board[r][c] = '*';
        boolean result =
                searchRecursively(board, word, r+1, c, index+1) ||
                        searchRecursively(board, word, r-1, c, index+1) ||
                        searchRecursively(board, word, r, c+1, index+1) ||
                        searchRecursively(board, word, r, c-1, index+1);
        board[r][c] = word.charAt(index);
        return result;
    }

    public static void main(String[] args) {
        var obj = new WordSearch();
        System.out.println(obj.exist(new char[][] {
                { 'A', 'B', 'C', 'E' },
                { 'S', 'F', 'C', 'S' },
                { 'A', 'D', 'E', 'E'}
        }, "SEE"));
    }
}
