package br.ucsal.vetclinicsystem.model.mock;

import br.ucsal.vetclinicsystem.model.entities.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Mock {

    // ---------------------
    // OWNERS
    // ---------------------
    public static final List<Owner> owners = new ArrayList<>(List.of(
            new Owner(1L, "12345678901", "Carlos Almeida", "carlos@gmail.com", "71991000001"),
            new Owner(2L, "98765432100", "Mariana Souza", "mariana@gmail.com", "71991000002"),
            new Owner(3L, "11122233344", "Pedro Santos", "pedro@gmail.com", "71991000003"),
            new Owner(4L, "55566677788", "Ana Oliveira", "ana@gmail.com", "71991000004"),
            new Owner(5L, "99988877766", "Rafael Costa", "rafael@gmail.com", "71991000005")
    ));

    // ---------------------
    // ADDRESSES (1:1)
    // ---------------------
    public static final List<Address> addresses = new ArrayList<>(List.of(
            new Address(1L, "Rua A, 10", "Salvador", "BA"),
            new Address(2L, "Rua B, 22", "Lauro de Freitas", "BA"),
            new Address(3L, "Rua C, 30", "Camaçari", "BA"),
            new Address(4L, "Rua D, 15", "Feira de Santana", "BA"),
            new Address(5L, "Rua E, 55", "Salvador", "BA")
    ));

    // ---------------------
    // VETERINARIANS
    // ---------------------
    public static final List<Veterinarian> veterinarians = new ArrayList<>(List.of(
            new Veterinarian(1L, "CRMV-1001", "Dr. João Ribeiro", "Clinico Geral", "71991234001"),
            new Veterinarian(2L, "CRMV-1002", "Dra. Fernanda Lima", "Dermatologia", "71991234002"),
            new Veterinarian(3L, "CRMV-1003", "Dr. Lucas Andrade", "Cardiologia", "71991234003"),
            new Veterinarian(4L, "CRMV-1004", "Dra. Paula Menezes", "Ortopedia", "71991234004"),
            new Veterinarian(5L, "CRMV-1005", "Dr. Ricardo Melo", "Exóticos", "71991234005")
    ));

    // ---------------------
    // ANIMALS
    // ---------------------
    public static final List<Animal> animals = new ArrayList<>(List.of(
            new Animal(1L,  owners.get(0), "Rex", "Cachorro", "Labrador", LocalDate.of(2020,5,10), 32.5f),
            new Animal(2L,  owners.get(0), "Mimi", "Gato", "Siamês", LocalDate.of(2021,3,2), 4.2f),

            new Animal(3L,  owners.get(1), "Thor", "Cachorro", "Husky", LocalDate.of(2019,11,15), 27.1f),
            new Animal(4L,  owners.get(1), "Luna", "Gato", "Persa", LocalDate.of(2022,8,11), 3.8f),

            new Animal(5L,  owners.get(2), "Bolt", "Cachorro", "Vira-lata", LocalDate.of(2023,1,20), 12.0f),
            new Animal(6L,  owners.get(2), "Nina", "Coelho", "Mini Holland", LocalDate.of(2022,4,4), 2.1f),

            new Animal(7L,  owners.get(3), "Apolo", "Cachorro", "Pastor Alemão", LocalDate.of(2018,9,9), 40.3f),
            new Animal(8L,  owners.get(3), "Mel", "Gato", "SRD", LocalDate.of(2020,2,18), 4.9f),

            new Animal(9L,  owners.get(4), "Kiwi", "Ave", "Calopsita", LocalDate.of(2021,7,25), 0.09f),
            new Animal(10L, owners.get(4), "Bob", "Cachorro", "Poodle", LocalDate.of(2017,12,1), 6.5f)
    ));

    // ---------------------
    // CONSULTATIONS
    // ---------------------
    public static final List<Consultation> consultations =new ArrayList<>( List.of(

            new Consultation(1L,  animals.get(0), veterinarians.get(0), LocalDateTime.now().minusDays(40), "Otite", new BigDecimal("150.00")),
            new Consultation(2L,  animals.get(1), veterinarians.get(1), LocalDateTime.now().minusDays(38), "Alergia de pele", new BigDecimal("220.00")),
            new Consultation(3L,  animals.get(2), veterinarians.get(2), LocalDateTime.now().minusDays(37), "Arritmia leve", new BigDecimal("300.00")),
            new Consultation(4L,  animals.get(3), veterinarians.get(1), LocalDateTime.now().minusDays(35), "Dermatite", new BigDecimal("180.00")),
            new Consultation(5L,  animals.get(4), veterinarians.get(0), LocalDateTime.now().minusDays(34), "Infecção viral", new BigDecimal("130.00")),
            new Consultation(6L,  animals.get(5), veterinarians.get(4), LocalDateTime.now().minusDays(33), "Check-up geral", new BigDecimal("95.00")),
            new Consultation(7L,  animals.get(6), veterinarians.get(3), LocalDateTime.now().minusDays(31), "Luxação leve", new BigDecimal("340.00")),
            new Consultation(8L,  animals.get(7), veterinarians.get(1), LocalDateTime.now().minusDays(30), "Conjuntivite", new BigDecimal("140.00")),
            new Consultation(9L,  animals.get(8), veterinarians.get(4), LocalDateTime.now().minusDays(29), "Troca de penas", new BigDecimal("70.00")),
            new Consultation(10L, animals.get(9), veterinarians.get(0), LocalDateTime.now().minusDays(28), "Vômito", new BigDecimal("115.00")),

            new Consultation(11L, animals.get(0), veterinarians.get(2), LocalDateTime.now().minusDays(26), "Taquicardia leve", new BigDecimal("260.00")),
            new Consultation(12L, animals.get(1), veterinarians.get(1), LocalDateTime.now().minusDays(25), "Pulgas", new BigDecimal("80.00")),
            new Consultation(13L, animals.get(2), veterinarians.get(0), LocalDateTime.now().minusDays(24), "Corte na pata", new BigDecimal("160.00")),
            new Consultation(14L, animals.get(3), veterinarians.get(3), LocalDateTime.now().minusDays(23), "Dor articular", new BigDecimal("210.00")),
            new Consultation(15L, animals.get(4), veterinarians.get(1), LocalDateTime.now().minusDays(22), "Infecção bacteriana", new BigDecimal("190.00")),
            new Consultation(16L, animals.get(5), veterinarians.get(4), LocalDateTime.now().minusDays(20), "Diarreia", new BigDecimal("70.00")),
            new Consultation(17L, animals.get(6), veterinarians.get(3), LocalDateTime.now().minusDays(18), "Ruptura parcial de ligamento", new BigDecimal("420.00")),
            new Consultation(18L, animals.get(7), veterinarians.get(1), LocalDateTime.now().minusDays(17), "Alergia alimentar", new BigDecimal("200.00")),
            new Consultation(19L, animals.get(8), veterinarians.get(4), LocalDateTime.now().minusDays(16), "Desidratação", new BigDecimal("60.00")),
            new Consultation(20L, animals.get(9), veterinarians.get(0), LocalDateTime.now().minusDays(15), "Tosse", new BigDecimal("140.00")),

            new Consultation(21L, animals.get(0), veterinarians.get(0), LocalDateTime.now().minusDays(14), "Otite recorrente", new BigDecimal("160.00")),
            new Consultation(22L, animals.get(1), veterinarians.get(1), LocalDateTime.now().minusDays(13), "Dermatite atópica", new BigDecimal("230.00")),
            new Consultation(23L, animals.get(2), veterinarians.get(2), LocalDateTime.now().minusDays(12), "Arritmia moderada", new BigDecimal("310.00")),
            new Consultation(24L, animals.get(3), veterinarians.get(1), LocalDateTime.now().minusDays(11), "Ferida infectada", new BigDecimal("185.00")),
            new Consultation(25L, animals.get(4), veterinarians.get(0), LocalDateTime.now().minusDays(10), "Intoxicação alimentar", new BigDecimal("220.00")),
            new Consultation(26L, animals.get(5), veterinarians.get(4), LocalDateTime.now().minusDays(9), "Apatia", new BigDecimal("65.00")),
            new Consultation(27L, animals.get(6), veterinarians.get(3), LocalDateTime.now().minusDays(8), "Dores lombares", new BigDecimal("350.00")),
            new Consultation(28L, animals.get(7), veterinarians.get(1), LocalDateTime.now().minusDays(7), "Sarna", new BigDecimal("90.00")),
            new Consultation(29L, animals.get(8), veterinarians.get(4), LocalDateTime.now().minusDays(6), "Anemia", new BigDecimal("105.00")),
            new Consultation(30L, animals.get(9), veterinarians.get(0), LocalDateTime.now().minusDays(5), "Gastroenterite", new BigDecimal("145.00")),

            new Consultation(31L, animals.get(0), veterinarians.get(2), LocalDateTime.now().minusDays(4), "Sopro cardíaco", new BigDecimal("350.00")),
            new Consultation(32L, animals.get(1), veterinarians.get(1), LocalDateTime.now().minusDays(4), "Problema urinário", new BigDecimal("170.00")),
            new Consultation(33L, animals.get(2), veterinarians.get(0), LocalDateTime.now().minusDays(3), "Corte leve", new BigDecimal("60.00")),
            new Consultation(34L, animals.get(3), veterinarians.get(3), LocalDateTime.now().minusDays(3), "Dificuldade para andar", new BigDecimal("400.00")),
            new Consultation(35L, animals.get(4), veterinarians.get(1), LocalDateTime.now().minusDays(3), "Febre", new BigDecimal("110.00")),
            new Consultation(36L, animals.get(5), veterinarians.get(4), LocalDateTime.now().minusDays(3), "Problema respiratório", new BigDecimal("85.00")),
            new Consultation(37L, animals.get(6), veterinarians.get(3), LocalDateTime.now().minusDays(2), "Fratura completa", new BigDecimal("690.00")),
            new Consultation(38L, animals.get(7), veterinarians.get(1), LocalDateTime.now().minusDays(2), "Agressividade por dor", new BigDecimal("130.00")),
            new Consultation(39L, animals.get(8), veterinarians.get(4), LocalDateTime.now().minusDays(2), "Parasitas internos", new BigDecimal("95.00")),
            new Consultation(40L, animals.get(9), veterinarians.get(0), LocalDateTime.now().minusDays(2), "Olho irritado", new BigDecimal("70.00")),

            new Consultation(41L, animals.get(0), veterinarians.get(0), LocalDateTime.now().minusDays(1), "Manchas na pele", new BigDecimal("120.00")),
            new Consultation(42L, animals.get(1), veterinarians.get(1), LocalDateTime.now().minusDays(1), "Hérnia", new BigDecimal("320.00")),
            new Consultation(43L, animals.get(2), veterinarians.get(2), LocalDateTime.now().minusDays(1), "Cardiopatia", new BigDecimal("380.00")),
            new Consultation(44L, animals.get(3), veterinarians.get(3), LocalDateTime.now().minusDays(1), "Trauma na pata", new BigDecimal("250.00")),
            new Consultation(45L, animals.get(4), veterinarians.get(0), LocalDateTime.now().minusDays(1), "Infecção intestinal", new BigDecimal("140.00")),
            new Consultation(46L, animals.get(5), veterinarians.get(4), LocalDateTime.now().minusDays(1), "Desnutrição leve", new BigDecimal("70.00")),
            new Consultation(47L, animals.get(6), veterinarians.get(3), LocalDateTime.now().minusDays(1), "Artrite", new BigDecimal("260.00")),
            new Consultation(48L, animals.get(7), veterinarians.get(1), LocalDateTime.now().minusDays(1), "Calosidade", new BigDecimal("75.00")),
            new Consultation(49L, animals.get(8), veterinarians.get(4), LocalDateTime.now().minusDays(1), "Muda difícil", new BigDecimal("85.00")),
            new Consultation(50L, animals.get(9), veterinarians.get(0), LocalDateTime.now().minusDays(1), "Gengivite", new BigDecimal("90.00")),

            new Consultation(51L, animals.get(0), veterinarians.get(2), LocalDateTime.now().minusHours(12), "Pressão alta", new BigDecimal("200.00")),
            new Consultation(52L, animals.get(1), veterinarians.get(1), LocalDateTime.now().minusHours(12), "Vômitos recorrentes", new BigDecimal("150.00")),
            new Consultation(53L, animals.get(2), veterinarians.get(0), LocalDateTime.now().minusHours(10), "Machucado na orelha", new BigDecimal("80.00")),
            new Consultation(54L, animals.get(3), veterinarians.get(3), LocalDateTime.now().minusHours(8), "Torção", new BigDecimal("300.00")),
            new Consultation(55L, animals.get(4), veterinarians.get(1), LocalDateTime.now().minusHours(7), "Reação alérgica", new BigDecimal("180.00")),
            new Consultation(56L, animals.get(5), veterinarians.get(4), LocalDateTime.now().minusHours(6), "Letargia", new BigDecimal("95.00")),
            new Consultation(57L, animals.get(6), veterinarians.get(3), LocalDateTime.now().minusHours(5), "Trauma craniano", new BigDecimal("800.00")),
            new Consultation(58L, animals.get(7), veterinarians.get(1), LocalDateTime.now().minusHours(3), "Infecção ocular", new BigDecimal("130.00")),
            new Consultation(59L, animals.get(8), veterinarians.get(4), LocalDateTime.now().minusHours(2), "Queda de energia", new BigDecimal("55.00")),
            new Consultation(60L, animals.get(9), veterinarians.get(0), LocalDateTime.now().minusHours(1), "Dor abdominal", new BigDecimal("190.00"))
    ));
}
