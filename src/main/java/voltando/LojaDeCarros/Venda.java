package voltando.LojaDeCarros;

import voltando.LojaDeCarros.Exceptions.DescontoNaoPermitidoException;

import java.text.NumberFormat;
import java.util.Locale;

public class Venda {
    private Cliente cliente;
    private Automovel automovel;
    private FormaPagamento formaPagamanto;
    private Data data;
    private String nomeVendedor;

    NumberFormat formatador = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

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

    public void imprimirRecibo(){
        System.out.println("=== RECIBO DE VENDA===");
        System.out.println("Data: "+ this.data.formatar());
        System.out.println("Cliente: "+ this.cliente.getNome());
        System.out.println("Veiculo: "+ this.automovel.getDescricao());
        System.out.println("Valor total: "+ formatador.format(this.automovel.precoTotal()));
        System.out.println("Vendedor: "+ this.nomeVendedor);
        System.out.println("======================");
    }
    public void simularParcelamento(int numeroParcela){
        double total = automovel.precoTotal();
        double juros = 0.15;
        double valorComJuros = total * (1 +(juros * numeroParcela));
        double valorParcela = valorComJuros / numeroParcela;

        System.out.println("--- SIMULAÇÃO DE FINANCIAMENTO ---");
        System.out.println("Valor Total (com juros): R$ " + valorComJuros);
        System.out.println("Plano: " + numeroParcela + "x de R$ " + String.format("%.2f", valorParcela));
    }
    public double calcularComissao(){
        double total = automovel.precoTotal();
        double porcentagem = 0.03;
        return total * porcentagem;
    }

    public double aplicarDesconto(double desconto, boolean reais) throws DescontoNaoPermitidoException{
        //quero que reais = true seja entrada em reais ex:50,100...
        //e reais false seja entrada ja em porcentagem ex:0.15, 0.10...

        //TODO ve um jeito de melhorar

        //TODO quero um jeito de limitar o desconto em dinheiro pra no maximo 25% tbm
        if (reais){
            return this.automovel.precoTotal()-desconto;
        } else {
            if(desconto < 25 && desconto > 1) {
                desconto /= 100;
                return this.automovel.precoTotal() * desconto;
            }else {
                throw new DescontoNaoPermitidoException("Não é permitido descontos maiores que 25% e menores que 1%!");
                }
            }
        }

    }