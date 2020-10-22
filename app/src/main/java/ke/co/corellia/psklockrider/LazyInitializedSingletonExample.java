package ke.co.corellia.psklockrider;


import com.google.android.gms.maps.model.LatLng;

public class LazyInitializedSingletonExample {
    private static LazyInitializedSingletonExample instance;
    private String lo;
    private String json;

    private String Email;
    private String Password;
    private String Telephone;
    private String Fullname ="None";
    private String Result;
    private String Distance;
    private Double fromlat;
    private Double tolat;
    private Double fromlon;
    private Double tolon;
    private LatLng lfrom;
    private LatLng lto;
    private String confirm ="0";
    private String riderequesteresponse;
    private String tripid,Myid,mytrip;
    private RideRequest MyRide;
    private LatLng Gdest;
    private String clientrating="0";

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    private String Driver ="All riders are busy.. Searching.." ;

    public String getTripid() {
        return tripid;
    }

    public LatLng getGdest() {
        return Gdest;
    }

    public void setGdest(LatLng gdest) {
        Gdest = gdest;
    }

    public String getMytrip() {
        return mytrip;
    }

    public void setMytrip(String mytrip) {
        this.mytrip = mytrip;
    }

    public String getMyid() {
        return Myid;
    }

    public RideRequest getMyRide() {
        return MyRide;
    }

    public void setMyRide(RideRequest myRide) {
        MyRide = myRide;
    }

    public void setMyid(String myid) {
        Myid = myid;
    }

    public void setTripid(String tripid) {
        this.tripid = tripid;
    }

    private String Source;
    private String Des;

    public String getFare() {
        return fare;
    }

    public String getDriver() {
        return Driver;
    }

    public void setDriver(String driver) {
        Driver = driver;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getClientrating() {
        return clientrating;
    }

    public void setClientrating(String clientrating) {
        this.clientrating = clientrating;
    }

    private String fare;
    private String job="Ride";

    public String getJob() {
        return job;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }

    public static void setInstance(LazyInitializedSingletonExample instance) {
        LazyInitializedSingletonExample.instance = instance;
    }

    public String getRiderequesteresponse() {
        return riderequesteresponse;
    }

    public void setRiderequesteresponse(String riderequesteresponse) {
        this.riderequesteresponse = riderequesteresponse;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public LatLng getLfrom() {
        return lfrom;
    }

    public void setLfrom(LatLng lfrom) {
        this.lfrom = lfrom;
    }

    public LatLng getLto() {
        return lto;
    }

    public void setLto(LatLng lto) {
        this.lto = lto;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public String getResult() {
        return Result;
    }

    public Double getFromlat() {
        return fromlat;
    }

    public void setFromlat(Double fromlat) {
        this.fromlat = fromlat;
    }

    public Double getTolat() {
        return tolat;
    }

    public void setTolat(Double tolat) {
        this.tolat = tolat;
    }

    public Double getFromlon() {
        return fromlon;
    }

    public void setFromlon(Double fromlon) {
        this.fromlon = fromlon;
    }

    public Double getTolon() {
        return tolon;
    }

    public void setTolon(Double tolon) {
        this.tolon = tolon;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    private LazyInitializedSingletonExample() {
    }  //private constructor.

    public static LazyInitializedSingletonExample getInstance() {
        if (instance == null) {
            //if there is no instance available... create new one;

            instance = new LazyInitializedSingletonExample();
        }

        return instance;
    }

    public String getLo() {
        return lo;
    }

    public void setLo(String lo) {
        this.lo = lo;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
