package service;

import br.com.alura.client.ClientHttpConfiguration;
import br.com.alura.domain.Abrigo;
import br.com.alura.domain.Pet;
import br.com.alura.service.AbrigoService;
import br.com.alura.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.atLeast;

public class AbrigoServiceTest {

    private ClientHttpConfiguration client = mock(ClientHttpConfiguration.class);
    private AbrigoService abrigoService = new AbrigoService(client);
    private HttpResponse<String> response = mock(HttpResponse.class);
    private Abrigo abrigo = new Abrigo("Teste", "61981880392", "abrigo_alura@gmail.com");

    @Test
    public void deveVerificarQuandoHaAbrigo() throws IOException, InterruptedException {
        abrigo.setId(0L);
        String expectedAbrigosCadastrados = "Abrigos cadastrados:";
        String expectedIdENome = "0 - Teste";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        when(response.body()).thenReturn("[{"+abrigo.toString()+"}]");
        when(client.dispararRequisicaoGet(anyString())).thenReturn(response);

        abrigoService.listarAbrigo();

        String[] lines = baos.toString().split(System.lineSeparator());
        String actualAbrigosCadastrados = lines[0];
        String actualIdENome = lines[1];

        Assertions.assertEquals(expectedAbrigosCadastrados, actualAbrigosCadastrados);
        Assertions.assertEquals(expectedIdENome, actualIdENome);
    }

    @Test
    public void deveVerificarQuandoNaoHaAbrigo() throws IOException, InterruptedException {
        abrigo.setId(0L);
        String expected = "Não há abrigos cadastrados";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        when(response.body()).thenReturn("[]"); //simulando um retorno vazio da api
        when(client.dispararRequisicaoGet(anyString())).thenReturn(response);

        abrigoService.listarAbrigo();

        String[] lines = baos.toString().split(System.lineSeparator());
        String actualMensagemDeAbrigosQuandoNaoHaCadastro = lines[0];

        Assertions.assertEquals(expected, actualMensagemDeAbrigosQuandoNaoHaCadastro);
    }

//    @Test
//    public void deveVerificarSeDispararRequisicaoPostSeraChamado() throws IOException, InterruptedException {
//        PetService petService = new PetService(client);
//        String userInput = String.format("Teste%pets.csv",
//                System.lineSeparator());
//        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
//         System.setIn(bais);
//
//        when(client.dispararRequisicaoPost(anyString(), any())).thenReturn(response);
//
//        petService.importarPetsDoAbrigo();
//        verify(client.dispararRequisicaoPost(anyString(), anyString()), atLeast(1));
//    }
}
