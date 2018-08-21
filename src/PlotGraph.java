import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class PlotGraph extends JFrame {
    PlotGraph(String title, double[] training, double[] test) {
        super(title);

        JFreeChart chart = ChartFactory.createXYLineChart("Accuracy Plots",
                "Number of Epochs",
                "Accuracy",
                createDataSet(training, test),
                PlotOrientation.VERTICAL,
                true, true, false);

        final XYPlot xyPlot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesPaint(1, Color.GREEN);

        xyPlot.setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(chart);
        setContentPane(chartPanel);
    }

    private XYDataset createDataSet(double[] training, double[] test) {
        final XYSeries training_plot_data = new XYSeries("Training");
        for (int i = 0; i < training.length; i++) {
            training_plot_data.add(i, training[i]);
        }

        final XYSeries test_plot_data = new XYSeries("Testing");
        for (int i = 0; i < test.length; i++) {
            test_plot_data.add(i, test[i]);
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(training_plot_data);
        dataset.addSeries(test_plot_data);

        return dataset;
    }
}
