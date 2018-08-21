import javax.swing.*;
import java.awt.*;

public class PerceptronLearning {
    private double[][] weights = new double[10][785];
    int maxNumberOfEpochs = 70;

    Label_Input train_label_input;
    int[] train_label;
    double[][] train_input;
    int train_total = 60000;
    double[] train_accuracy = new double[maxNumberOfEpochs + 1]; // +1 for the one before training

    Label_Input test_label_input;
    int[] test_label;
    double[][] test_input;
    int test_total = 10000;
    double[] test_accuracy = new double[maxNumberOfEpochs + 1]; // +1 for the one before training

    public void perceptronLearning() {
        //----------Get data for training-----------------
        train_label_input = Accuracy.getData("train");
        train_label = train_label_input.getLabel();
        train_input = train_label_input.getInput();

        //----------Get data for testing-----------------
        test_label_input = Accuracy.getData("test");
        test_label = test_label_input.getLabel();
        test_input = test_label_input.getInput();

        //-----------Get Initial Weights-------------------
        weights = Accuracy.generateRandomWeights();

        // Store the initial accuracy before training
        train_accuracy[0] = findAccuracy("train");
        test_accuracy[0] = findAccuracy("test");

        // For each epoch of training, find accuracy.
        // If accuracy difference is less than 1, stop adjust the weights.
        for (int n = 1; n <= maxNumberOfEpochs; n++) {
            adjustWeights();

            train_accuracy[n] = findAccuracy("train");
            test_accuracy[n] = findAccuracy("test");

//            if (Math.abs(train_accuracy[n] - train_accuracy[n - 1]) < 1) {
//                for (int i = n + 1; i <= maxNumberOfEpochs; i++) {
//                    train_accuracy[i] = train_accuracy[n];
//                    test_accuracy[i] = test_accuracy[n];
//                }
//                break;
//            }
        }
    }

    private double findAccuracy(String type) {
        double correct;
        double accuracy;
        if (type.equals("train")) {
            correct = Accuracy.findClassifications(true, train_total, weights, train_label, train_input);
            accuracy = correct * 100 / train_total;
        } else {
            correct = Accuracy.findClassifications(true, test_total, weights, test_label, test_input);
            accuracy = correct * 100 / test_total;
        }
        return accuracy;
    }

    private void adjustWeights() {
        Accuracy.findClassifications(false, train_total, weights, train_label, train_input);
    }

    public void plotAccuracyGraphs() {
        PlotGraph plotGraph = new PlotGraph("Accuracy Plots", train_accuracy, test_accuracy);
        plotGraph.pack();
        plotGraph.setVisible(true);
    }

    public void showConfusionMatrix() {
        JTable table = new JTable(11, 11);
        table.setDefaultRenderer(Object.class,new CustomTableCellRenderer());

        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);

        for (int i = 1; i < table.getColumnCount(); i++) {
            table.setValueAt(i - 1, 0, i);
        }

        for (int i = 1; i < table.getRowCount(); i++) {
            table.setValueAt(i - 1, i, 0);
        }

        int[] outputs = Accuracy.findClassificationsForConfusionMatrix(weights, test_input);
        int[][] grid = new int[10][10];

        for (int n = 0; n < 10000; n++) {
            grid[test_label[n]][outputs[n]]++;
        }

        for (int r = 1; r < table.getRowCount(); r++) {
            for (int c = 1; c < table.getColumnCount(); c++) {
                table.setValueAt(grid[r - 1][c - 1], r, c);
            }
        }

        JFrame frame = new JFrame("Confusion Matrix");
        frame.getContentPane().add(table);
        frame.pack();
        frame.setVisible(true);
    }
}
