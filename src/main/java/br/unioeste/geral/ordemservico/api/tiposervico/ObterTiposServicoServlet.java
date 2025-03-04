package br.unioeste.geral.ordemservico.api.tiposervico;

import br.unioeste.geral.ordemservico.bo.cliente.Cliente;
import br.unioeste.geral.ordemservico.bo.tiposervico.TipoServico;
import br.unioeste.geral.ordemservico.servico.service.tiposervico.UCTipoServicoServicos;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/tipo-servico")
public class ObterTiposServicoServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final UCTipoServicoServicos tipoServicoServicos;

    public ObterTiposServicoServlet(){
        objectMapper = new ObjectMapper();
        tipoServicoServicos = new UCTipoServicoServicos();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try{
            List<TipoServico> tipoServicos = tipoServicoServicos.obterTiposServicos();

            String tipoServicosJSON = objectMapper.writeValueAsString(tipoServicos);

            response.getWriter().write(tipoServicosJSON);
        }
        catch (Exception e){
            String errorJSON = objectMapper.writeValueAsString(e);

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(errorJSON);
        }
    }
}
