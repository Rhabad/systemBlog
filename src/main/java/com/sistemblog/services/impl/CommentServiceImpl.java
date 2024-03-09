package com.sistemblog.services.impl;

import com.sistemblog.exceptions.BlogAppException;
import com.sistemblog.exceptions.ResourceNotFoundException;
import com.sistemblog.models.dao.CommentDao;
import com.sistemblog.models.dao.PublicationDao;
import com.sistemblog.models.dto.CommentDto;
import com.sistemblog.models.entities.Comment;
import com.sistemblog.models.entities.Publication;
import com.sistemblog.services.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private PublicationDao publicationDao;

    @Override
    public CommentDto createComment(Long idPublication, CommentDto commentDto) {
        Comment comment = mapearEntidad(commentDto);
        Publication publication = publicationDao.findById(idPublication)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Publicacion", "id", idPublication));
        comment.setPublication(publication);

        Comment nuevoComentario = commentDao.save(comment);

        return mapearDto(nuevoComentario);
    }

    @Override
    public List<CommentDto> getCommentByIdPublication(Long idPublicacion) {
        List<Comment> comentarios = commentDao.findByIdPublication(idPublicacion);
        return comentarios.stream().map(comentario ->
                mapearDto(comentario)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long idPublicacion, Long idComentario) {
        Publication publication = publicationDao.findById(idPublicacion)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Publicacion", "id", idPublicacion));
        Comment comment = commentDao.findById(idComentario)
                .orElseThrow(()->
                        new ResourceNotFoundException("Comentario", "id", idComentario));

        if (!comment.getPublication().getId().equals(publication.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertence a la publicacion");
        }

        return mapearDto(comment);
    }

    @Override
    public CommentDto update(Long idPublicacion, Long idComentario, CommentDto commentDto) {
        Publication publication = publicationDao.findById(idPublicacion)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Publicacion", "id", idPublicacion));
        Comment comment = commentDao.findById(idComentario)
                .orElseThrow(()->
                        new ResourceNotFoundException("Comentario", "id", idComentario));

        if (!comment.getPublication().getId().equals(publication.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertence a la publicacion");
        }

        comment.setName(commentDto.getNombre());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getCuerpo());

        Comment comentUpdate = commentDao.save(comment);

        return mapearDto(comentUpdate);
    }

    @Override
    public void deleteComment(Long idPublicacion, Long idComentario) {
        Publication publication = publicationDao.findById(idPublicacion)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Publicacion", "id", idPublicacion));
        Comment comment = commentDao.findById(idComentario)
                .orElseThrow(()->
                        new ResourceNotFoundException("Comentario", "id", idComentario));

        if (!comment.getPublication().getId().equals(publication.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertence a la publicacion");
        }
        commentDao.delete(comment);
    }


    // -----------------------

    private CommentDto mapearDto(Comment comment){
        CommentDto commentDto = CommentDto.builder()
                .id(comment.getId())
                .nombre(comment.getName())
                .email(comment.getEmail())
                .cuerpo(comment.getBody())
                .build();
        return commentDto;
    }
    private Comment mapearEntidad(CommentDto commentDto){
        Comment comment = Comment.builder()
                .id(commentDto.getId())
                .name(commentDto.getNombre())
                .email(commentDto.getEmail())
                .body(commentDto.getCuerpo())
                .build();
        return comment;
    }
}
