package voltando.LojaDeCarros;

import java.util.ArrayList;
import java.util.List;

public class Automovel {
    private String chassi;
    private String modelo;
    private String marca;
    private String cor;
    private String ano;
    private double preco;
    private List<Acessorio> acessorio = new ArrayList<>();

    public Automovel(String chassi, String modelo, String marca, String cor, String ano, double preco) {
        this.chassi = chassi;
        this.modelo = modelo;
        this.marca = marca;
        this.cor = cor;
        this.ano = ano;
        this.preco = preco;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao(){
        return "Modelo: "+modelo+", Marca:"+marca+", Cor:"+cor+", R$"+preco+", acessorios:"+acessorio.size()+".";
    }

    public void adiocionarAcessorios(Acessorio acessorio){
        this.acessorio.add(acessorio);
    }

    public double precoTotal(){
        double total = this.preco;
        for(Acessorio a: acessorio){
            total += a.getPreco();
        }
        return total;
    }
}
