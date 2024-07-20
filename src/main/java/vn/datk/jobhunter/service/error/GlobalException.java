package vn.datk.jobhunter.service.error;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.datk.jobhunter.domain.RestResponse;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = IdInvalidException.class)
    public ResponseEntity<Object> handleIdException(IdInvalidException idInvalidException){
        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(idInvalidException.getMessage());
        res.setMessage("Id Invalid Exception");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(res);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException dataIntegrityViolationException
    ){
        RestResponse<Object> res = new RestResponse<Object>();
        res.setStatusCode(HttpStatus.BAD_REQUEST.value());
        res.setError(dataIntegrityViolationException.getMessage());
        res.setMessage(dataIntegrityViolationException.getCause());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(res);
    }
}
