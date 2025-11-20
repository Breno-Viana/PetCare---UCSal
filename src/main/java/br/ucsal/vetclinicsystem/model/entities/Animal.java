package br.ucsal.vetclinicsystem.model.entities;

import java.time.LocalDate;

public class Animal {

    private Long id;
    private Owner owner;
    private String name;
    private String species;
    private String breed;
    private LocalDate birthdate;
    private float weight;

    public Animal() {}

    public Animal(Long id, Owner owner, String name, String species, String breed,
                  LocalDate birthdate, float weight) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.birthdate = birthdate;
        this.weight = weight;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOwner() { return owner.getName(); }

    public Owner getOwnerO(){return owner;}
    public void setOwner(Owner owner) { this.owner = owner; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecies() { return species; }
    public void setSpecies(String species) { this.species = species; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public LocalDate getBirthdate() { return birthdate; }
    public void setBirthdate(LocalDate birthdate) { this.birthdate = birthdate; }

    public float getWeight() { return weight; }
    public void setWeight(float weight) { this.weight = weight; }
}

