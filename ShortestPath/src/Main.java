/*
 ** Turkey Shortest Path Finder
 ** @author Emir Kucuktas
 */
import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.StringBuilder;
public class Main {

  //Get the city by its name
  public static Cities getCity(String name, Cities[] cityArray) {
    for(Cities city : cityArray) {
      if (name.equals(city.getCityName())) {
        return city;
      }
    }
    throw new CityNotFoundException("City \"" + name + "\" not found!");
  }

  //Get the path
  public static Cities[] getPath(Cities[] cityArray, int[] parent, int current) {
    ArrayList<Integer> temp = new ArrayList<>();
    while (current != -1) {
      temp.add(current);
      current = parent[current];
    }

    int size = temp.size();
    Cities[] path = new Cities[size];
    //Array reversing
    for (int i = 0; i < size; i++) {
      path[i] = cityArray[temp.get(size - 1 - i)];
    }
    return path;
  }

  //Get the path as a String to display the path
  public static String getPathString(Cities[] cityArray, int[] parent, int current) {
    StringBuilder builder = new StringBuilder("Path: ");
    Cities[] path = getPath(cityArray, parent, current);
    for(Cities city : path) {
      builder.append(city.getCityName()).append(" -> ");
    }
    builder.append("\b\b\b\b");
    return builder.toString();
  }

  public static void main(String[] args) {
    File city_connections = new File("city_connections");
    File city_coordinates = new File("city_coordinates");

    int city_counter = 0;
    // 0-10 Get the number of cities in NUMBER_OF_CITIES
    try {
      Scanner input = new Scanner(city_coordinates);
      while (input.hasNextLine()) {
        input.nextLine();
        city_counter++;
      }
      input.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    int connection_counter = 0;
    //0-10 Get the number of connections in NUMBER_OF_CONNECTIONS
    try {
      Scanner input = new Scanner(city_connections);
      while (input.hasNextLine()) {
        input.nextLine();
        connection_counter++;
      }
      input.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    final int NUM_CITIES = city_counter;
    final int NUM_CONNECTIONS = connection_counter;

    // Create an array with elements as objects of Cities
    Cities[] cityArray = new Cities[NUM_CITIES];

    //0-14 Initialize elements of cityArray except this.connections
    try {
      Scanner input = new Scanner(city_coordinates);
      for (int i = 0; i < NUM_CITIES; i++) {
        String text = input.nextLine();
        String[] line = text.split(", ");
        String cityName = line[0];
        int x = Integer.parseInt(line[1]);
        int y = Integer.parseInt(line[2]);
        cityArray[i] = new Cities(cityName, x, y);
      }
      input.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    //0-20 Add connections in cityArray[i].connections
    try {
      for (Cities city : cityArray) {
        city.setConnections(new ArrayList<>());
        Scanner input = new Scanner(city_connections);
        for (int i = 0; i < NUM_CONNECTIONS; i++) {
          String text = input.nextLine();
          String[] line = text.split(",");
          String cityName1 = line[0];
          String cityName2 = line[1];
          if (city.getCityName().equals(cityName1)) {
            city.addConnection(getCity(cityName2, cityArray));
          } else if (city.getCityName().equals(cityName2)) {
            city.addConnection(getCity(cityName1, cityArray));
          }
        }
        input.close();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    Cities startingCity = null;
    Cities destinationCity = null;

    //Get input
    Scanner input = new Scanner(System.in);
    while(true) {
      System.out.print("Enter starting city: ");
      String startingCityName = input.nextLine();
      try {
        startingCity = getCity(startingCityName, cityArray);
      } catch (CityNotFoundException e) {
        System.out.println("City named '" + startingCityName + "' not found. Please enter a valid city name.");
      }
      if(startingCity != null) {
        break;
      }
    }
    while(true) {
      System.out.print("Enter destination city: ");
      String destinationCityName = input.nextLine();
      try {
        destinationCity = getCity(destinationCityName, cityArray);
      } catch (CityNotFoundException e) {
        System.out.println("City named '" + destinationCityName + "' not found. Please enter a valid city name.");
      }
      if(destinationCity != null) {
        break;
      }
    }
    input.close();

    final int START_INDEX = startingCity.getIndex(cityArray);
    final int DESTINATION_INDEX = destinationCity.getIndex(cityArray);
    final int INFINITY = Integer.MAX_VALUE;

    //Declare arrays
    double[] distances = new double[NUM_CITIES];
    int[] parent = new int[NUM_CITIES];
    boolean[] visited = new boolean[NUM_CITIES];

    //Initialize arrays
    for(int i = 0; i < NUM_CITIES; i++) {
      distances[i] = INFINITY;
      parent[i] = -1;
      visited[i] = false;
    }
    distances[START_INDEX] = 0;

    //Main loop
    while(true) {
      int closestIndex = -1;
      double minDistance = INFINITY;
      //This loop gets the closest unvisited city
      for (int i = 0; i < NUM_CITIES; i++) {
        if (!visited[i] && distances[i] < minDistance) {
          minDistance = distances[i];
          closestIndex = i;
        }
      }
      //Break out of the loop if all cities are visited
      if (closestIndex == -1) {
        break;
      }
      Cities currentCity = cityArray[closestIndex];
      visited[closestIndex] = true;
      //Go to neighbors of the current city
      for (Cities neighbor : currentCity.getConnections()) {
        int j = neighbor.getIndex(cityArray);
        double distance = distances[closestIndex] + currentCity.computeDistance(neighbor);
        //Update distances and parent
        if (!visited[j] && distance < distances[j]) {
          distances[j] = distance;
          parent[j] = closestIndex;
        }
      }
    }

    //Round distances up to two decimals.
    for(int i = 0; i< NUM_CITIES; i++) {
      distances[i] = Math.round(distances[i] * 100) / 100.0;
    }

    if(distances[DESTINATION_INDEX] != INFINITY) {
      System.out.println("Total distance: " + distances[DESTINATION_INDEX]);
      System.out.println(getPathString(cityArray, parent, DESTINATION_INDEX));
    }
    else {
      System.out.println("No path found!");
    }

    Cities[] path = getPath(cityArray, parent, DESTINATION_INDEX);

    int canvasWidth = 2380 / 2;
    int canvasHeight = 1054 / 2;
    Font font = new Font("TimesNewRoman", Font.PLAIN, 12);

    StdDraw.enableDoubleBuffering();
    StdDraw.setCanvasSize(canvasWidth, canvasHeight);
    StdDraw.setXscale(0, canvasWidth * 2);
    StdDraw.setYscale(0, canvasHeight * 2);
    StdDraw.picture(canvasWidth, canvasHeight, "map.png", canvasWidth * 2, canvasHeight * 2);
    StdDraw.setFont(font);
    StdDraw.setPenRadius(0.003);
    StdDraw.setPenColor(Color.GRAY);

    //Write city names
    for (Cities city : cityArray) {
      int x = city.getX();
      int y = city.getY();
      StdDraw.text(x, y + 15, city.getCityName());

      //Connect all neighbors
      for (Cities neighbor : city.getConnections()) {
        int x1 = neighbor.getX();
        int y1 = neighbor.getY();
        StdDraw.line(x, y, x1, y1);
      }
    }

    //Connect all cities in the path and write their names in blue
    for (int i = 0; i < path.length - 1; i++) {
      Cities city = path[i];
      Cities nextCity = path[i + 1];
      int x = city.getX();
      int y = city.getY();
      int x1 = nextCity.getX();
      int y1 = nextCity.getY();

      StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
      StdDraw.setPenRadius(0.008);
      StdDraw.line(x, y, x1, y1);
      StdDraw.text(x, y + 15, city.getCityName());
    }
    //If there is a path, write the destination city's name in blue
    if(path.length != 1) {
      StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
      StdDraw.text(destinationCity.getX(), destinationCity.getY() + 15, destinationCity.getCityName());
    }
    StdDraw.show();
  }
}
