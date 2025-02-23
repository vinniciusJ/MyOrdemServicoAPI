package br.unioeste.geral.ordemservico.api.telefone;

import br.unioeste.geral.pessoa.bo.ddi.DDI;
import br.unioeste.geral.pessoa.servico.service.UCPessoaServicos;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/telefone/ddi")
public class ObterDDIsServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final UCPessoaServicos pessoaServicos;

    public ObterDDIsServlet(){
        objectMapper = new ObjectMapper();
        pessoaServicos = new UCPessoaServicos();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try{
            List<DDI> ddis = pessoaServicos.obterDDIs();
            String ddisResponse = objectMapper.writeValueAsString(ddis);

            response.getWriter().write(ddisResponse);
        }
        catch (Exception e){
            String errorJSON = objectMapper.writeValueAsString(e);

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(errorJSON);
        }
    }
}
