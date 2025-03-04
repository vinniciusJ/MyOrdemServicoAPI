package br.unioeste.geral.ordemservico.api.ordemservico;

import br.unioeste.geral.ordemservico.bo.ordemservico.OrdemServico;
import br.unioeste.geral.ordemservico.servico.service.ordemservico.UCOrdemServicoServicos;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/ordem-servico")
public class ObterOrdemServicosServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final UCOrdemServicoServicos ordemServicoServicos;

    public ObterOrdemServicosServlet(){
        objectMapper = new ObjectMapper();
        ordemServicoServicos = new UCOrdemServicoServicos();

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try{
            List<OrdemServico> ordemServicos = ordemServicoServicos.obterOrdemServicos();

            String ordemServicosJSON = objectMapper.writeValueAsString(ordemServicos);

            response.getWriter().write(ordemServicosJSON);
        }
        catch (Exception e){
            String errorJSON = objectMapper.writeValueAsString(e);

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(errorJSON);
        }
    }
}
