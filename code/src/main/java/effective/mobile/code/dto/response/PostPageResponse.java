package effective.mobile.code.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import effective.mobile.code.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostPageResponse {
    @JsonProperty("posts")
    private List<PostResponse> posts;

    @JsonProperty("current_page")
    private Integer currentPage;

    @JsonProperty("total_items")
    private Long totalItems;

    @JsonProperty("total_pages")
    private Integer totalPages;
}
