package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Theatre {
    private final String theatreId;
    private String name;
    private String city;
    private String address;
    private final List<Hall> halls;

    public Theatre(String theatreId, String name, String city, String address) {
        this.theatreId = theatreId;
        this.name      = name;
        this.city      = city;
        this.address   = address;
        this.halls     = new ArrayList<>();
    }

    public void addHall(Hall hall) {
        halls.add(hall);
    }

    public String getTheatreId()            { return theatreId; }
    public String getName()                 { return name; }
    public String getCity()                 { return city; }
    public String getAddress()              { return address; }
    public List<Hall> getHalls()            { return Collections.unmodifiableList(halls); }

    public void setName(String name)        { this.name    = name; }
    public void setCity(String city)        { this.city    = city; }
    public void setAddress(String address)  { this.address = address; }
}
