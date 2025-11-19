package br.ucsal.vetclinicsystem.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Consultation {

    private Long id;
    private Animal animal;
    private Veterinarian vet;
    private String dateTime;
    private String diagnosis;
    private BigDecimal value;

    public Consultation() {}

    public Consultation(Long id, Animal animal, Veterinarian veterinarian, String dateTime,
                        String diagnosis, BigDecimal value) {

        this.id = id;
        this.animal = animal;
        this.vet = veterinarian;
        this.dateTime = dateTime;
        this.diagnosis = diagnosis;
        this.value = value;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAnimal() { return animal.getName(); }
    public void setAnimalId(Animal animal) { this.animal = animal; }

    public String getVet() { return vet.getName(); }
    public void setVeterinarianId(Veterinarian vet) { this.vet = vet; }

    public String getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime.toString(); }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }
}
