package br.unioeste.geral.ordemservico.api.endereco;

import br.unioeste.geral.endereco.bo.cidade.Cidade;
import br.unioeste.geral.endereco.bo.endereco.Endereco;
import br.unioeste.geral.endereco.servico.service.UCEnderecoServicos;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/endereco/id")
public class ObterEnderecoPorIDServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final UCEnderecoServicos enderecoServicos;

    public ObterEnderecoPorIDServlet(){
        objectMapper = new ObjectMapper();
        enderecoServicos = new UCEnderecoServicos();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try{
            Long id = Long.valueOf(request.getParameter("id"));

            Endereco endereco = enderecoServicos.obterEnderecoPorID(id);

            String enderecoResponse = objectMapper.writeValueAsString(endereco);

            response.getWriter().write(enderecoResponse);
        }
        catch (Exception e){
            String errorJSON = objectMapper.writeValueAsString(e);

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(errorJSON);
        }
    }
}