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

@WebServlet("/cliente/cadastrar")
public class CadastrarClienteServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final UCClienteServicos clienteServicos;

    public CadastrarClienteServlet(){
        objectMapper = new ObjectMapper();
        clienteServicos = new UCClienteServicos();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try{
            Cliente clienteForm = objectMapper.readValue(request.getReader(), Cliente.class);

            Cliente cliente = clienteServicos.cadastrarCliente(clienteForm);

            String clienteResponse = objectMapper.writeValueAsString(cliente);

            response.getWriter().write(clienteResponse);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            String errorJSON = objectMapper.writeValueAsString(e);

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(errorJSON);
        }
    }
}