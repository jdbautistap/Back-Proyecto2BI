package com.uniandes.Proyecto2BI.controller;


import com.uniandes.Proyecto2BI.Service.ReseniaService;
import com.uniandes.Proyecto2BI.Service.UsuarioService;
import com.uniandes.Proyecto2BI.dataaccess.model.Resenia;
import com.uniandes.Proyecto2BI.dataaccess.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/resenias")
public class ReseniaController {

    @Autowired
    private ReseniaService reseniaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping()
    private ResponseEntity<Object> getAllResenias() {
        return new  ResponseEntity<>(reseniaService.getallresenias(),HttpStatus.OK);
    }

    @GetMapping("/usuario/{id}")
    private ResponseEntity<Object> getAllReseniasbyUser(@PathVariable Long id) {
        if (usuarioService.checkUsuarioId(id).isPresent()){
            Usuario usuario=usuarioService.checkUsuarioId(id).get();
            return new  ResponseEntity<>(reseniaService.getallreseniasbyUserID(usuario),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("No existe un usuario con este Id "+id,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id1}/usuario/{id}")
    private ResponseEntity<Object> getReseniabyidbyUser(@PathVariable Long id1,@PathVariable Long id) {
        if (usuarioService.checkUsuarioId(id).isPresent()){
            Usuario usuario=usuarioService.checkUsuarioId(id).get();
            Resenia resenia=reseniaService.getReseniabyId(id1).get();
            if(resenia!=null){
                if(reseniaService.getallreseniasbyUserID(usuario).contains(resenia)){
                    return new  ResponseEntity<>(resenia,HttpStatus.CREATED);
                }else {
                    return new ResponseEntity<>("La reseña con id: "+id1+" no pertenece al user"+id,HttpStatus.NOT_FOUND);
                }
            }else{
                return new ResponseEntity<>("No existe una reseña con este Id "+id1,HttpStatus.NOT_FOUND);
            }
        } else{
            return new ResponseEntity<>("No existe un usuario con este Id "+id,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/usuario/{id}")
    private ResponseEntity<Object> createResenia(@PathVariable Long id, @RequestBody Resenia resenia){
        if (usuarioService.checkUsuarioId(id).isPresent()){
            Usuario usuario=usuarioService.checkUsuarioId(id).get();
            usuario.getResenias().add(resenia);
            resenia.setUsuario(usuario);
            Resenia tmp = reseniaService.createResenia(resenia);
            return new ResponseEntity<>(tmp,HttpStatus.CREATED);
        } else{
            return new ResponseEntity<>("No existe un usuario con este Id "+id,HttpStatus.NOT_FOUND);
        }
    }

}
