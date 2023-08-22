package effective.mobile.code.controllers.rest;

import effective.mobile.code.dto.request.PostRequest;
import effective.mobile.code.dto.response.ErrorResponse;
import effective.mobile.code.dto.response.PostResponse;
import effective.mobile.code.entities.Post;
import effective.mobile.code.exceptions.NotFindEntity;
import effective.mobile.code.services.PostService;
import effective.mobile.code.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
            @ModelAttribute PostRequest postRequest,
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

    @Operation(summary = "Обновить пост")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Successful update",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400", description = "Bad request",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    }
            )
    })
    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> patch(@PathVariable Long id,
                                   @ModelAttribute PostRequest postRequest)
            throws NotFindEntity {
        /* TODO: Сделать проверку на принадлежность поста юзеру
        */

        Post post = postService.partialUpdate(id, postRequest);

        return ResponseEntity.ok().body(
                PostResponse.builder()
                        .title(post.getTitle())
                        .text(post.getText())
                        .imageURL(post.getImageURL())
                        .userId(post.getId())
                        .build()
        );
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws NotFindEntity {
        postService.deletePost(id);

        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(NotFindEntity.class)
    public ResponseEntity<ErrorResponse> handleInvalidToken(NotFindEntity exc) {
        return ResponseEntity.badRequest().body(
                ErrorResponse.builder()
                        .error(exc.getMessage())
                        .build()
        );
    }
}
