package br.unioeste.geral.ordemservico.api.endereco;

import br.unioeste.geral.endereco.bo.bairro.Bairro;
import br.unioeste.geral.endereco.servico.service.UCEnderecoServicos;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/endereco/bairro/")
public class ObterBairrosServlet extends HttpServlet  {
    private final ObjectMapper objectMapper;
    private final UCEnderecoServicos enderecoServicos;

    public ObterBairrosServlet(){
        objectMapper = new ObjectMapper();
        enderecoServicos = new UCEnderecoServicos();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try{
            List<Bairro> bairros = enderecoServicos.obterBairros();
            String bairrosJSON = objectMapper.writeValueAsString(bairros);

            response.getWriter().write(bairrosJSON);
        }
        catch (Exception e){
            String errorJSON = objectMapper.writeValueAsString(e);

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(errorJSON);
        }
    }
}
