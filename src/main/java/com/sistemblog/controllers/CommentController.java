package com.sistemblog.controllers;

import com.sistemblog.models.dto.CommentDto;
import com.sistemblog.services.ICommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @GetMapping("/publicaciones/{idPublicacion}/comentario")
    public List<CommentDto> listarComentariosPorPublicacion(@PathVariable Long idPublicacion){
        return commentService.getCommentByIdPublication(idPublicacion);
    }

    @GetMapping("/publicaciones/{idPublicacion}/comentario/{idComentario}")
    public ResponseEntity<CommentDto> getCommetById(
            @PathVariable Long idPublicacion,
            @PathVariable Long idComentario
    ){
        CommentDto commentDto = commentService.getCommentById(idPublicacion, idComentario);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }


    @PostMapping("/publicaciones/{idPublicacion}/comentario")
    public ResponseEntity<CommentDto> saveComment(
            @PathVariable Long idPublicacion,
            @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(idPublicacion, commentDto), HttpStatus.CREATED);
    }

    @PutMapping("/publicaciones/{idPublicacion}/comentario/{idComentario}")
    public ResponseEntity<CommentDto> update(
            @PathVariable Long idPublicacion,
            @PathVariable Long idComentario,
            @Valid @RequestBody CommentDto commentDto
    ){
        CommentDto comentUpdate = commentService.update(idPublicacion, idComentario, commentDto);
        return new ResponseEntity<>(comentUpdate, HttpStatus.OK);
    }

    @DeleteMapping("/publicaciones/{idPublicacion}/comentario/{idComentario}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long idPublicacion,
            @PathVariable Long idComentario
    ){
        commentService.deleteComment(idPublicacion, idComentario);
        return new ResponseEntity<>("Comentario eliminado", HttpStatus.OK);
    }

}
