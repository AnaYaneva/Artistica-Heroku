package com.artcources.artistica.web.rest;

import com.artcources.artistica.exception.WorkshopNotFoundException;
import com.artcources.artistica.model.binding.CommentCreationBindingModel;
import com.artcources.artistica.model.service.CommentMessageServiceModel;
import com.artcources.artistica.model.view.CommentDisplayViewModel;
import com.artcources.artistica.service.CommentService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentRestController {
    private CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{workshopId}/comments")
    public ResponseEntity<List<CommentDisplayViewModel>> getComments(@PathVariable("workshopId") Long routeId) {
        return ResponseEntity.ok(commentService.getAllCommentsForWorkshop(routeId));
    }

    @PostMapping(value = "/{workshopId}/comments", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommentDisplayViewModel> createComment(@PathVariable("workshopId") Long workshopId,
                                                                 Principal principal,
                                                                 @RequestBody CommentMessageServiceModel commentDto) {
        CommentCreationBindingModel commentCreationBindingModel = new CommentCreationBindingModel(
                principal.getName(),
                workshopId,
                commentDto.getMessage()
        );

        CommentDisplayViewModel comment = commentService.createComment(commentCreationBindingModel);

        return ResponseEntity
                .created(URI.create(String.format("/api/%d/comments/%d", workshopId, comment.getId())))
                .body(comment);
    }

    @ExceptionHandler({WorkshopNotFoundException.class})
    public ResponseEntity<ErrorApiResponse> handleWorkshopNotFound() {
        return ResponseEntity.status(404).body(new ErrorApiResponse("Such Workshop doesn't exist!", 1004));
    }
}


// GET /api/{routeId}/comments -> returns list of comments for route
// POST /api/{routeId}/comments -> create comment to the route and to return the comment just created
// * GET /api/{routeId}/comments/{commentId} -> returns specific comment by id

class ErrorApiResponse {
    private String message;
    private Integer errorCode;

    public ErrorApiResponse(String message, Integer errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public ErrorApiResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
