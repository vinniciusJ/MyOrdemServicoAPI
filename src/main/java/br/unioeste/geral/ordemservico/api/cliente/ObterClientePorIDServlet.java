package br.unioeste.geral.ordemservico.api.cliente;

import br.unioeste.geral.ordemservico.bo.cliente.Cliente;

import br.unioeste.geral.ordemservico.servico.service.cliente.UCClienteServicos;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/cliente/id")
public class ObterClientePorIDServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final UCClienteServicos clienteServicos;

    public ObterClientePorIDServlet(){
        objectMapper = new ObjectMapper();
        clienteServicos = new UCClienteServicos();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try{
            Long id = Long.parseLong(request.getParameter("id"));
            Cliente cliente = clienteServicos.obterClientePorID(id);

            String clienteResponse = objectMapper.writeValueAsString(cliente);

            response.getWriter().write(clienteResponse);
        }
        catch (Exception e){
            String errorJSON = objectMapper.writeValueAsString(e);

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(errorJSON);
        }
    }

}
