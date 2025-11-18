package br.ucsal.vetclinicsystem.model.mock;



public class Consulta {

    private int consulId;        // PK
    private int animalId;        // FK1
    private int vetelId;         // FK2

    private String dataHora;
    private String diagnostico;
    private String valor;
    private String consultaPorCpf;

    public Consulta(int consulId, int animalId, int vetelId,
                    String dataHora, String diagnostico,
                    String valor, String consultaPorCpf) {

        this.consulId = consulId;
        this.animalId = animalId;
        this.vetelId = vetelId;
        this.dataHora = dataHora;
        this.diagnostico = diagnostico;
        this.valor = valor;
        this.consultaPorCpf = consultaPorCpf;
    }

    // Getters e setters
    public int getConsulId() {
        return consulId;
    }

    public void setConsulId(int consulId) {
        this.consulId = consulId;
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public int getVetelId() {
        return vetelId;
    }

    public void setVetelId(int vetelId) {
        this.vetelId = vetelId;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getConsultaPorCpf() {
        return consultaPorCpf;
    }

    public void setConsultaPorCpf(String consultaPorCpf) {
        this.consultaPorCpf = consultaPorCpf;
    }
}

