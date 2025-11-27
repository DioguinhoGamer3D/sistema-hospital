package voltando.LojaDeCarros;

public class SistemaLojaDeCarros {
    public static void main(String[] args) {
        System.out.println("--- INICIANDO SISTEMA DE VENDAS ---");


        Data data = new Data(23,11,2025);


        Automovel automovel = new Automovel("0957","Gol"
                ,"Wolswagem", "Amarelo", "2020", 120.000);

        Acessorio arCondicionado = new Acessorio("123", "Ar-Condicionado", 1.000);
        Acessorio som = new Acessorio("122", "Som", 200);

        automovel.adiocionarAcessorios(arCondicionado);
        automovel.adiocionarAcessorios(som);

        Cliente cliente = new Cliente("077.546.227-98", "Carlos"
                , new Endereco("Rua B","Centro", "482"), "8398237564");


        Venda venda = new Venda(cliente,automovel,FormaPagamento.PIX,data,"Claudio");

        System.out.println(venda.aplicarDesconto(35,true));

        venda.imprimirRecibo();
    }
}
