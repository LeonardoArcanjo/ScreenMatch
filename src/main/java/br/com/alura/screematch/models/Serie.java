package br.com.alura.screematch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)  // ignora as propriedades e inclusive as nao passadas na classe
public record Serie(@JsonAlias({"Title", "Titulo"}) String title,
                    @JsonAlias("totalSeasons") Integer totalSeasons,
                    @JsonAlias("imdbRating") String rating)
{
    // Diferenca entre o JsonAlias vs JsonProperty
    // O JsonProperty ele gera e le o json de acordo com o nome original do atributo
    // O JsonAlias so serve para ler de um json
     // Dentro do JsonAlias voce pode usar um array de possivel valores de propriedade que o JsonAlias vai procurar para receber o valor
}
