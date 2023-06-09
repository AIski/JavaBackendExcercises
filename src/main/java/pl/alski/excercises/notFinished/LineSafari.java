package pl.alski.excercises.notFinished;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class LineSafari {

//You are given a grid, which always includes exactly two end-points indicated by X
//
//You simply need to return true/false if you can detect a one and only one "valid" line joining those points.
//
//A line can have the following characters :
//
//- = left / right
//| = up / down
//+ = corner
//Rules for valid lines
//The most basic kind of valid line is when the end-points are already adjacent
//X
//X
//XX
//The corner character (+) must be used for all corners (but only for corners).
//If you find yourself at a corner then you must turn.
//It must be possible to follow the line with no ambiguity (lookahead of just one step, and never treading on the same spot twice).
//The line may take any path between the two points.
//Sometimes a line may be valid in one direction but not the other. Such a line is still considered valid.
//Every line "character" found in the grid must be part of the line. If extras are found then the line is not valid.
//Examples

    //Good lines
//X---------X
//X
//|
//|
//X
//   +--------+
//X--+        +--+
//               |
//               X
//   +-------------+
//   |             |
//X--+      X------+
//   +-------+
//   |      +++---+
//X--+      +-+   X
//Bad lines
//X-----|----X
//X
//|
//+
//X
//   |--------+
//X---        ---+
//               |
//               X
//   +------
//   |
//X--+      X
//      +------+
//      |      |
//X-----+------+
//      |
//      X
//Hint
//Imagine yourself walking a path where you can only see your very next step. Can you know which step you must take, or not?

    public static boolean line(final char[][] grid) {
        final Point startPoint = findStart(grid);
        final Point endPoint = findEnd(grid);
        if (checkGameWasSetProperly(grid, startPoint, endPoint)) {
            return false;
        }
        else return walkTheLine(startPoint, endPoint, grid);
    }

    private static boolean checkGameWasSetProperly(char[][] grid, Point startPoint, Point endPoint) {
        return startPoint == null || endPoint == null || !gotOnlyTwoXs(grid);
    }

    private static Point findStart(char[][] grid) {
        for (int x = 0; x < grid[0].length; x++) {
            for (int y = 0; y < grid.length; y++) {
                if (grid[y][x] == 'X') {
                    return (new Point(x, y));
                }
            }
        }
        return null;
    }

    private static Point findEnd(char[][] grid) {
        for (int x = grid[0].length - 1; x >= 0; x--) {
            for (int y = grid.length - 1; y >= 0; y--) {
                if (grid[y][x] == 'X') {
                    return (new Point(x, y));
                }
            }
        }
        return null;
    }

    private static boolean gotOnlyTwoXs(char[][] grid) {
        List<Point> xs = new ArrayList<>();
        for (int x = 0; x < grid[0].length; x++) {
            for (int y = 0; y < grid.length; y++) {
                if (grid[y][x] == 'X') {
                    xs.add(new Point(x, y));
                }
            }
        }
        return xs.size() == 2;
    }

    private static boolean walkTheLine(Point startPoint, Point endPoint, char[][] grid) {
        Point currentPoint = (Point) startPoint.clone();
        Boolean gameResult = tryToSolveTheGame(grid, currentPoint, endPoint);
        System.out.println("Game result: " + gameResult);
        if (gameResult) return true;

        else {
            System.out.println(" ");
            currentPoint = (Point) startPoint.clone();
            gameResult = tryFromTheOtherSide(grid, currentPoint, endPoint);
            System.out.println("Game result: " + gameResult);
            return gameResult;
        }
    }

    private static boolean tryToSolveTheGame(char[][] grid, Point currentPoint, Point endPoint) {
        System.out.println("Start: " + currentPoint.x + "x:" + currentPoint.y + "y");
        System.out.println("End: " + endPoint.x + "x:" + endPoint.y + "y");

        Stack<Point> recentMoves = new Stack<>();
        List<Point> possibleMoves = getPossibleMoves(currentPoint, recentMoves, grid);
        Point lastMove;

        while (possibleMoves.size() > 0) {
            possibleMoves = getPossibleMoves(currentPoint, recentMoves, grid);
            lastMove = getLastMoveFromStack(recentMoves);
            if (possibleMoves.size() > 1) {
                return false;
            }
            if ((possibleMoves.size() == 1) && checkThePathIsValid(grid, currentPoint, lastMove, possibleMoves.get(0))) {
                makeMoveAndSaveItToRecentMoves(currentPoint, recentMoves, possibleMoves.get(0));
            }
        }

        addEndPointToStackIfWeMadeIt(currentPoint, endPoint, recentMoves);
        System.out.println("No moves possible. Checking if GameWasFinished");
        System.out.println(" ");

        return checkGameWasFinished(grid, currentPoint, recentMoves);
    }

    private static List<Point> getPossibleMoves(Point currentPoint, Stack<Point> recentMoves, char[][] grid) {
        List<Point> possibleMoves = new ArrayList<>();

        if ((currentPoint.x + 1 < grid[0].length) && (grid[currentPoint.y][currentPoint.x + 1] != ' ')) {
            possibleMoves.add(new Point(currentPoint.x + 1, currentPoint.y));
        }
        if ((currentPoint.x - 1 >= 0) && (grid[currentPoint.y][currentPoint.x - 1] != ' ')) {
            possibleMoves.add(new Point(currentPoint.x - 1, currentPoint.y));
        }
        if ((currentPoint.y + 1 < grid.length) && (grid[currentPoint.y + 1][currentPoint.x] != ' ')) {
            possibleMoves.add(new Point(currentPoint.x, currentPoint.y + 1));
        }
        if ((currentPoint.y - 1 >= 0) && (grid[currentPoint.y - 1][currentPoint.x] != ' ')) {
            possibleMoves.add(new Point(currentPoint.x, currentPoint.y - 1));
        }
        for (Point m : recentMoves) {
            possibleMoves.remove(m);
        }
        possibleMoves = filterMoves(grid, possibleMoves, currentPoint, getLastMoveFromStack(recentMoves));
        System.out.println("possible moves: ");
        System.out.println(possibleMoves);
        return possibleMoves;
    }

    private static List<Point> filterMoves(char[][] grid, List<Point> possibleMoves, Point currentPoint, Point previousPoint) {
        List<Point> filteredMoves = possibleMoves.stream().filter(m -> checkThePathIsValid(grid, currentPoint, previousPoint, m)).collect(Collectors.toList());
        System.out.println(filteredMoves.size() + " possible moves.");
        return filteredMoves;
    }

    private static Point getLastMoveFromStack(Stack<Point> recentMoves) {
        Point lastMove;
        if (recentMoves.isEmpty()) {
            lastMove = null;
        } else lastMove = recentMoves.peek();
        return lastMove;
    }

    private static boolean checkThePathIsValid(char[][] grid, Point currentPoint, Point previousPoint, Point nextPoint) {
        // simple bans
        if (
                (
                        ((gridAtPoint(grid, currentPoint) == '-') && (gridAtPoint(grid, nextPoint) == '|')) ||
                                ((gridAtPoint(grid, currentPoint) == '|') && (gridAtPoint(grid, nextPoint) == '-'))
                                //harder 2char bans
                                || ((gridAtPoint(grid, currentPoint) == '|') && (gridAtPoint(grid, nextPoint) == '+') && (currentPoint.y == nextPoint.y))
                                || ((gridAtPoint(grid, currentPoint) == '+') && (gridAtPoint(grid, nextPoint) == '|') && (currentPoint.y == nextPoint.y))
                                || ((gridAtPoint(grid, currentPoint) == '+') && (gridAtPoint(grid, nextPoint) == '-') && (currentPoint.x == nextPoint.x))
                                || ((gridAtPoint(grid, currentPoint) == '-') && (gridAtPoint(grid, nextPoint) == '+') && (currentPoint.x == nextPoint.x))
                                || ((gridAtPoint(grid, currentPoint) == '-') && (gridAtPoint(grid, nextPoint) == '-') && (currentPoint.x == nextPoint.x))
                                || ((gridAtPoint(grid, currentPoint) == '|') && (gridAtPoint(grid, nextPoint) == '|') && (currentPoint.y == nextPoint.y))
                                || ((gridAtPoint(grid, currentPoint) == 'X') && (gridAtPoint(grid, nextPoint) == '|') && (currentPoint.y == nextPoint.y))
                                || ((gridAtPoint(grid, currentPoint) == '|') && (gridAtPoint(grid, nextPoint) == 'X') && (currentPoint.y == nextPoint.y))
                                || ((gridAtPoint(grid, currentPoint) == 'X') && (gridAtPoint(grid, nextPoint) == '-') && (currentPoint.x == nextPoint.x))
                                || ((gridAtPoint(grid, currentPoint) == '-') && (gridAtPoint(grid, nextPoint) == 'X') && (currentPoint.x == nextPoint.x))
                )
                        ||

                        (
                                (previousPoint != null) && (
                                        ((gridAtPoint(grid, previousPoint) == '-') && (gridAtPoint(grid, currentPoint) == '+') && (gridAtPoint(grid, nextPoint) == '-'))
                                                || (
                                                (gridAtPoint(grid, previousPoint) == '|') && (gridAtPoint(grid, currentPoint) == '+') && (gridAtPoint(grid, nextPoint) == '|'))
                                                // Harder corner bans
                                                || (
                                                ((gridAtPoint(grid, previousPoint) == '-') || (gridAtPoint(grid, previousPoint) == 'X')) && (gridAtPoint(grid, currentPoint) == '+')
                                                        && ((gridAtPoint(grid, nextPoint) == '|') || (gridAtPoint(grid, nextPoint) == 'X'))
                                                        && ((previousPoint.x == nextPoint.x) || (previousPoint.y == nextPoint.y)))
                                                || (
                                                ((gridAtPoint(grid, previousPoint) == '|') || (gridAtPoint(grid, previousPoint) == 'X'))
                                                        && (gridAtPoint(grid, currentPoint) == '+')
                                                        && ((gridAtPoint(grid, nextPoint) == '-') || (gridAtPoint(grid, nextPoint) == 'X'))
                                                        && ((previousPoint.x == nextPoint.x) || (previousPoint.y == nextPoint.y)))
                                                || (
                                                (
                                                        (gridAtPoint(grid, previousPoint) == '+' && gridAtPoint(grid, currentPoint) == '+')
                                                                || (gridAtPoint(grid, currentPoint) == '+' && gridAtPoint(grid, nextPoint) == '+')
                                                )
                                                        && (
                                                        ((previousPoint.x == currentPoint.x) && (currentPoint.x == nextPoint.x))
                                                                || ((previousPoint.y == currentPoint.y && (currentPoint.y == nextPoint.y)))
                                                )
                                        )
                                )
                        )
        ) {
            return false;
        }
        return true;
    }

    private static void makeMoveAndSaveItToRecentMoves(Point currentPoint, Stack<Point> recentMoves, Point nextMove) {
        addPointToRecentMoves(currentPoint, recentMoves);
        currentPoint.x = nextMove.x;
        currentPoint.y = nextMove.y;
        System.out.println("Moved to point: " + currentPoint.x + ":" + currentPoint.y);
        System.out.println(" ");

    }

    private static void addPointToRecentMoves(Point currentPoint, Stack<Point> recentMoves) {
        recentMoves.add(new Point(currentPoint.x, currentPoint.y));
        System.out.println("Added point to stack: " + currentPoint.x + ":" + currentPoint.y);
    }

    private static void addEndPointToStackIfWeMadeIt(Point currentPoint, Point endPoint, Stack<Point> recentMoves) {
        if (currentPoint.x == endPoint.x && currentPoint.y == endPoint.y) {
            addPointToRecentMoves(currentPoint, recentMoves);
        }
    }

    private static Boolean tryFromTheOtherSide(char[][] grid, Point startPoint, Point endPoint) {
        System.out.println("Starting from the other side");
        Point tempPoint = (Point) startPoint.clone();
        startPoint = (Point) endPoint.clone();
        endPoint = tempPoint;
        return tryToSolveTheGame(grid, startPoint, endPoint);
    }

    private static boolean checkGameWasFinished(char[][] grid, Point currentPoint, Stack<Point> recentMoves) {
        System.out.println("Checking if game was finished...");
        boolean allFieldsWereUsed = checkAllFieldsWereUsed(grid, recentMoves);
        boolean endsWithX = grid[currentPoint.y][currentPoint.x] == 'X';
        boolean result = allFieldsWereUsed && endsWithX;
        System.out.println("Game was successfully finished: " + result);
        return result;
    }

    private static boolean checkAllFieldsWereUsed(char[][] grid, Stack<Point> recentMoves) {
        List<Point> allFieldsToUse = getAllFieldsToUse(grid);
        for (Point p : allFieldsToUse) {
            if (!recentMoves.contains(p)) {
                System.out.println("Some fields were not used!");
                return false;
            }
        }
        System.out.println("Nice! All fields were used!");
        return true;
    }

    private static List<Point> getAllFieldsToUse(char[][] grid) {
        List<Point> allFieldsToUse = new ArrayList<>();
        for (int x = 0; x < grid[0].length; x++) {
            for (int y = 0; y < grid.length; y++) {
                if (grid[y][x] != ' ') {
                    allFieldsToUse.add(new Point(x, y));
                }
            }
        }
        System.out.println(allFieldsToUse);
        return allFieldsToUse;
    }

    private static char gridAtPoint(char[][] grid, Point point) {
        return grid[point.y][point.x];
    }


}
