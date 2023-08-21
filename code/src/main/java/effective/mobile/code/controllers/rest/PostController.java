package effective.mobile.code.controllers.rest;

import effective.mobile.code.dto.request.PostRequest;
import effective.mobile.code.dto.response.ErrorResponse;
import effective.mobile.code.dto.response.PostResponse;
import effective.mobile.code.entities.Post;
import effective.mobile.code.exceptions.NotFindEntity;
import effective.mobile.code.services.PostService;
import effective.mobile.code.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "Post", description = "post API")
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<PostResponse> savePost(
            @Valid @ModelAttribute PostRequest postRequest,
            Principal principal
            ) {
        postRequest.setUser(userService.getUser(principal.getName()));
        Post post = postService.savePost(postRequest);
        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .text(post.getText())
                .imageURL(post.getImageURL())
                .userId(post.getUser().getId())
                .build();

        return ResponseEntity.ok().body(postResponse);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws NotFindEntity {
        postService.deletePost(id);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(NotFindEntity.class)
    public ResponseEntity<?> handleInvalidToken(NotFindEntity exc) {
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .error(exc.getMessage())
                        .build()
        );
    }
}
