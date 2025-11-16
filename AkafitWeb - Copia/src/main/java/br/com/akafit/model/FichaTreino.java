package br.com.akafit.model;

/**
 * Classe Modelo (POJO) que representa a entidade FichaTreino (um exercício).
 * (Idêntica à versão Swing)
 */
public class FichaTreino {

    private int id;
    private int alunoId;
    private String diaSemana;
    private String musculo;
    private String exercicio;
    private String series;
    private String repeticoes;
    private String observacao;

    // Construtor vazio
    public FichaTreino() {
    }

    // Construtor completo (para listar)
    public FichaTreino(int id, int alunoId, String diaSemana, String musculo, String exercicio, String series, String repeticoes, String observacao) {
        this.id = id;
        this.alunoId = alunoId;
        this.diaSemana = diaSemana;
        this.musculo = musculo;
        this.exercicio = exercicio;
        this.series = series;
        this.repeticoes = repeticoes;
        this.observacao = observacao;
    }
    
    // Construtor para salvar (sem ID e alunoId, pois são tratados de forma diferente)
    public FichaTreino(String musculo, String exercicio, String series, String repeticoes, String observacao) {
        this.musculo = musculo;
        this.exercicio = exercicio;
        this.series = series;
        this.repeticoes = repeticoes;
        this.observacao = observacao;
    }
    
    // --- Getters e Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getMusculo() {
        return musculo;
    }

    public void setMusculo(String musculo) {
        this.musculo = musculo;
    }

    public String getExercicio() {
        return exercicio;
    }

    public void setExercicio(String exercicio) {
        this.exercicio = exercicio;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getRepeticoes() {
        return repeticoes;
    }

    public void setRepeticoes(String repeticoes) {
        this.repeticoes = repeticoes;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}