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
    public void simularParcelamento(int numeroParcela){
        double total = getValorTotal();
        double juros = 0.15;
        double valorComJuros = total * (1 +(juros * numeroParcela));
        double valorParcela = valorComJuros / numeroParcela;

        System.out.println("--- SIMULAÇÃO DE FINANCIAMENTO ---");
        System.out.println("Valor Total (com juros): R$ " + valorComJuros);
        System.out.println("Plano: " + numeroParcela + "x de R$ " + String.format("%.2f", valorParcela));
    }
    public double calcularComissao(){
        double total = getValorTotal();
        double porcentagem = 0.03;
        return total * porcentagem;
    }

    public double aplicarDesconto(double desconto, boolean reais){//reais = true if R$, false if %
        // desconto = 15, reais = false
        double total = this.getValorTotal();

        if(desconto > 25 && !reais){
            return 99;
        }// check

        if(!reais) {
            desconto /= 100;


            if (total * desconto < 0.25 * total) {
                return 12;
            } else if (total * desconto == 0 || total * desconto < 0) {
                return 13;
            } else {
                return total * desconto;
            }
        } else{
            return total;
        }
    }
}
