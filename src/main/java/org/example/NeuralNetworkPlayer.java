package org.example;

import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.DropoutLayer;
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
    private int modelNumber;

    public NeuralNetworkPlayer(int modelNumber) throws IOException {
        // Initialize neural network configuration
        this.modelNumber = modelNumber;
        configuration = new NeuralNetConfiguration.Builder()
                .seed(12345)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(new Adam())
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(NUM_INPUTS)
                        .nOut(NUM_HIDDEN_NODES)
                        .activation(Activation.LEAKYRELU)
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .layer(1, new DenseLayer.Builder() // Add another hidden layer here
                        .nIn(NUM_HIDDEN_NODES)
                        .nOut(NUM_HIDDEN_NODES) // Adjust the number of hidden nodes as needed
                        .activation(Activation.LEAKYRELU)
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .layer(2, new OutputLayer.Builder()
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

        int moveIndex = 0;

        if (Math.random() < epsilon) {
            // Explore: Choose a random move
            do {
                moveIndex = (int) (Math.random() * 9);
            } while (!board.isValidMove(board.getBoard(), moveIndex / 3, moveIndex % 3));
        } else {
            // Exploit: Choose the best move
            INDArray sortedOutput = sortOutputForMakeMove(output);
            double[] sortedOutputArray = sortedOutput.toDoubleVector();

            // Find the best move that is valid starting from the highest probability
            for (int i = 8; i >= 0; i--) {
                for (int j = 8; j >= 0; j--) {
                    if (output.getDouble(j) == sortedOutputArray[i] && board.isValidMove(board.getBoard(), j / 3, j % 3)) {
                        moveIndex = j;
                        break;
                    }
                }
                if (board.isValidMove(board.getBoard(), moveIndex / 3, moveIndex % 3)) {
                    break;
                }
            }
        }

        return moveIndex;
    }


    private INDArray sortOutputForMakeMove(INDArray output) {
        double[] outputArray = output.toDoubleVector();
        double[] sortedOutputArray = outputArray.clone();
        Arrays.sort(sortedOutputArray);
        INDArray sortedOutput = Nd4j.create(sortedOutputArray, new int[]{1, 9});
        return sortedOutput;
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

    public void trainSelfPlay(int numIterations, int trainingInterval, char startSymbol) throws IOException {
        int moveIndex = (int) (Math.random() * 9);
        int iterations = 0;
        int moves = 0;

        System.out.println("Training model " + this.modelNumber + " with " + numIterations + " iterations.");

        String fileName = "C:/Uni/Github/Tic-tac-toe-AI/src/main/java/modelPackage/iterations" + this.modelNumber + ".txt";
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
            moves = 0;
            if (iteration % 100 == 0) {
                System.out.println("Iteration: " + iteration);
            }

            List<double[]> inputs = new ArrayList<>();
            List<double[]> labels = new ArrayList<>();

            Board board = new Board(); // Create a new board for each game
            char currentPlayer = startSymbol;

            // Initialize inputs and moves array
            int[] movesArray = new int[9];
            Arrays.fill(movesArray, -1);

            while (!board.checkWin('X') && !board.checkWin('O') && !board.checkTie()) {
                // Make sure the moveIndex is valid for the current board state
                while (!board.isValidMove(board.getBoard(), moveIndex / 3, moveIndex % 3)) {
                    if (currentPlayer == 'O') {
                        moveIndex = makeMove(board, 0.001);
                    } else {
                        switch (iteration / (numIterations / 12)) {
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
                }

                // Store the move index in the moves array
                movesArray[moves] = moveIndex;
                // Store the game state (input) before making the move
                double[] input = convertBoardToInput(board); // Implement this function
                inputs.add(input);

                // Play the move and get the next player
                int row = moveIndex / 3;
                int col = moveIndex % 3;
                board.getBoard()[row][col] = currentPlayer;
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                moves++;
            }

            if (board.checkWin('X')) {
                reward += 1.0;

                // Assign rewards for 'X' moves in forward order
                for (int i = 0; i < moves; i++) {
                    moveIndex = movesArray[i]; // Get the move index from the array
                    labels.add(new double[NUM_OUTPUTS]); // Initialize with zeros
                    labels.get(i)[moveIndex] = reward;
                    reward -= 0.1; // Decrease reward for previous moves
                }
            } else if (board.checkWin('O')) {
                reward += -1.0;

                // Assign rewards for 'O' moves in forward order
                for (int i = 0; i < moves; i++) {
                    moveIndex = movesArray[i]; // Get the move index from the array
                    labels.add(new double[NUM_OUTPUTS]); // Initialize with zeros
                    labels.get(i)[moveIndex] = reward;
                    reward += 0.1; // Increase reward for previous moves (punish 'O' moves)
                }
            } else {
                reward -= 0.2;

                // Assign rewards for all moves in forward order
                for (int i = 0; i < moves; i++) {
                    moveIndex = movesArray[i]; // Get the move index from the array
                    labels.add(new double[NUM_OUTPUTS]); // Initialize with zeros
                    labels.get(i)[moveIndex] = reward;
                    reward -= 0.05; // Decrease reward for previous moves
                }
            }

            // Train the model with the collected data
            if (iteration % trainingInterval == 0) {
                train(inputs, labels, this.modelNumber, numIterations);
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
        System.out.println("Model " + this.modelNumber + " has now done " + iterations + " iterations.");
    }
}

