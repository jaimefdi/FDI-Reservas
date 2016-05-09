package es.fdi.reservas.reserva.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import es.fdi.reservas.reserva.business.boundary.ReservaSolapadaException;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(ReservaSolapadaException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {    
        
		String acceptHeader = request.getHeader("Accept");
       
        // If Accept header exists, check if it expects a response of type json, otherwise just return text/html
        // Use apache commons lang3 to escape json values
        if(acceptHeader.contains("application/json")) {
            // return as JSON
            String jsonString = 
                    "{\"success\": false, \"message\": \"hhhhh\" }";
        
            System.out.println("In handleGeneric" + e.getMessage());
            return jsonString;
        } else {
            //return as HTML
            response.setContentType("text/html");
           // response.getWriter().write(e.getMessage());
            return response.toString();
        }
        
		//return new ErrorResult(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
