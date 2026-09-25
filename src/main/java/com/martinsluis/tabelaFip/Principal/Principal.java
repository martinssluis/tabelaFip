package com.martinsluis.tabelaFip.Principal;

import com.martinsluis.tabelaFip.config.FipeClientConfig;
import com.martinsluis.tabelaFip.dto.*;
import com.martinsluis.tabelaFip.exception.OpcaoInvalidaException;
import com.martinsluis.tabelaFip.exception.TipoDivergenteException;
import com.martinsluis.tabelaFip.exception.ValorNaoEncontradoOuNuloException;
import com.martinsluis.tabelaFip.service.ConverteDados;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private FipeClientConfig fipeClientConfig = new FipeClientConfig();
    private ConverteDados conversor = new ConverteDados();
    private String tipoVeiculo;

    private final String BASE_URL = "https://parallelum.com.br/fipe/api/v1/";


    public void exibirMenu() {
        System.out.print("""
                    ******OPÇÕES******
                
                    (1) Carro
                    (2) Moto
                    (3) Caminhão
                
                     Digite uma das opções para consultar valores: 
                """);
        try {
        Integer escolhaTipoVeiculo = scanner.nextInt();


            switch (escolhaTipoVeiculo) {
                case 1:
                    System.out.println("Carro");
                    tipoVeiculo = "carros";
                    break;

                case 2:
                    System.out.println("Moto");
                    tipoVeiculo = "motos";
                    break;

                case 3:
                    System.out.println("Caminhão");
                    tipoVeiculo = "caminhoes";
                    break;

                default:
                    throw new OpcaoInvalidaException("A opção passada pelo usuário não é valida");
            }

            var urlMarcas = BASE_URL + tipoVeiculo + "/marcas";
            var jsonTipoVeiculo = fipeClientConfig.getData(urlMarcas);


            List<MarcaDTO> marcaDTO = conversor.obterListaDeDados(jsonTipoVeiculo, MarcaDTO.class);
            marcaDTO.forEach(marca -> System.out.println("Cód: " + marca.codigo() + " Nome Marca: " + marca.nome()));

            System.out.println("Selecione a marca do carro de acordo com o id: ");
            int escolhaMarca = scanner.nextInt();

            MarcaDTO marcaEscolhida = marcaDTO.stream()
                    .filter(m -> m.codigo() == escolhaMarca)
                    .findFirst()
                    .orElseThrow(() -> new ValorNaoEncontradoOuNuloException());

            var urlModelos = urlMarcas + "/" + marcaEscolhida.codigo() + "/modelos";
            var jsonModelos = fipeClientConfig.getData(urlModelos);

            ModeloResponseDTO responseModelos = conversor.obterDados(jsonModelos, ModeloResponseDTO.class);
            List<ModeloDTO> modeloDTO = responseModelos.modelos();

            modeloDTO.forEach(modelo -> System.out.println("Cód: " + modelo.codigo() + " Nome Modelo: " + modelo.descricao()));

            //limpar o buffer
            scanner.nextLine();

            System.out.println("Digite um trecho do nome do veículo para consulta: ");
            String modeloDesejado = scanner.nextLine();
            List<ModeloDTO> modelosEncontrados = modeloDTO.stream()
                    .filter(m -> m.descricao().toLowerCase().contains(modeloDesejado.toLowerCase()))
                    .toList(); // ver porque nao lista todos e se tem como corrigir

            if (modelosEncontrados.isEmpty()) {
                System.out.println("Nenhum modelo encontrado na busca!");
            } else {

                modelosEncontrados.forEach(modelosEncontrado ->
                        System.out.println("Cód: " + modelosEncontrado.codigo() + " Descrição: " + modelosEncontrado.descricao()));

                System.out.println("Selecione o modelo desejado pelo id: ");
                int modeloEscolhido = scanner.nextInt();

                ModeloDTO modelo = modelosEncontrados.stream()
                        .filter(m -> m.codigo() == modeloEscolhido)
                        .findFirst()
                        .orElseThrow(() -> new ValorNaoEncontradoOuNuloException());

                System.out.println("Cód: " + modelo.codigo() + " Descrição: " + modelo.descricao());

                var anosUrl = urlModelos + "/" + modelo.codigo() + "/anos";
                var anos = fipeClientConfig.getData(anosUrl);

                List<AnoDTO> anoDTO = conversor.obterListaDeDados(anos, AnoDTO.class);

                List<String> listaAnos = anoDTO.stream()
                        .map(AnoDTO::codigo)
                        .toList();

                System.out.println(listaAnos);

                System.out.println("Todos os valores por ano");

                listaAnos.forEach(ano -> {
                    String jsonVeiculo = fipeClientConfig.getData(anosUrl + "/" + ano);
                    VeiculoDTO veiculoDTO = conversor.obterDados(jsonVeiculo, VeiculoDTO.class);
                    System.out.println(veiculoDTO);
                });
            }

            scanner.close();
        } catch (NullPointerException | ValorNaoEncontradoOuNuloException e) {
            System.out.println(e.getMessage());
        } catch (OpcaoInvalidaException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException | TipoDivergenteException e) {
            System.out.println("O valor passado possui uma tipagem diferente do esperado");
            /*caso na hora de testar usemos um numero muito grande para simular um id invalido/null ele cairá aqui
            Ex: 9999999999999999999
            esse valor deixa de ser um inteiro e passa a ser um Long ou até um BigInteger
            */
        }

        System.out.println("Projeto encerrado");
    }
}