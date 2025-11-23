package voltando.LojaDeCarros;

public class Venda {
    private Cliente cliente;
    private Automovel automovel;
    private FormaPagamento formaPagamanto;
    private Data data;
    private String nomeVendedor;

    public Venda(Cliente cliente, Automovel automovel, FormaPagamento formaPagamento, Data data, String nomeVendedor) {
        this.cliente = cliente;
        this.automovel = automovel;
        this.formaPagamanto = formaPagamento;
        this.data = data;
        this.nomeVendedor = nomeVendedor;
    }

    public String getNomeVendedor() {
        return nomeVendedor;
    }

    public void setNomeVendedor(String nomeVendedor) {
        this.nomeVendedor = nomeVendedor;
    }
    public double getValorTotal(){
        return this.automovel.getPreco();
    }
    public void imprimirRecibo(){
        System.out.println("=== RECIBO DE VENDA===");
        System.out.println("Data: "+ this.data.formatar());
        System.out.println("Cliente: "+ this.cliente.getNome());
        System.out.println("Veiculo: "+ this.automovel.getDescricao());
        System.out.println("Valor total: "+this.automovel.precoTotal());
        System.out.println("Vendedor: "+ this.nomeVendedor);
        System.out.println("======================");
    }
}
