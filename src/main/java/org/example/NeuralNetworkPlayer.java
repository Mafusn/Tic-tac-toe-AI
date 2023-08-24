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
import java.util.Arrays;
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
        model = new MultiLayerNetwork(configuration);
        model.init();
    }

    public void train(List<double[]> inputs, List<double[]> labels) {
        // Convert inputs and labels to INDArray
        INDArray inputsArray = Nd4j.create(inputs.size(), NUM_INPUTS);
        for (int i = 0; i < inputs.size(); i++) {
            inputsArray.putRow(i, Nd4j.create(inputs.get(i)));
        }

        INDArray labelsArray = Nd4j.create(labels.size(), NUM_OUTPUTS);
        for (int i = 0; i < labels.size(); i++) {
            labelsArray.putRow(i, Nd4j.create(labels.get(i)));
        }

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



    public int makeMove(Board board, double epsilon) {
        if (model == null) {
            throw new IllegalStateException("The neural network model has not been trained yet.");
        }

        // Flatten the game board and reshape it
        double[] flattenedInput = flattenAndReshapeBoard(board);

        // Create an INDArray from the flattened input
        INDArray input = Nd4j.create(flattenedInput, new int[]{1, 9}); // Reshape to 1x9 matrix

        // Make a prediction using the model
        INDArray output = model.output(input);

        int moveIndex;
        if (Math.random() < epsilon) {
            // Explore: Choose a random move
            moveIndex = (int) (Math.random() * 9);
        } else {
            // Exploit: Choose the best move
            moveIndex = Nd4j.argMax(output, 1).getInt(0);
        }

        return moveIndex;
    }

    private double[] flattenAndReshapeBoard(Board board) {
        double[] flattenedInput = new double[NUM_INPUTS];
        int counter = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                flattenedInput[counter] = (board.getBoard()[i][j]);
                counter++;
            }
        }
        return flattenedInput;
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

    public void trainSelfPlay(int numIterations) {
        int moveIndex = (int) (Math.random() * 9);
        for (int iteration = 0; iteration < numIterations; iteration++) {
            double reward = 0;
            if (iteration % 100 == 0) {
                System.out.println("Iteration: " + iteration);
            }

            List<double[]> inputs = new ArrayList<>();
            List<double[]> labels = new ArrayList<>();

            Board board = new Board(); // Create a new board for each game
            char currentPlayer = 'X';

            while (!board.checkWin('X') && !board.checkWin('O') && !board.checkTie()) {
                while (!board.isValidMove(board.getBoard(), moveIndex / 3, moveIndex % 3)) {
                    switch (iteration % 1000) {
                        case 0:
                            moveIndex = makeMove(board, 0.9);
                            break;
                        case 1:
                            moveIndex = makeMove(board, 0.8);
                            break;
                        case 2:
                            moveIndex = makeMove(board, 0.7);
                            break;
                        case 3:
                            moveIndex = makeMove(board, 0.6);
                            break;
                        case 4:
                            moveIndex = makeMove(board, 0.5);
                            break;
                        case 5:
                            moveIndex = makeMove(board, 0.4);
                            break;
                        case 6:
                            moveIndex = makeMove(board, 0.3);
                            break;
                        case 7:
                            moveIndex = makeMove(board, 0.2);
                            break;
                        default:
                            moveIndex = makeMove(board, 0.1);
                            break;
                    }
                }

                double[] input = flattenAndReshapeBoard(board);
                inputs.add(input);

                // Play the move and get the next player
                int row = moveIndex / 3;
                int col = moveIndex % 3;
                board.getBoard()[row][col] = currentPlayer;
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }

            // Assign rewards based on game outcome
            if (board.checkWin('X')) {
                reward += 1.0;
            } else if (board.checkWin('O')) {
                reward += -1.0;
            } else {
                reward += 0.2;
            }

            // Create labels for each input
            for (int i = 0; i < inputs.size(); i++) {
                labels.add(new double[NUM_OUTPUTS]); // Initialize with zeros
                labels.get(i)[moveIndex] = reward;
            }

            // Train the model with the collected data
            if (iteration % 100 == 0) {
                train(inputs, labels);
            }
        }
    }
}

