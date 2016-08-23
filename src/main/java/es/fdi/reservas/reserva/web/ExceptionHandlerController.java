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
import es.fdi.reservas.users.business.boundary.UserPasswordException;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(ReservaSolapadaException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult reservaSolapadaException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {    
 
		return new ErrorResult(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
	
	
	@ExceptionHandler(UserPasswordException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult userPasswordException(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {    
 
		return new ErrorResult(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
	
}
