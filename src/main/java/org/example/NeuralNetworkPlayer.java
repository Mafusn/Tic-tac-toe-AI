package org.example;

import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;


public class NeuralNetworkPlayer {
    private static final int NUM_INPUTS = 9;
    private static final int NUM_HIDDEN_NODES = 64;
    private static final int NUM_OUTPUTS = 9;
    private MultiLayerConfiguration configuration;
    private org.deeplearning4j.nn.multilayer.MultiLayerNetwork model;

    public NeuralNetworkPlayer(int modelNumber) throws IOException {
        // Initialize neural network configuration
        configuration = new NeuralNetConfiguration.Builder()
                .seed(12345)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(new Adam())
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(NUM_INPUTS)
                        .nOut(NUM_HIDDEN_NODES)
                        .activation(Activation.RELU) // .activation(Activation.LEAKYRELU)
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .layer(1, new OutputLayer.Builder()
                        .nIn(NUM_HIDDEN_NODES)
                        .nOut(NUM_OUTPUTS)
                        .activation(Activation.SOFTMAX)
                        .lossFunction(LossFunctions.LossFunction.MCXENT)
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .build();
        File modelFile = new File("C:/Uni/Github/Tic-tac-toe-AI/src/main/java/modelPackage/model" + modelNumber + ".zip");
        if (modelFile.exists()) {
            model = ModelSerializer.restoreMultiLayerNetwork(modelFile);
        } else {
            model = new MultiLayerNetwork(configuration);
            model.init();
        }
    }

    public void train(List<double[]> inputs, List<double[]> labels, int modelNumber, int iterations) throws IOException {
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

        // Save the model in a file here
        ModelSerializer.writeModel(model, "C:/Uni/Github/Tic-tac-toe-AI/src/main/java/modelPackage/model" + modelNumber + ".zip", true);
    }



    public int makeMove(Board board, double epsilon) {
        if (model == null) {
            throw new IllegalStateException("The neural network model has not been trained yet.");
        }

        // Flatten the game board and reshape it
        double[] flattenedInput = convertBoardToInput(board);

        // Create an INDArray from the flattened input
        INDArray input = Nd4j.create(flattenedInput, new int[]{1, 9}); // Reshape to 1x9 matrix

        // Make a prediction using the model
        INDArray output = model.output(input);
        if (Math.random() < 0.0001) {
            System.out.println("Output: " + Arrays.toString(output.toDoubleVector()));
        }

        int moveIndex;
        if (Math.random() < epsilon) {
            // Explore: Choose a random move
            moveIndex = (int) (Math.random() * 9);
        } else {
            // Exploit: Choose the best move
            moveIndex = 0;
            for (int i = 1; i < output.toDoubleVector().length; i++) {
                if (output.toDoubleVector()[i] > output.toDoubleVector()[moveIndex]) { // gets the index of the highest probability
                    moveIndex = i;
                }
            }
        }

        return moveIndex;
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

    public void trainSelfPlayToBeFirst(int numIterations, int modelNumber, int traininginterval) throws IOException {
        int moveIndex = (int) (Math.random() * 9);
        int iterations = 0;

        String fileName = "C:/Uni/Github/Tic-tac-toe-AI/src/main/java/modelPackage/iterations" + modelNumber + ".txt";
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            iterations = Integer.parseInt(line);
            bufferedReader.close();
            System.out.println("Numbber of iterations done before this training session: " + iterations);
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }

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
                    switch (iteration / 10000) {
                        case 0:
                            moveIndex = makeMove(board, 1);
                            break;
                        case 1:
                            moveIndex = makeMove(board, 0.9);
                            break;
                        case 2:
                            moveIndex = makeMove(board, 0.8);
                            break;
                        case 3:
                            moveIndex = makeMove(board, 0.7);
                            break;
                        case 4:
                            moveIndex = makeMove(board, 0.6);
                            break;
                        case 5:
                            moveIndex = makeMove(board, 0.5);
                            break;
                        case 6:
                            moveIndex = makeMove(board, 0.4);
                            break;
                        case 7:
                            moveIndex = makeMove(board, 0.3);
                            break;
                        case 8:
                            moveIndex = makeMove(board, 0.2);
                            break;
                        case 9:
                            moveIndex = makeMove(board, 0.1);
                            break;
                        default:
                            moveIndex = makeMove(board, 0.05);
                            break;
                    }
                }

                double[] input = convertBoardToInput(board);
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
                reward -= 0.2;
            }

            // Create labels for each input
            for (int i = 0; i < inputs.size(); i++) {
                labels.add(new double[NUM_OUTPUTS]); // Initialize with zeros
                labels.get(i)[moveIndex] = reward;
            }

            // Train the model with the collected data
            if (iteration % traininginterval == 0) {
                train(inputs, labels, modelNumber, numIterations);
            }
        }
        FileWriter fileWriter = new FileWriter(fileName);
        int totalIterations = iterations + numIterations;
        fileWriter.write(Integer.toString(totalIterations));
        fileWriter.close();
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        iterations = Integer.parseInt(line);
        bufferedReader.close();
        System.out.println("Model " + modelNumber + " has now done " + iterations + " iterations.");
    }

    public void trainSelfPlayToBeSecond(int numIterations, int modelNumber, int traininginterval) throws IOException {
        int moveIndex = (int) (Math.random() * 9);
        int iterations = 0;

        String fileName = "C:/Uni/Github/Tic-tac-toe-AI/src/main/java/modelPackage/iterations" + modelNumber + ".txt";
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            iterations = Integer.parseInt(line);
            bufferedReader.close();
            System.out.println("Numbber of iterations done before this training session: " + iterations);
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }

        for (int iteration = 0; iteration < numIterations; iteration++) {
            double reward = 0;
            if (iteration % 100 == 0) {
                System.out.println("Iteration: " + iteration);
            }

            List<double[]> inputs = new ArrayList<>();
            List<double[]> labels = new ArrayList<>();

            Board board = new Board(); // Create a new board for each game
            char currentPlayer = 'O';

            while (!board.checkWin('X') && !board.checkWin('O') && !board.checkTie()) {
                while (!board.isValidMove(board.getBoard(), moveIndex / 3, moveIndex % 3)) {
                    switch (iteration / 10000) {
                        case 0:
                            moveIndex = makeMove(board, 1);
                            break;
                        case 1:
                            moveIndex = makeMove(board, 0.9);
                            break;
                        case 2:
                            moveIndex = makeMove(board, 0.8);
                            break;
                        case 3:
                            moveIndex = makeMove(board, 0.7);
                            break;
                        case 4:
                            moveIndex = makeMove(board, 0.6);
                            break;
                        case 5:
                            moveIndex = makeMove(board, 0.5);
                            break;
                        case 6:
                            moveIndex = makeMove(board, 0.4);
                            break;
                        case 7:
                            moveIndex = makeMove(board, 0.3);
                            break;
                        case 8:
                            moveIndex = makeMove(board, 0.2);
                            break;
                        case 9:
                            moveIndex = makeMove(board, 0.1);
                            break;
                        default:
                            moveIndex = makeMove(board, 0.05);
                            break;
                    }
                }

                double[] input = convertBoardToInput(board);
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
                reward -= 0.2;
            }

            // Create labels for each input
            for (int i = 0; i < inputs.size(); i++) {
                labels.add(new double[NUM_OUTPUTS]); // Initialize with zeros
                labels.get(i)[moveIndex] = reward;
            }

            // Train the model with the collected data
            if (iteration % traininginterval == 0) {
                train(inputs, labels, modelNumber, numIterations);
            }
        }
        FileWriter fileWriter = new FileWriter(fileName);
        int totalIterations = iterations + numIterations;
        fileWriter.write(Integer.toString(totalIterations));
        fileWriter.close();
        FileReader fileReader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();
        iterations = Integer.parseInt(line);
        bufferedReader.close();
        System.out.println("Model " + modelNumber + " has now done " + iterations + " iterations.");
    }
}

