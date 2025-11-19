package br.ucsal.vetclinicsystem.model.entities;

public class Veterinarian {

    private Long id;
    private String crmv;
    private String name;
    private String specialty;
    private String phone;

    public Veterinarian() {}

    public Veterinarian(Long id, String crmv, String name, String specialty, String phone) {
        this.id = id;
        this.crmv = crmv;
        this.name = name;
        this.specialty = specialty;
        this.phone = phone;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCrmv() { return crmv; }
    public void setCrmv(String crmv) { this.crmv = crmv; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return this.name;
    }
}
