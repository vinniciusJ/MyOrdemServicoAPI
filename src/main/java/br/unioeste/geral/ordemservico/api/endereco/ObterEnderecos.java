package br.unioeste.geral.ordemservico.api.endereco;

import br.unioeste.geral.endereco.bo.endereco.Endereco;
import br.unioeste.geral.endereco.bo.unidadefederativa.UnidadeFederativa;
import br.unioeste.geral.endereco.servico.service.UCEnderecoServicos;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/endereco/")
public class ObterEnderecos extends HttpServlet  {
    private final ObjectMapper objectMapper;
    private final UCEnderecoServicos enderecoServicos;

    public ObterEnderecos(){
        objectMapper = new ObjectMapper();
        enderecoServicos = new UCEnderecoServicos();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try{
            List<Endereco> enderecos = enderecoServicos.obterEnderecos();
            String enderecosJSON = objectMapper.writeValueAsString(enderecos);

            response.getWriter().write(enderecosJSON);
        }
        catch (Exception e){
            String errorJSON = objectMapper.writeValueAsString(e);

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(errorJSON);
        }
    }
}
