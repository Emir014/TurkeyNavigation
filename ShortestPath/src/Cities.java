import java.util.ArrayList;
import java.lang.Math;
public class Cities {
  private String cityName;
  private int x;
  private int y;
  private ArrayList<Cities> connections;

  //Constructor
  Cities(String cityName, int x, int y) {
    this.cityName = cityName;
    this.x = x;
    this.y = y;
  }

  //Setters
  public void setCityName(String cityName) {this.cityName = cityName;}
  public void setX(int x) {this.x = x;}
  public void setY(int y) {this.y = y;}
  public void setConnections(ArrayList<Cities> connections) {this.connections = connections;}
  public void addConnection(Cities connection) {this.connections.add(connection);}

  //Getters
  public String getCityName() {return this.cityName;}
  public int getX() {return this.x;}
  public int getY() {return this.y;}
  public ArrayList<Cities> getConnections() {return this.connections;}

  //Computes the distance between two connected cities
  public double computeDistance(Cities other) {
    if(this.equals(other)) {
      return 0;
    }
    else if(!this.connections.contains(other)) {
      throw new UnsupportedOperationException("Cannot calculate distance between unconnected cities!");
    }
    int dx = this.x - other.x;
    int dy = this.y -other.y;
    return Math.sqrt(dx * dx + dy * dy);
  }

  //Gets the index of a city in an Array of cities
  public int getIndex(Cities[] cityArray) {
    for(int i = 0; i < cityArray.length; i++) {
      if(cityArray[i].equals(this)) {
        return i;
      }
    }
    throw new CityNotFoundException("Invalid city");
  }
}