package com.sistemblog.services;

import com.sistemblog.models.dto.CommentDto;
import java.util.List;

public interface ICommentService {
    CommentDto createComment(Long idPublication, CommentDto commentDto);
    List<CommentDto> getCommentByIdPublication(Long idPublicacion);
    CommentDto getCommentById(Long idPublicacion, Long idComentario);
    CommentDto update(Long idPublicacion, Long idComentario, CommentDto commentDto);
    void deleteComment(Long idPublicacion, Long idComentario);
}
