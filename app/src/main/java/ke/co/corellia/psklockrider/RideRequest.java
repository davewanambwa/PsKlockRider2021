package ke.co.corellia.psklockrider;

public class RideRequest {


     private  String id, customer, clat,clong, distance, destination, rlat, rlong,source, rtown,ctown,pack;

    public String getRtown() {
        return rtown;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public void setRtown(String rtown) {
        this.rtown = rtown;
    }

    public String getCtown() {
        return ctown;
    }

    public void setCtown(String ctown) {
        this.ctown = ctown;
    }

    public RideRequest(String name) {
        this.customer = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getClat() {
        return clat;
    }

    public void setClat(String clat) {
        this.clat = clat;
    }

    public String getClong() {
        return clong;
    }

    public void setClong(String clong) {
        this.clong = clong;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getRlat() {
        return rlat;
    }

    public void setRlat(String rlat) {
        this.rlat = rlat;
    }

    public String getRlong() {
        return rlong;
    }

    public void setRlong(String rlong) {
        this.rlong = rlong;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return id;
    }
}
