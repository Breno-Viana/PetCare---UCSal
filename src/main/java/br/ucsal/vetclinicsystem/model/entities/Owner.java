package br.ucsal.vetclinicsystem.model.entities;

public class Owner {

    private Long id;
    private String cpf;
    private String name;
    private String email;
    private String phone;

    private Address address;

    private String street;

    private String num;

    public Owner() {}

    public Owner(Long id, String cpf, String name, String email, String phone, Address address) {
        this.id = id;
        this.cpf = cpf;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        String street1 = address.getStreet();
        String[] split = street1.split(",");
        this.street = split[0];
        this.num = split[1];
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public void setAddress(Address address) {
        this.address = address;
    }
    public Address getAddress(){
        return address;
    }

    public String getState(){
        return address.getState();
    }
    public String getCity(){
        return address.getCity();
    }

    public String getStreet(){
        return street;
    }
    public String getNum(){
        return num;
    }
}
