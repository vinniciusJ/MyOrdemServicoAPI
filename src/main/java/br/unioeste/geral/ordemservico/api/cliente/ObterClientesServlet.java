package br.unioeste.geral.ordemservico.api.cliente;

import br.unioeste.geral.ordemservico.bo.cliente.Cliente;
import br.unioeste.geral.ordemservico.servico.service.UCClienteServicos;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/cliente")
public class ObterClientesServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final UCClienteServicos clienteServicos;

    public ObterClientesServlet(){
        objectMapper = new ObjectMapper();
        clienteServicos = new UCClienteServicos();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try{
            List<Cliente> clientes = clienteServicos.obterClientes();

            String clientesResponse = objectMapper.writeValueAsString(clientes);

            response.getWriter().write(clientesResponse);
        }
        catch (Exception e){
            String errorJSON = objectMapper.writeValueAsString(e);

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(errorJSON);
        }
    }
}
