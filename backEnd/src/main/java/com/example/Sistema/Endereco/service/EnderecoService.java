package com.example.Sistema.Endereco.service;

import com.example.Sistema.Endereco.Dto.EnderecoDTO;
import com.example.Sistema.Endereco.model.Endereco;
import com.example.Sistema.Endereco.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public Object findByRegiao(String cep) throws IOException {
        long cepConvert = Long.parseLong(cep);
        String url = "https://viacep.com.br/ws/"+cepConvert+"/json/";
        URL obj = new URL(url);
        //Abre a conexão
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        // Lê a resposta do servidor
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response;
    }

    public Endereco add(EnderecoDTO enderecoDTO) {
        Endereco endereco = new Endereco();

        endereco.setCep(enderecoDTO.getCep());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setEstado(enderecoDTO.getEstado());
        endereco.setRua(enderecoDTO.getRua());
        endereco.setNumero(enderecoDTO.getNumero());

       return enderecoRepository.save(endereco);
    }

}
