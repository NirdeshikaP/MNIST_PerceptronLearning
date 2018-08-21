import java.util.Random;
import java.util.stream.DoubleStream;

public class Accuracy {
    static double learningRate = 0.1;

    public static Label_Input getData(String type) {
        ReadCSV r;
        if (type.equals("train")) {
            r = new ReadCSV("mnist_train.csv", type);
        } else
            r = new ReadCSV("mnist_test.csv", type);
        return r.readCSV();
    }

    public static double[][] generateRandomWeights() {
        Random random = new Random();
        DoubleStream doubleStream;
        double[][] weights = new double[10][785];

        for (int i = 0; i < 10; i++) {
            doubleStream = random.doubles(785, -0.05, 0.05);
            weights[i] = doubleStream.toArray();
        }
        return weights;
    }

    public static int findClassifications(boolean isForAccuracy, int total, double[][] weights, int[] label, double[][] input) {
        int correct = 0;
        int t;
        int y;

        for (int i = 0; i < total; i++) { //For each example
            double[] value = new double[10];
            for (int o = 0; o < 10; o++) { //Calculate the output for each neuron
                for (int j = 0; j < 785; j++) {
                    value[o] += weights[o][j] * input[i][j];
                }
            }
            if (isForAccuracy) {
                if (label[i] == findMaximum(value))
                    correct++;
            } else if (!isForAccuracy) {
                for (int o = 0; o < 10; o++) {
                    t = label[i] == o ? 1 : 0; // 1 if the output unit is the correct one for this training example
                    y = value[o] > 0 ? 1 : 0; // 1 if 𝒘 ∙ 𝒙 >0 0
                    if (t != y) {
                        for (int j = 0; j < 785; j++) {
                            weights[o][j] = weights[o][j] + learningRate * (t - y) * input[i][j];
                        }
                    }
                }
            }
        }
        return correct;
    }

    public static int[] findClassificationsForConfusionMatrix(double[][] weights, double[][] input) {
        int[] output = new int[10000];

        for (int i = 0; i < 10000; i++) { //For each example
            double[] value = new double[10];
            for (int o = 0; o < 10; o++) { //Calculate the output for each neuron
                for (int j = 0; j < 785; j++) {
                    value[o] += weights[o][j] * input[i][j];
                }
            }

            output[i] = findMaximum(value);
        }
        return output;
    }

    private static int findMaximum(double[] a) {
        double maximumValue = a[0];
        int maximumIndex = 0;

        for (int i = 1; i < a.length; i++) {
            if (maximumValue < a[i]) {
                maximumIndex = i;
                maximumValue = a[i];
            }
        }
        return maximumIndex;
    }
}
