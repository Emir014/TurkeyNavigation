# Turkey Shortest Path Finder

## Overview
This project is part of Assignment 2 from the CMPE 160 Object-Oriented Programming course at Bogazici University, Spring 2024. The goal of this assignment is to implement a program that calculates the shortest path between cities using Dijkstra's algorithm and display in on a map image.

## Features
- **City Management**: Read cities and their coordinates from a file.
- **Connection Management**: Read connections between cities from a file.
- **Shortest Path Calculation**: Uses Dijkstra's algorithm to find the shortest path between two cities.
- **Visualization**: Displays the cities, connections, and the shortest path on a map.

## Dependencies
- `StdDraw`: A library for drawing graphics.
- `city_connections` file: Contains the connections between cities in the format: `City1, City2`.
- `city_coordinates` file: Contains the city coordinates in the format: `CityName, x, y`.
- `map.png`: An image file of the map of Turkey to visualize the cities and paths.

## Setup and Compilation
1. **Clone the Repository**:
    ```sh
    git clone <repository-url>
    cd <repository-directory>
    ```

2. **Required files in project directory**:
    - `city_connections`: File containing connections between cities.
    - `city_coordinates`: File containing coordinates of cities.
    - `map.png`: Map image file for visualization.
    - `Lib/stdlib.jar`: The StdDraw library file.

3. **Compile the Program**:
    Open a terminal in the project directory and run:
    ```sh
    javac -cp Lib/stdlib.jar -d bin src/*.java
    ```

4. **Run the Program**:
    ```sh
    java -cp Lib/stdlib.jar;bin ShortestPath
    ```

## Input Files Format
- **city_connections**: Each line contains two city names separated by a comma, indicating a connection between them.
    ```
    City1, City2
    City3, City4
    ```

- **city_coordinates**: Each line contains a city name and its coordinates (x, y) separated by a comma.
    ```
    CityName, x, y
    Istanbul, 416, 860
    Ankara, 836, 652
    ```

## Usage
1. **Running the Program**:
    - Upon running, the program will prompt for the starting city and the destination city.
    - Enter the names of the cities as they appear in the `city_coordinates` file.

2. **Example**:
    ```
    Enter starting city: Istanbul
    Enter destination city: Ankara
    ```

3. **Output**:
    - The program will output the total distance of the shortest path and the path itself.
    - A graphical window will display the map with the cities, connections, and the shortest path highlighted.

## Notes
- Ensure the city names entered match exactly with those in the `city_coordinates` file.
- Make sure all required files are in the same directory as the program files.
  
