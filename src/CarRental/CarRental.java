package CarRental;

/* CarRental javaBean */
public class CarRental implements Comparable<CarRental> {
    private String name;
    private String phoneNumber;
    private String address;
    private String carID;
    private String priceAndDay;

    public CarRental() {  //无参构造函数

    }

    public CarRental(String name, String phoneNumber, String address, String carID, String priceAndDay) {  //有参构造函数
        super();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.carID = carID;
        this.priceAndDay = priceAndDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getPriceAndDay() {
        return priceAndDay;
    }

    public void setPriceAndDay(String priceAndDay) {
        this.priceAndDay = priceAndDay;
    }

    @Override  //重写toString方法
    public String toString() {
        return "LeaseForm{" + "name='" + name + '\'' + ", phoneNumber='" + phoneNumber + '\'' + ", address='"
                + address + '\'' + ", carID='" + carID + '\'' + ", priceAndDay='" + priceAndDay + '\'' + '}';
    }

    @Override  //重写compareTo方法
    public int compareTo(CarRental o) {
        if (this.name.compareTo(o.name) > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
