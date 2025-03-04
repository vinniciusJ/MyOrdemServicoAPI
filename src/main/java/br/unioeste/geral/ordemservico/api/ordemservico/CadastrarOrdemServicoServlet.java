package br.unioeste.geral.ordemservico.api.ordemservico;

import br.unioeste.geral.ordemservico.bo.ordemservico.OrdemServico;
import br.unioeste.geral.ordemservico.bo.tiposervico.TipoServico;
import br.unioeste.geral.ordemservico.servico.service.ordemservico.UCOrdemServicoServicos;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/ordem-servico/cadastrar")
public class CadastrarOrdemServicoServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final UCOrdemServicoServicos ordemServicoServicos;

    public CadastrarOrdemServicoServlet(){
        objectMapper = new ObjectMapper();
        ordemServicoServicos = new UCOrdemServicoServicos();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try{
            OrdemServico form = objectMapper.readValue(request.getReader(), OrdemServico.class);
            OrdemServico ordemServico = ordemServicoServicos.cadastrarOrdemServico(form);

            String ordemServicoJSON = objectMapper.writeValueAsString(ordemServico);

            response.getWriter().write(ordemServicoJSON);
        }
        catch (Exception e){
            String errorJSON = objectMapper.writeValueAsString(e);

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(errorJSON);
        }
    }
}