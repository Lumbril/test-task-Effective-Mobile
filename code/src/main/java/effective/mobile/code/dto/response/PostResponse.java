package effective.mobile.code.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import effective.mobile.code.entities.Post;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("text")
    private String text;

    @JsonProperty("image")
    private String imageURL;

    @JsonProperty("user_id")
    private Long userId;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.text = post.getText();
        this.imageURL = post.getImageURL();
        this.userId = post.getUser().getId();
    }
}
