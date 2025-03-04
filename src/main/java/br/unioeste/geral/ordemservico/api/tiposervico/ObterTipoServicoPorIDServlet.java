package br.unioeste.geral.ordemservico.api.tiposervico;

import br.unioeste.geral.ordemservico.bo.tiposervico.TipoServico;
import br.unioeste.geral.ordemservico.servico.service.tiposervico.UCTipoServicoServicos;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/tipo-servico/id")
public class ObterTipoServicoPorIDServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final UCTipoServicoServicos tipoServicoServicos;

    public ObterTipoServicoPorIDServlet(){
        objectMapper = new ObjectMapper();
        tipoServicoServicos = new UCTipoServicoServicos();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try{
            Long id = Long.parseLong(request.getParameter("id"));
            TipoServico tipoServico = tipoServicoServicos.obterTipoServicoPorID(id);

            String tipoServicoJSON = objectMapper.writeValueAsString(tipoServico);

            response.getWriter().write(tipoServicoJSON);
        }
        catch (Exception e){
            String errorJSON = objectMapper.writeValueAsString(e);

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(errorJSON);
        }
    }

}
