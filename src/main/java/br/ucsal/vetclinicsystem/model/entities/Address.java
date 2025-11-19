package br.ucsal.vetclinicsystem.model.entities;

public class Address {

    private Long ownerId;   // same as Owner.id (PK=FK)
    private String street;
    private String city;
    private String state; // UF

    public Address() {}

    public Address(Long ownerId, String street, String city, String state) {
        this.ownerId = ownerId;
        this.street = street;
        this.city = city;
        this.state = state;
    }

    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
}
