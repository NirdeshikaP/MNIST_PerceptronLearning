public class Label_Input {

    private int[] label;
    private double[][] input;

    public Label_Input(int[] label, double[][] input) {
        this.input = input;
        this.label = label;
    }

    public int[] getLabel() {
        return label;
    }

    public double[][] getInput() {
        return input;
    }

}
