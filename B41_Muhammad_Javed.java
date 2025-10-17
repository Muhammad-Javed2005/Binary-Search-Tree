import java.util.*;
import java.io.*;
import java.time.*;
import java.lang.*;

public class B41_Muhammad_Javed {

    static int n = 100; // Maximum Nodes
    static int[][] bst = new int[3][n]; // 3 rows (Left, Data, Right)
    static int nodeCount = 1;
    static String fileName = "BST.txt"; // File name for saving tree

    public static void main(String[] args) {

        System.out.println("Hi, Javed!");
        Scanner sc = new Scanner(System.in);

        // Initialize array with -1
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < n; j++) {
                bst[i][j] = -1;
            }
        }

        // Root Node
        bst[1][0] = 50;


        // Insert More values
        int[] values = {45, 32, 76, 81, 12, 99, 25, 3, 4, 40, 42, -19, 0, 60, 80, 20};
        for (int i = 0; i < values.length; i++) {
            insert(bst, 0, values[i]);
        }

        // Save initial tree to file
        saveTreeToFile();

        // Menu for user
        while (true) {
            System.out.println("--------BINARY SEARCH TREE MENU--------");
            System.out.println("1. Display Tree");
            System.out.println("2. Search a Value");
            System.out.println("3. Print In-Order Traversal");
            System.out.println("4. Insert New Value");
            System.out.println("5. Exit");
            System.out.print("Please Enter Your Choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> displayTree();
                case 2 -> {
                    System.out.print("Enter Value to Search: ");
                    int key = sc.nextInt();
                    searchValue(bst, key);
                }
                case 3 -> {
                    System.out.println("In-Order Traversal:");
                    inorderTraversal(bst, 0);
                    System.out.println();
                }
                case 4 -> {
                    System.out.print("Enter value to insert: ");
                    int newValue = sc.nextInt();
                    insert(bst, 0, newValue);
                    System.out.println("Value inserted successfully!");
                    saveTreeToFile(); // Update file after insertion
                }
                case 5 -> {
                    System.out.println("Exiting...");
                    System.out.println("Program developed by Engr. Muhammad Javed");
                    return;
                }
                default -> System.out.println("Invalid Choice!");
            }
        }
    }

    static void insert(int[][] bst, int index, int value) {
        if (value < bst[1][index]) {
            if (bst[0][index] == -1) {
                int newIndex = findEmpty(bst);
                bst[0][index] = newIndex;
                bst[1][newIndex] = value;
            } else {
                insert(bst, bst[0][index], value);
            }
        } else if (value > bst[1][index]) {
            if (bst[2][index] == -1) {
                int newIndex = findEmpty(bst);
                bst[2][index] = newIndex;
                bst[1][newIndex] = value; 
            } else {
                insert(bst, bst[2][index], value);
            }
        }
    }

    // Find next Empty Slot
    static int findEmpty(int[][] bst) {
        for (int i = 0; i < bst[0].length; i++) {
            if (bst[1][i] == -1) {
                return i;
            }
        }
        return -1;
    }

    // Display Whole Tree
    static void displayTree() {
        System.out.println("\nBST Structure (Left | Data | Right)");
        for (int i = 0; i < bst[0].length; i++) {
            if (bst[1][i] != -1) {
                System.out.println("Node [" + i + "] => Left=" + bst[0][i] +
                                   " | Data=" + bst[1][i] +
                                   " | Right=" + bst[2][i]);
            }
        }
        saveTreeToFile(); // Also update file when displaying
    }

    // Save Tree Structure to BST.txt
    static void saveTreeToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("-------- BINARY SEARCH TREE STRUCTURE --------");
            writer.println("Left | Data | Right");
            writer.println("----------------------------------------------");
            for (int i = 0; i < bst[0].length; i++) {
                if (bst[1][i] != -1) {
                    writer.println("Node [" + i + "] => Left=" + bst[0][i] +
                                   " | Data=" + bst[1][i] +
                                   " | Right=" + bst[2][i]);
                }
            }
            writer.println("\nTree saved successfully at " + LocalDateTime.now());
        } catch (IOException e) {
            System.out.println("Error saving tree to file: " + e.getMessage());
        }
    }

    // Search Value and show Parent and Child info
    static void searchValue(int[][] bst, int key) {
        int index = findIndex(bst, 0, key);
        if (index == -1) {
            System.out.println("Value " + key + " not found in tree.");
            return;
        }

        int parent = findParent(bst, 0, key);
        int left = bst[0][index];
        int right = bst[2][index];

        System.out.println("-----Search Result-----");
        System.out.println("Value = " + key);
        System.out.println("Index = " + index);
        System.out.println("Parent = " + (parent == -1 ? "None (Root)" : bst[1][parent]));
        System.out.println("Left Child = " + (left == -1 ? "None" : bst[1][left]));
        System.out.println("Right Child = " + (right == -1 ? "None" : bst[1][right]));
    }

    // Find Index of the value
    static int findIndex(int[][] bst, int index, int key) {
        if (index == -1 || bst[1][index] == -1) return -1;
        if (bst[1][index] == key) return index;
        if (key < bst[1][index]) return findIndex(bst, bst[0][index], key);
        else return findIndex(bst, bst[2][index], key);
    }

    // Find Parent Node of key
    static int findParent(int[][] bst, int index, int key) {
        if (index == -1) return -1;
        if ((bst[0][index] != -1 && bst[1][bst[0][index]] == key) ||
            (bst[2][index] != -1 && bst[1][bst[2][index]] == key))
            return index;
        if (key < bst[1][index]) return findParent(bst, bst[0][index], key);
        else return findParent(bst, bst[2][index], key);
    }

    // Inorder Traversal (sorted order)
    static void inorderTraversal(int[][] bst, int index) {
        if (index == -1 || bst[1][index] == -1) return;
        inorderTraversal(bst, bst[0][index]);
        System.out.print(bst[1][index] + " ");
        inorderTraversal(bst, bst[2][index]);
    }
}


