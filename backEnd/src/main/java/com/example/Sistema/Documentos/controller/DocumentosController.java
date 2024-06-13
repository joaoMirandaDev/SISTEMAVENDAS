package com.example.Sistema.Documentos.controller;

import com.example.Sistema.Documentos.model.FileName;
import com.example.Sistema.Documentos.service.DocumentosService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/arquivos")
public class DocumentosController {
    @Autowired
    private DocumentosService documentosService;

    @Autowired
    public DocumentosController(DocumentosService documentosService) {
        this.documentosService = documentosService;
    }


    @CrossOrigin(origins = "http://localhost:8080/")
    @PostMapping("/uploadTemp")
    @Operation(summary = "Upload arquivo pasta temporaria", description = "Metodo utilizado para enviar o arquivo para a pasta temporaria", tags = "Arquivos")
    public String uploadFIle(@RequestParam("file") MultipartFile file) throws Exception {
        return documentosService.saveTemp(file);
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @PostMapping("/image")
    @Operation(summary = "Obter imagem", description = "Metodo utilizado para obter fotos", tags = "Arquivos")
    public ResponseEntity<Resource> getImage(@RequestBody FileName fileName) throws Exception {
        return documentosService.getImagem(fileName.getKey());
    }

}
