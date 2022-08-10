package com.artcources.artistica.service;

import com.artcources.artistica.exception.WorkshopNotFoundException;
import com.artcources.artistica.model.binding.CommentCreationBindingModel;
import com.artcources.artistica.model.entity.CommentEntity;
import com.artcources.artistica.model.entity.OnlineWorkshopEntity;
import com.artcources.artistica.model.entity.UserEntity;
import com.artcources.artistica.model.view.CommentDisplayViewModel;
import com.artcources.artistica.repository.CommentRepository;
import com.artcources.artistica.repository.UserRepository;
import com.artcources.artistica.repository.WorkshopRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final WorkshopRepository workshopRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentService(WorkshopRepository workshopRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.workshopRepository = workshopRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }


    public List<CommentDisplayViewModel> getAllCommentsForWorkshop(Long wokshopId) {
        OnlineWorkshopEntity workshop = workshopRepository.findById(wokshopId).orElseThrow(WorkshopNotFoundException::new);

        List<CommentEntity> comments = commentRepository.findAllByWorkshop(workshop).get();
        return comments.stream().map(comment -> new CommentDisplayViewModel(comment.getId(), comment.getAuthor().toString(),
            comment.getText())).collect(Collectors.toList());
    }

    public CommentDisplayViewModel createComment(CommentCreationBindingModel commentDto) {
        UserEntity author = userRepository.findByUsername(commentDto.getUsername()).get();

        CommentEntity comment = new CommentEntity();
        comment.setCreated(LocalDateTime.now());
        comment.setWorkshop(workshopRepository.findById(commentDto.getWorkshopId()).get());
        comment.setAuthor(author);
        comment.setApproved(true);
        comment.setText(commentDto.getMessage());

        commentRepository.save(comment);

        return new CommentDisplayViewModel(comment.getId(), author.toString(), comment.getText());
    }
}
