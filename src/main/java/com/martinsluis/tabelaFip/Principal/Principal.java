package com.martinsluis.tabelaFip.Principal;

import com.martinsluis.tabelaFip.config.FipeClientConfig;
import com.martinsluis.tabelaFip.dto.MarcaDTO;
import com.martinsluis.tabelaFip.dto.ModeloDTO;
import com.martinsluis.tabelaFip.dto.ModeloResponseDTO;
import com.martinsluis.tabelaFip.model.Marca;
import com.martinsluis.tabelaFip.service.ConverteDados;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private FipeClientConfig fipeClientConfig = new FipeClientConfig();
    private ConverteDados conversor = new ConverteDados();
    private String tipoVeiculo;

    private final String BASE_URL = "https://parallelum.com.br/fipe/api/v1/";


    public void exibirMenu(){
                 System.out.print("""
                        ******OPÇÕES******
                    
                        (1) Carro
                        (2) Moto
                        (3) Caminhão
                    
                         Digite uma das opções para consultar valores: 
                    """);
            int escolhaTipoVeiculo = scanner.nextInt();

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
                    System.out.println("Opção inválida");
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
                .orElse(null);

        Marca marca = new Marca(marcaEscolhida.codigo(), marcaEscolhida.nome());

        System.out.println(marca);

        var urlModelos = urlMarcas+"/"+marca.getCodigo()+"/modelos";
        var jsonModelos = fipeClientConfig.getData(urlModelos);

        System.out.println(jsonModelos);

        ModeloResponseDTO responseModelos = conversor.obterDados(jsonModelos, ModeloResponseDTO.class);
        List<ModeloDTO> modeloDTO = responseModelos.modelos();

        modeloDTO.forEach(modelo -> System.out.println("Cód: " + modelo.codigo() + " Nome Modelo: " + modelo.codigo()));

        //limpar o buffer
        scanner.nextLine();

        System.out.println("Digite um trecho do nome do veículo para consulta: ");
        String modeloDesejado = scanner.nextLine();

        List<ModeloDTO> modelosEncontrados = modeloDTO.stream()
                .filter(m -> m.descricao().toLowerCase().contains(modeloDesejado.toLowerCase()))
                .toList();

        modelosEncontrados.forEach(modelosEncontrado ->
                System.out.println("Cód: " + modelosEncontrado.codigo() + " Descrição: " + modelosEncontrado.descricao()));
        }

        // caso o usuário escreva uma opção inválida, deve lançar exceção

        //* Escolher marca do carro pelo código
        //* Digitar trecho do descricao do carro para consulta
        //* Digitar código do modelo para consultar valores
        //https://parallelum.com.br/fipe/api/v1/carros/marcas/21/modelos/545/anos
            // endpoint acima lista os anos do modelo
        //Listar modelos pelo ano : https://parallelum.com.br/fipe/api/v1/carros/marcas/21/modelos/545/anos/2003-1
//        {
//            "TipoVeiculo": 1,
//                "Valor": "R$ 15.768,00",
//                "Marca": "Fiat",
//                "Modelo": "Palio Weekend Adventure 1.6 8V/16V",
//                "AnoModelo": 2003,
//                "Combustivel": "Gasolina",
//                "CodigoFipe": "001111-8",
//                "MesReferencia": "setembro de 2026",
//                "SiglaCombustivel": "G"
//        }

    }
