package pojo;
import java.util.List;

public class OrderCreate {
    private String firstName = "Лера";
    private String lastName = "Букина";
    private String address = "Ставрополь, ул.Банановая";
    // Исправлено: тип int для соответствия числу 1
    private int metroStation = 1;
    private String phone = "89063101579";
    // Исправлено: тип int для соответствия числу 1 (убрали кавычки)
    private int rentTime = 1;
    private String deliveryDate = "2026-04-01";
    private String comment = "хочу самокат";
    private List<String> color;

    public OrderCreate(List<String> color) {
        this.color = color;
    }

    public OrderCreate() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMetroStation() {
        return metroStation;
    }

    // Исправлено: параметр теперь int
    public void setMetroStation(int metroStation) {
        this.metroStation = metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRentTime() {
        return rentTime;
    }

    // Исправлено: параметр теперь int
    public void setRentTime(int rentTime) {
        this.rentTime = rentTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }
}