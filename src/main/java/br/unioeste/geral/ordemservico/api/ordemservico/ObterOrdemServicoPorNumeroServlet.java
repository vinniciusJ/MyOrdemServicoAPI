package br.unioeste.geral.ordemservico.api.ordemservico;

import br.unioeste.geral.ordemservico.bo.ordemservico.OrdemServico;
import br.unioeste.geral.ordemservico.bo.tiposervico.TipoServico;
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


@WebServlet("/ordem-servico/numero")
public class ObterOrdemServicoPorNumeroServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final UCOrdemServicoServicos ordemServicoServicos;

    public ObterOrdemServicoPorNumeroServlet(){
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
            String numero = request.getParameter("numero");
            OrdemServico ordemServico = ordemServicoServicos.obterOrdemServicoPorNumero(numero);

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
