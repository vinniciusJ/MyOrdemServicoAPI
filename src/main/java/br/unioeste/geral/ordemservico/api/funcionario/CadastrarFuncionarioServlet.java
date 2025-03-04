package br.unioeste.geral.ordemservico.api.funcionario;

import br.unioeste.geral.ordemservico.bo.cliente.Cliente;
import br.unioeste.geral.ordemservico.bo.funcionario.Funcionario;
import br.unioeste.geral.ordemservico.servico.service.cliente.UCClienteServicos;
import br.unioeste.geral.ordemservico.servico.service.funcionario.UCFuncionarioServicos;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/funcionario/cadastrar")
public class CadastrarFuncionarioServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final UCFuncionarioServicos funcionarioServicos;

    public CadastrarFuncionarioServlet(){
        objectMapper = new ObjectMapper();
        funcionarioServicos = new UCFuncionarioServicos();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try{
            Funcionario funcionarioForm = objectMapper.readValue(request.getReader(), Funcionario.class);
            Funcionario funcionarioCadastrado = funcionarioServicos.cadastrarFuncionario(funcionarioForm);

            String funcionJSON = objectMapper.writeValueAsString(funcionarioCadastrado);

            response.getWriter().write(funcionJSON);
        }
        catch (Exception e){
            String errorJSON = objectMapper.writeValueAsString(e);

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(errorJSON);
        }
    }
}