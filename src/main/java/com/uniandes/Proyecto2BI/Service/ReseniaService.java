package com.uniandes.Proyecto2BI.Service;


import com.uniandes.Proyecto2BI.dataaccess.model.Resenia;
import com.uniandes.Proyecto2BI.dataaccess.model.Usuario;
import com.uniandes.Proyecto2BI.dataaccess.repository.ReseniaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReseniaService {

    @Autowired
    private ReseniaRepository reseniaRepository;

    public Resenia createResenia(Resenia resenia){
        return reseniaRepository.save(resenia);
    }
    public Optional<Resenia> getReseniabyId(long id){
        return reseniaRepository.findById(id);
    }
    public List<Resenia> getallresenias(){
        return reseniaRepository.findAll();
    }
    public List<Resenia> getallreseniasbyUserID(Usuario usuario){
        return usuario.getResenias();
    }

}
