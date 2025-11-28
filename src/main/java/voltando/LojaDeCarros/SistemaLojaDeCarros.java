package voltando.LojaDeCarros;

import voltando.LojaDeCarros.Exceptions.DescontoNaoPermitidoException;

import java.text.NumberFormat;
import java.util.Locale;

public class SistemaLojaDeCarros {
    public static void main(String[] args) {

        NumberFormat formatador = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        System.out.println("--- INICIANDO SISTEMA DE VENDAS ---");


        Data data = new Data(23, 11, 2025);


        Automovel automovel = new Automovel("0957", "Gol"
                , "Wolswagem", "Amarelo", "2020", 120000);

        Acessorio arCondicionado = new Acessorio("123", "Ar-Condicionado", 1000);
        Acessorio som = new Acessorio("122", "Som", 200);

        automovel.adiocionarAcessorios(arCondicionado);
        automovel.adiocionarAcessorios(som);

        Cliente cliente = new Cliente("077.546.227-98", "Carlos"
                , new Endereco("Rua B", "Centro", "482"), "8398237564");


        Venda venda = new Venda(cliente, automovel, FormaPagamento.PIX, data, "Claudio");


        venda.imprimirRecibo();

        try {
            System.out.println("Desconto: " + formatador.format(venda.aplicarDesconto(20000, true)));
        } catch (DescontoNaoPermitidoException e){
            System.out.println("Não é permitido descontos maiores que 25% e menores que 1%!");
        }

    }
}
