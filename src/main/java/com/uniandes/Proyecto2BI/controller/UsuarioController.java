package com.uniandes.Proyecto2BI.controller;

import com.uniandes.Proyecto2BI.Service.EncryptDecryptService;
import com.uniandes.Proyecto2BI.Service.UsuarioService;
import com.uniandes.Proyecto2BI.dataaccess.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EncryptDecryptService encryptDecryptService;

    @PostMapping
    private ResponseEntity<Object> saveUsuario(@RequestBody Usuario usuario){
        if (usuarioService.chekUsuarioEmail(usuario.getEmail())==null){
            try{
                usuario.setPassword(EncryptDecryptService.encrypt(usuario.getPassword()));
            }
            catch(Exception e){
                return new ResponseEntity<>("Error Encriptando contraseña.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Usuario tmp =  usuarioService.createUsuario(usuario);
            return new ResponseEntity<>(tmp,HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>("Ya Existe un Usuario con ese Email",HttpStatus.CONFLICT);
        }
    }
    @PostMapping("/login")
    private ResponseEntity<Object> getUsuario(@RequestBody Usuario usuario){
        Usuario found=usuarioService.chekUsuarioEmail(usuario.getEmail());
        if ((found)!=null) {
            String passwordFound;
            try {
                passwordFound = encryptDecryptService.decrypt(found.getPassword());
            } catch (Exception e){
                return new ResponseEntity<>("Error Encriptando contraseña.",HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if(passwordFound.equals(usuario.getPassword())){
                return new ResponseEntity<>(found,HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Credencial no Coincide",HttpStatus.UNAUTHORIZED);
            }
        }
        else {
            return new ResponseEntity<>("No existe usuario con este correo",HttpStatus.NOT_FOUND);
        }
    }
}
