package br.unioeste.geral.ordemservico.api.chatbot;

import br.unioeste.geral.ordemservico.bo.cliente.Cliente;
import br.unioeste.geral.ordemservico.chatbot.dto.RespostaIA;
import br.unioeste.geral.ordemservico.chatbot.service.UCChatbotServicos;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/chatbot")
public class ChatbotServlet extends HttpServlet {
    private final ObjectMapper objectMapper;
    private final UCChatbotServicos chatbotServicos;

    public ChatbotServlet(){
        objectMapper = new ObjectMapper();
        chatbotServicos = new UCChatbotServicos();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        try{
            String mensagem = request.getParameter("mensagem");
            RespostaIA<?> resposta = chatbotServicos.obterResposta(mensagem);

            String respostaJSON = objectMapper.writeValueAsString(resposta);

            response.getWriter().write(respostaJSON);
        }
        catch (Exception e){
            String errorJSON = objectMapper.writeValueAsString(e);

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(errorJSON);
        }
    }
}
