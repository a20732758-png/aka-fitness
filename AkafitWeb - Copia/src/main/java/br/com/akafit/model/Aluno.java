package br.com.akafit.model;

import java.text.DecimalFormat;

/**
 * Classe Modelo (POJO) que representa a entidade Aluno.
 * (Idêntica à versão Swing)
 */
public class Aluno {

    private int id;
    private String nome;
    private int idade;
    private double peso;
    private double altura;
    private String endereco;
    private String email;
    private String telefone;
    private String telefoneEmergencia;
    private String frequencia;

    // Construtor vazio
    public Aluno() {
    }

    // Construtor completo (usado para listar)
    public Aluno(int id, String nome, int idade, double peso, double altura, String endereco, String email, String telefone, String telefoneEmergencia, String frequencia) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.peso = peso;
        this.altura = altura;
        this.endereco = endereco;
        this.email = email;
        this.telefone = telefone;
        this.telefoneEmergencia = telefoneEmergencia;
        this.frequencia = frequencia;
    }
    
    // Construtor para cadastro (sem ID)
    public Aluno(String nome, int idade, double peso, double altura, String endereco, String email, String telefone, String telefoneEmergencia, String frequencia) {
        this.nome = nome;
        this.idade = idade;
        this.peso = peso;
        this.altura = altura;
        this.endereco = endereco;
        this.email = email;
        this.telefone = telefone;
        this.telefoneEmergencia = telefoneEmergencia;
        this.frequencia = frequencia;
    }
    
    
    // --- Getters e Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefoneEmergencia() {
        return telefoneEmergencia;
    }

    public void setTelefoneEmergencia(String telefoneEmergencia) {
        this.telefoneEmergencia = telefoneEmergencia;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }
    
    // --- Métodos de Negócio ---
    
    public String getIMC() {
        if (altura > 0) {
            double imc = peso / (altura * altura);
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(imc);
        }
        return "N/A";
    }

    public String getStatusIMC() {
        if (altura <= 0) return "Dados inválidos";
        
        double imc = peso / (altura * altura);
        
        if (imc < 18.5) return "Abaixo do peso";
        if (imc < 24.9) return "Peso normal";
        if (imc < 29.9) return "Sobrepeso";
        if (imc < 34.9) return "Obesidade Grau I";
        if (imc < 39.9) return "Obesidade Grau II";
        return "Obesidade Grau III";
    }
    
    @Override
    public String toString() {
        return getNome();
    }
}