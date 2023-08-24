package org.example;

import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.shape.LongShapeDescriptor;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.dataset.api.preprocessor.serializer.NormalizerSerializer;
import org.nd4j.linalg.dataset.api.preprocessor.serializer.NormalizerType;
import org.nd4j.linalg.dataset.api.preprocessor.serializer.StandardizeSerializerStrategy;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;


public class NeuralNetworkPlayer {
    private static final int NUM_INPUTS = 9;
    private static final int NUM_HIDDEN_NODES = 64;
    private static final int NUM_OUTPUTS = 9;
    private MultiLayerConfiguration configuration;
    private org.deeplearning4j.nn.multilayer.MultiLayerNetwork model;

    public NeuralNetworkPlayer() {
        // Initialize neural network configuration
        configuration = new NeuralNetConfiguration.Builder()
                .seed(12345)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(new Adam())
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(NUM_INPUTS)
                        .nOut(NUM_HIDDEN_NODES)
                        .activation(Activation.RELU)
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .layer(1, new OutputLayer.Builder()
                        .nIn(NUM_HIDDEN_NODES)
                        .nOut(NUM_OUTPUTS)
                        .activation(Activation.SOFTMAX)
                        .lossFunction(LossFunctions.LossFunction.MEAN_SQUARED_LOGARITHMIC_ERROR)
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .build();
    }

    public void train(List<double[]> inputs, List<double[]> labels) {
        // Convert inputs and labels to INDArray
        INDArray inputsArray = Nd4j.create((LongShapeDescriptor) inputs);
        INDArray labelsArray = Nd4j.create((LongShapeDescriptor) labels);

        // Create DataSet from INDArrays
        DataSet dataSet = new DataSet(inputsArray, labelsArray);

        // Create a DataSetIterator with the desired batch size
        int batchSize = 64;
        DataSetIterator dataSetIterator = new ListDataSetIterator<>(dataSet.asList(), batchSize);

        // Build and train the neural network model
        model = new MultiLayerNetwork(configuration);
        model.init();
        model.fit(dataSetIterator);
    }


    public int makeMove(Board board) {
        // Convert the board state into a suitable input format for the neural network
        double[] input = convertBoardToInput(board);

        // Make a prediction using the trained neural network
        int predictedMove = model.predict(Nd4j.create(input))[0];

        // Return the predicted move
        return predictedMove;
    }

    private double[] convertBoardToInput(Board board) {
        double[] input = new double[NUM_INPUTS];
        char[][] gameBoard = board.getBoard();

        // Convert game board state into input format
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                char cell = gameBoard[i][j];
                if (cell == 'X') {
                    input[index] = 1.0;
                } else if (cell == 'O') {
                    input[index] = -1.0;
                }
            }
        }
        return input;
    }
}

